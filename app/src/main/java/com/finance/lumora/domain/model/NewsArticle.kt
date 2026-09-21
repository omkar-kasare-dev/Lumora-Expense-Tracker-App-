package com.finance.lumora.domain.model

data class NewsArticle(
    val id: String,
    val title: String,
    val snippet: String,
    val source: String,
    val url: String,
    val imageUrl: String?,
    val publishedAt: Long,
    val scope: NewsScope // GLOBAL or LOCAL
)
enum class NewsScope { GLOBAL, LOCAL }