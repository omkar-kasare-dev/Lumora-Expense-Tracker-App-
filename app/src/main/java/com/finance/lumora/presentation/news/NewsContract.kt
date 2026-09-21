package com.finance.lumora.presentation.news

import com.finance.lumora.domain.model.NewsArticle
import com.finance.lumora.domain.model.NewsScope

// presentation/news/NewsContract.kt
data class NewsState(
    val isLoading: Boolean = false,
    val globalNews: List<NewsArticle> = emptyList(),
    val localNews: List<NewsArticle> = emptyList(),
    val error: String? = null
)

sealed interface NewsIntent {
    data class LoadNews(val scope: NewsScope) : NewsIntent
    data class Refresh(val scope: NewsScope) : NewsIntent
    data class OpenArticle(val url: String) : NewsIntent
}

sealed interface NewsEffect {
    data class OpenBrowser(val url: String) : NewsEffect
    data class ShowError(val message: String) : NewsEffect
}