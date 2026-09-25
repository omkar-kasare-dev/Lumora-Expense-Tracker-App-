package com.finance.lumora.presentation.news

import com.finance.lumora.domain.model.NewsArticle
import com.finance.lumora.domain.model.NewsScope

data class NewsState(
    // Start as loading so the screen never flashes an "empty" message before the first load.
    val isGlobalLoading: Boolean = true,
    val isLocalLoading: Boolean = true,
    val globalNews: List<NewsArticle> = emptyList(),
    val localNews: List<NewsArticle> = emptyList()
) {
    val isLoading: Boolean get() = isGlobalLoading || isLocalLoading
}

sealed interface NewsIntent {
    /** Makes sure a feed is being observed. Ignored if it already is. */
    data class LoadNews(val scope: NewsScope) : NewsIntent

    /** Forces a network refresh (the repository still enforces a minimum gap). */
    data class Refresh(val scope: NewsScope) : NewsIntent

    data class OpenArticle(val url: String) : NewsIntent
}

sealed interface NewsEffect {
    data class OpenBrowser(val url: String) : NewsEffect
    data class ShowError(val message: String) : NewsEffect
}