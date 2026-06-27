package com.finance.lumora.data.mapper

import com.finance.lumora.data.local.entity.ExpenseEntity
import com.finance.lumora.data.local.relation.ExpenseWithCategory
import com.finance.lumora.domain.model.Expense

/**
 * Converts ExpenseWithCategory to Expense domain model.
 */
fun ExpenseWithCategory.toDomain(): Expense {

    return Expense(
        id = expense.id,
        title = expense.title,
        amount = expense.amount,
        categoryId = expense.categoryId,
        categoryName = category.name,
        note = expense.note,
        date = expense.date,
        createdAt = expense.createdAt,
        updatedAt = expense.updatedAt
    )
}

/**
 * Converts Expense domain model to ExpenseEntity.
 */
fun Expense.toEntity(): ExpenseEntity {

    return ExpenseEntity(
        id = id,
        title = title,
        amount = amount,
        categoryId = categoryId,
        note = note,
        date = date,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

/**
 * Converts a list of ExpenseWithCategory to a list of Expense.
 */
fun List<ExpenseWithCategory>.toDomainList(): List<Expense> {

    return map { expenseWithCategory ->
        expenseWithCategory.toDomain()
    }
}

/**
 * Converts a list of Expense to a list of ExpenseEntity.
 */
fun List<Expense>.toEntityList(): List<ExpenseEntity> {

    return map { expense ->
        expense.toEntity()
    }
}