package com.finance.lumora.presentation.news.intent

import com.finance.lumora.domain.model.NewsScope

sealed interface NewsIntent {
    /** Makes sure a feed is being observed. Ignored if it already is. */
    data class LoadNews(val scope: NewsScope) : NewsIntent

    /** Forces a network refresh (the repository still enforces a minimum gap). */
    data class Refresh(val scope: NewsScope) : NewsIntent

    data class OpenArticle(val url: String) : NewsIntent
}