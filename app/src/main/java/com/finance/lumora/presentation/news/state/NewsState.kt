package com.finance.lumora.presentation.news.state

import com.finance.lumora.domain.model.NewsArticle

data class NewsState(
    // Start as loading so the screen never flashes an "empty" message before the first load.
    val isGlobalLoading: Boolean = true,
    val isLocalLoading: Boolean = true,
    val globalNews: List<NewsArticle> = emptyList(),
    val localNews: List<NewsArticle> = emptyList()
) {
    val isLoading: Boolean get() = isGlobalLoading || isLocalLoading
}