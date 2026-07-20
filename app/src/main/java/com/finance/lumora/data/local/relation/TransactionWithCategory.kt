package com.finance.lumora.data.local.relation



import androidx.room.Embedded
import androidx.room.Relation
import com.finance.lumora.data.local.entity.CategoryEntity
import com.finance.lumora.data.local.entity.TransactionEntity

/**
 * Represents a transaction along with its associated category.
 *
 * Room automatically performs the relationship lookup
 * using category_id -> Category.id.
 */
data class TransactionWithCategory(

    @Embedded
    val transaction: TransactionEntity,

    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: CategoryEntity
)