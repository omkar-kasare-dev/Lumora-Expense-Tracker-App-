package com.finance.lumora.domain.usecase.ai

import com.finance.lumora.data.local.dao.TransactionDao
import com.finance.lumora.data.local.enums.TransactionType
import com.finance.lumora.domain.model.ai.AurixIntent
import com.finance.lumora.domain.model.ai.FinancialQueryResult
import com.finance.lumora.domain.model.ai.QueryType
import kotlinx.coroutines.flow.first
import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject
import kotlin.math.abs

/**
 * Internal projection model to safely map category aggregate results from Room DAO queries.
 */
data class CategoryResult(
    val categoryName: String,
    val totalAmount: Double
)

/**
 * Universal execution engine for processing financial queries against local Lumora data.
 */
class ExecuteFinancialQueryUseCase @Inject constructor(
    private val transactionDao: TransactionDao
) {

    private val indianLocale = Locale.forLanguageTag("en-IN")
    private val currencyFormatter = NumberFormat.getCurrencyInstance(indianLocale)

    suspend operator fun invoke(queryIntent: AurixIntent.QueryFinance): FinancialQueryResult {
        val zoneId = ZoneId.systemDefault()
        val raw = queryIntent.rawQuery.lowercase()

        // 1. Comparison Queries (Month-over-Month, Period vs Period)
        if (isComparisonQuery(raw)) {
            return handleComparisonQuery(zoneId)
        }

        // 2. Net Financial Balance / Savings Queries (Income - Expense)
        if (isNetIncomeOrSavingsQuery(raw)) {
            return handleNetIncomeQuery(raw, queryIntent, zoneId)
        }

        // 3. Resolve Primary Date Range
        val (startMillis, endMillis, periodLabel) = resolveDateBoundsAndLabel(
            rawQuery = queryIntent.rawQuery,
            startDateIso = queryIntent.startDateIso,
            endDateIso = queryIntent.endDateIso,
            zoneId = zoneId
        )

        // Fetch primary aggregate transaction data & safely map to local CategoryResult model
        val categoryTotals: List<CategoryResult> = transactionDao.getCategoryTotals(
            type = TransactionType.EXPENSE,
            startDate = startMillis,
            endDate = endMillis
        ).first().map { item ->
            CategoryResult(
                categoryName = item.categoryName ?: "Uncategorized",
                totalAmount = item.totalAmount
            )
        }

        val intentCategory = queryIntent.categoryName
        val matchedCategory = intentCategory
            ?: extractCategoryFromQuery(queryIntent.rawQuery, categoryTotals.map { it.categoryName })

        // 4. Category-Wise Breakdown Request
        if (isBreakdownQuery(raw) || (queryIntent.queryType == QueryType.CATEGORY_SPENDING && matchedCategory == null)) {
            return handleCategoryBreakdown(categoryTotals, periodLabel)
        }

        // 5. Specific Category Spending
        if (!matchedCategory.isNullOrBlank()) {
            return handleSpecificCategorySpending(matchedCategory, categoryTotals, periodLabel)
        }

        // 6. Transaction Search / Itemized Lists
        if (queryIntent.queryType == QueryType.RECENT_TRANSACTIONS || isSearchQuery(raw)) {
            return handleRecentOrSearchTransactions(raw, matchedCategory, startMillis, endMillis, periodLabel, zoneId)
        }

        // 7. Total Income Query
        if (queryIntent.queryType == QueryType.TOTAL_INCOME || raw.contains("income") || raw.contains("earned")) {
            val totalIncome = transactionDao.getTotalIncome(
                incomeType = TransactionType.INCOME,
                startDate = startMillis,
                endDate = endMillis
            ).first()

            return FinancialQueryResult(
                formattedAnswer = "Your total income $periodLabel is **${currencyFormatter.format(totalIncome)}**.",
                totalAmount = totalIncome,
                queryType = QueryType.TOTAL_INCOME
            )
        }

        // 8. Default Total Expense Fallback
        val totalExpense = transactionDao.getTotalExpense(
            expenseType = TransactionType.EXPENSE,
            startDate = startMillis,
            endDate = endMillis
        ).first()

        return FinancialQueryResult(
            formattedAnswer = "Your total expense $periodLabel is **${currencyFormatter.format(totalExpense)}**.",
            totalAmount = totalExpense,
            queryType = QueryType.TOTAL_EXPENSE
        )
    }

    // --- Intent Handlers ---

    private suspend fun handleComparisonQuery(zoneId: ZoneId): FinancialQueryResult {
        val now = LocalDate.now()

        // Current Period
        val currStart = now.withDayOfMonth(1).atStartOfDay(zoneId).toInstant().toEpochMilli()
        val currEnd = now.withDayOfMonth(now.lengthOfMonth()).plusDays(1).atStartOfDay(zoneId).toInstant().toEpochMilli() - 1

        // Previous Period
        val prevMonth = now.minusMonths(1)
        val prevStart = prevMonth.withDayOfMonth(1).atStartOfDay(zoneId).toInstant().toEpochMilli()
        val prevEnd = prevMonth.withDayOfMonth(prevMonth.lengthOfMonth()).plusDays(1).atStartOfDay(zoneId).toInstant().toEpochMilli() - 1

        val currentTotal = transactionDao.getTotalExpense(TransactionType.EXPENSE, currStart, currEnd).first()
        val previousTotal = transactionDao.getTotalExpense(TransactionType.EXPENSE, prevStart, prevEnd).first()

        val currMonthName = now.month.name.lowercase().replaceFirstChar { it.uppercase() }
        val prevMonthName = prevMonth.month.name.lowercase().replaceFirstChar { it.uppercase() }

        val diff = currentTotal - previousTotal
        val formattedCurr = currencyFormatter.format(currentTotal)
        val formattedPrev = currencyFormatter.format(previousTotal)
        val formattedDiff = currencyFormatter.format(abs(diff))

        val response = when {
            diff < 0 -> {
                val pct = if (previousTotal > 0) String.format(Locale.US, "%.1f", (abs(diff) / previousTotal) * 100) else "0"
                "Your expenses **decreased by $formattedDiff** ($pct%) this month! You spent **$formattedCurr** in $currMonthName compared to **$formattedPrev** in $prevMonthName."
            }
            diff > 0 -> {
                val pct = if (previousTotal > 0) String.format(Locale.US, "%.1f", (diff / previousTotal) * 100) else "100"
                "Your expenses **increased by $formattedDiff** ($pct%) this month. You spent **$formattedCurr** in $currMonthName compared to **$formattedPrev** in $prevMonthName."
            }
            else -> "Your spending is **unchanged**. You spent **$formattedCurr** in both $currMonthName and $prevMonthName."
        }

        return FinancialQueryResult(formattedAnswer = response, totalAmount = currentTotal, queryType = QueryType.TOTAL_EXPENSE)
    }

    private suspend fun handleNetIncomeQuery(
        rawQuery: String,
        queryIntent: AurixIntent.QueryFinance,
        zoneId: ZoneId
    ): FinancialQueryResult {
        val (start, end, label) = resolveDateBoundsAndLabel(rawQuery, queryIntent.startDateIso, queryIntent.endDateIso, zoneId)

        val income = transactionDao.getTotalIncome(TransactionType.INCOME, start, end).first()
        val expense = transactionDao.getTotalExpense(TransactionType.EXPENSE, start, end).first()
        val net = income - expense

        val formattedNet = currencyFormatter.format(abs(net))
        val formattedInc = currencyFormatter.format(income)
        val formattedExp = currencyFormatter.format(expense)

        val statusText = if (net >= 0) {
            "You have saved **$formattedNet** $label (Earned: $formattedInc, Spent: $formattedExp)."
        } else {
            "You are running a deficit of **$formattedNet** $label (Earned: $formattedInc, Spent: $formattedExp)."
        }

        return FinancialQueryResult(formattedAnswer = statusText, totalAmount = net, queryType = QueryType.TOTAL_INCOME)
    }

    private fun handleCategoryBreakdown(
        categoryTotals: List<CategoryResult>,
        periodLabel: String
    ): FinancialQueryResult {
        if (categoryTotals.isEmpty()) {
            return FinancialQueryResult("No category expenses recorded $periodLabel.", 0.0, QueryType.CATEGORY_SPENDING)
        }

        val grandTotal = categoryTotals.sumOf { it.totalAmount }
        val breakdown = categoryTotals.joinToString("\n") { cat ->
            val pct = if (grandTotal > 0) " (${String.format(Locale.US, "%.1f", (cat.totalAmount / grandTotal) * 100)}%)" else ""
            "• **${cat.categoryName}**: ${currencyFormatter.format(cat.totalAmount)}$pct"
        }

        val answer = "Here is your spending distribution $periodLabel:\n\n$breakdown\n\n**Total:** ${currencyFormatter.format(grandTotal)}"
        return FinancialQueryResult(answer, grandTotal, QueryType.CATEGORY_SPENDING)
    }

    private fun handleSpecificCategorySpending(
        targetCategory: String,
        categoryTotals: List<CategoryResult>,
        periodLabel: String
    ): FinancialQueryResult {
        val cleanTarget = targetCategory.trim().lowercase()
        val match = categoryTotals.find {
            val name = it.categoryName.trim().lowercase()
            name == cleanTarget || name.contains(cleanTarget) || cleanTarget.contains(name)
        }

        return if (match != null) {
            val amount = currencyFormatter.format(match.totalAmount)
            FinancialQueryResult("You spent **$amount** on **${match.categoryName}** $periodLabel.", match.totalAmount, QueryType.CATEGORY_SPENDING)
        } else {
            FinancialQueryResult("No expenses recorded under **$targetCategory** $periodLabel.", 0.0, QueryType.CATEGORY_SPENDING)
        }
    }

    private suspend fun handleRecentOrSearchTransactions(
        rawQuery: String,
        category: String?,
        startMillis: Long,
        endMillis: Long,
        periodLabel: String,
        zoneId: ZoneId
    ): FinancialQueryResult {
        val searchKeyword = category ?: extractSearchKeyword(rawQuery)
        val list = transactionDao.searchTransactions(
            query = searchKeyword,
            transactionType = null,
            categoryId = null,
            startDate = startMillis,
            endDate = endMillis,
            minAmount = null,
            maxAmount = null
        ).first().take(7)

        if (list.isEmpty()) {
            return FinancialQueryResult("No matching transactions found $periodLabel.", queryType = QueryType.RECENT_TRANSACTIONS)
        }

        val dFormatter = DateTimeFormatter.ofPattern("MMM dd", Locale.getDefault())
        val formattedList = list.joinToString("\n") { item ->
            val dateStr = Instant.ofEpochMilli(item.transaction.transactionDate).atZone(zoneId).format(dFormatter)
            val sign = if (item.transaction.type == TransactionType.EXPENSE) "-" else "+"
            val amt = currencyFormatter.format(item.transaction.amount)
            val noteOrCategory = item.transaction.note?.takeIf { it.isNotBlank() } ?: item.category.name
            "• **$dateStr** — $noteOrCategory: $sign$amt"
        }

        return FinancialQueryResult("Here are your matching transactions $periodLabel:\n\n$formattedList", queryType = QueryType.RECENT_TRANSACTIONS)
    }

    // --- Helper Analyzers ---

    private fun isComparisonQuery(raw: String) = raw.contains("increase") || raw.contains("decrease") ||
            raw.contains("compared") || raw.contains("compare") || raw.contains("higher") ||
            raw.contains("lower") || raw.contains("vs") || raw.contains("than last month") ||
            raw.contains("expect the last month")

    private fun isNetIncomeOrSavingsQuery(raw: String) = raw.contains("savings") || raw.contains("saved") ||
            raw.contains("remaining") || raw.contains("net") || raw.contains("profit") || raw.contains("balance")

    private fun isBreakdownQuery(raw: String) = raw.contains("category wise") || raw.contains("distribution") ||
            raw.contains("breakdown") || raw.contains("split") || raw.contains("by category") || raw.contains("per category")

    private fun isSearchQuery(raw: String) = raw.contains("show") || raw.contains("list") || raw.contains("find") ||
            raw.contains("where") || raw.contains("history") || raw.contains("recent")

    private fun extractSearchKeyword(raw: String): String {
        val stopWords = setOf("show", "me", "my", "recent", "transactions", "list", "history", "expenses", "spent", "on", "for", "in")
        return raw.lowercase().split("\\s+".toRegex()).filterNot { stopWords.contains(it) }.joinToString(" ")
    }

    private fun extractCategoryFromQuery(rawQuery: String, knownCategories: List<String>): String? {
        val lower = rawQuery.lowercase()
        knownCategories.firstOrNull { lower.contains(it.lowercase()) }?.let { return it }

        val match = Regex("""(?i)\b(?:on|for|in|about)\s+([a-zA-Z]+)""").find(rawQuery)
        val candidate = match?.groupValues?.getOrNull(1)

        val excluded = setOf("this", "the", "my", "current", "last", "month", "year", "week", "today", "total", "distribution")
        return if (candidate != null && !excluded.contains(candidate.lowercase())) candidate else null
    }

    private fun resolveDateBoundsAndLabel(
        rawQuery: String,
        startDateIso: String?,
        endDateIso: String?,
        zoneId: ZoneId
    ): Triple<Long, Long, String> {
        val now = LocalDate.now()
        val lower = rawQuery.lowercase()

        if (!startDateIso.isNullOrBlank() && !endDateIso.isNullOrBlank()) {
            val start = try { LocalDate.parse(startDateIso) } catch (_: Exception) { now.withDayOfMonth(1) }
            val end = try { LocalDate.parse(endDateIso) } catch (_: Exception) { now.withDayOfMonth(now.lengthOfMonth()) }
            return Triple(
                start.atStartOfDay(zoneId).toInstant().toEpochMilli(),
                end.plusDays(1).atStartOfDay(zoneId).toInstant().toEpochMilli() - 1,
                "between $startDateIso and $endDateIso"
            )
        }

        val (start, end, label) = when {
            lower.contains("last month") || lower.contains("previous month") -> {
                val lm = now.minusMonths(1)
                Triple(lm.withDayOfMonth(1), lm.withDayOfMonth(lm.lengthOfMonth()), "in ${lm.month.name.lowercase().replaceFirstChar { it.uppercase() }}")
            }
            lower.contains("today") -> Triple(now, now, "today")
            lower.contains("this year") -> Triple(now.withDayOfYear(1), now.withDayOfYear(now.lengthOfYear()), "this year")
            else -> Triple(now.withDayOfMonth(1), now.withDayOfMonth(now.lengthOfMonth()), "this month")
        }

        return Triple(
            start.atStartOfDay(zoneId).toInstant().toEpochMilli(),
            end.plusDays(1).atStartOfDay(zoneId).toInstant().toEpochMilli() - 1,
            label
        )
    }
}