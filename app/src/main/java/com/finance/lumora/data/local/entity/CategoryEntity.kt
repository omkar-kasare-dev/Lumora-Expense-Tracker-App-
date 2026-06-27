package com.finance.lumora.data.local.entity



import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories",
    indices = [
        Index(
            value = ["name"],
            unique = true
        )
    ]
)
data class CategoryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "name")
    val name: String,

    /**
     * Material Symbol name.
     * Example:
     * "shopping_cart"
     * "restaurant"
     */
    @ColumnInfo(name = "icon")
    val icon: String,

    /**
     * Stored as ARGB Long.
     */
    @ColumnInfo(name = "color")
    val color: Long,

    /**
     * Built-in categories
     * cannot be deleted.
     */
    @ColumnInfo(name = "is_default")
    val isDefault: Boolean = false
)