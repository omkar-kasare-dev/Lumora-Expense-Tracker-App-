package com.finance.lumora.data.local.entity



import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.finance.lumora.data.local.enums.TransactionType

@Entity(
    tableName = "transactions",

    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],

            /**
             * Prevent deleting categories
             * that already contain transactions.
             */
            onDelete = ForeignKey.RESTRICT,

            onUpdate = ForeignKey.CASCADE
        )
    ],

    indices = [
        Index("category_id"),
        Index("type"),
        Index("transaction_date")
    ]
)
data class TransactionEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    /**
     * Transaction amount.
     *
     * Example:
     * 500
     * 2500
     * 12000
     */
    @ColumnInfo(name = "amount")
    val amount: Double,

    /**
     * INCOME / EXPENSE
     */
    @ColumnInfo(name = "type")
    val type: TransactionType,

    /**
     * FK -> categories.id
     */
    @ColumnInfo(name = "category_id")
    val categoryId: Long,

    /**
     * Optional note.
     */
    @ColumnInfo(name = "note")
    val note: String? = null,

    /**
     * User selected transaction date.
     *
     * Stored as epoch millis.
     */
    @ColumnInfo(name = "transaction_date")
    val transactionDate: Long,

    /**
     * Record creation timestamp.
     */
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    /**
     * Record last update timestamp.
     */
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)