package com.finance.lumora.notifications

import android.Manifest
import android.util.Log
import androidx.annotation.RequiresPermission
import com.finance.lumora.domain.model.BudgetAlertEvent
import com.finance.lumora.domain.model.BudgetAlertLevel
import com.finance.lumora.domain.repository.SettingsRepository
import com.finance.lumora.domain.usecase.budget.BudgetAlertEvaluator
import com.finance.lumora.domain.usecase.transaction.GetMonthlyExpenseUseCase
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BudgetAlertCoordinator @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val getMonthlyExpenseUseCase: GetMonthlyExpenseUseCase,
    private val budgetAlertEvaluator: BudgetAlertEvaluator,
    private val notificationManager: LumoraNotificationManager
) {
    private companion object {
        private const val TAG = "BudgetCoordinator"
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    suspend fun evaluate() {
        Log.d(TAG, "Starting budget evaluation...")


        val notificationsEnabled = settingsRepository.isNotificationsEnabled.first()
        if (!notificationsEnabled) {
            Log.w(TAG, "Evaluation stopped: Notifications disabled in settings.")
            return
        }

        val budgetAlertsEnabled = settingsRepository.isBudgetAlertsEnabled.first()
        if (!budgetAlertsEnabled) {
            Log.w(TAG, "Evaluation stopped: Budget alerts disabled in settings.")
            return
        }

        //val monthlyBudget = 15000.0

        val monthlyBudget = settingsRepository.monthlyBudget.first()
        Log.d(TAG, "Monthly Budget: $monthlyBudget")
        if (monthlyBudget <= 0.0) {
            Log.w(TAG, "Evaluation stopped: Monthly budget is 0 or negative.")
            return
        }

        val monthlyExpense = getMonthlyExpenseUseCase().first()
        Log.d(TAG, "Monthly Expense: $monthlyExpense")

        val currentMonth = SimpleDateFormat("yyyy-MM", Locale.US).format(Date())
        val storedMonth = settingsRepository.budgetAlertMonth.first()
        val storedLevel = settingsRepository.lastBudgetAlertLevel.first()
        val previousLevel = if (storedMonth == currentMonth) storedLevel else BudgetAlertLevel.NONE

        val currentLevel = budgetAlertEvaluator(
            monthlyExpense = monthlyExpense,
            monthlyBudget = monthlyBudget
        )

        Log.d(TAG, "Current Level: $currentLevel | Previous Level: $previousLevel")

        if (currentLevel.ordinal <= previousLevel.ordinal) {
            Log.w(TAG, "Evaluation stopped: Current level ($currentLevel) <= Previous level ($previousLevel).")
            return
        }

        val event = when (currentLevel) {
            BudgetAlertLevel.NONE -> BudgetAlertEvent.None
            BudgetAlertLevel.WARNING -> BudgetAlertEvent.Warning
            BudgetAlertLevel.CRITICAL -> BudgetAlertEvent.Critical
            BudgetAlertLevel.EXCEEDED -> BudgetAlertEvent.Exceeded
        }

        val content = BudgetAlertNotificationMapper.map(event)
        if (content == null) {
            Log.w(TAG, "Evaluation stopped: Notification mapper returned null for event $event.")
            return
        }

        Log.d(TAG, "Attempting to show notification: Title='${content.title}'")
        val isPosted = notificationManager.showBudgetAlertNotification(
            title = content.title,
            message = content.message
        )

        Log.d(TAG, "Notification posted result: $isPosted")

        if (isPosted) {
            settingsRepository.setBudgetAlertState(currentMonth, currentLevel)
        }
    }
}
