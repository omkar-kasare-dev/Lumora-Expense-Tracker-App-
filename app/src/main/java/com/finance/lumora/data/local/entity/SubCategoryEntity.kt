package com.finance.lumora.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sub_categories",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("categoryId")
    ]
)
data class SubCategoryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val categoryId: Long,

    val name: String,

    val isDefault: Boolean = false
)