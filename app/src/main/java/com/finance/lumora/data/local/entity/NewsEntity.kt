package com.finance.lumora.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey val id: String,
    val title: String,
    val snippet: String,
    val source: String,
    val url: String,
    val imageUrl: String?,
    val publishedAt: Long,
    val scope: String,
    val fetchedAt: Long
)