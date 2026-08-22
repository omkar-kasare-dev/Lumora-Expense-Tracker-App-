package com.finance.lumora.data.local.relation



import androidx.room.Embedded
import androidx.room.Relation
import com.finance.lumora.data.local.entity.CategoryEntity
import com.finance.lumora.data.local.entity.ExpenseEntity

/**
 * Represents an Expense along with its associated Category.
 * Used only inside the data layer.
 */
data class ExpenseWithCategory(

    @Embedded
    val expense: ExpenseEntity,

    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: CategoryEntity
)