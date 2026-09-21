package com.finance.lumora.domain.repository

import com.finance.lumora.domain.model.NewsArticle
import com.finance.lumora.domain.model.NewsScope
import kotlinx.coroutines.flow.Flow
import com.finance.lumora.domain.model.Result

interface NewsRepository {
    fun getFinanceNews(scope: NewsScope, forceRefresh: Boolean): Flow<Result<List<NewsArticle>>>
}