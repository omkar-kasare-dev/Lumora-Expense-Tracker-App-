package com.finance.lumora.domain.usecase.news

import com.finance.lumora.domain.model.NewsScope
import com.finance.lumora.domain.repository.NewsRepository
import javax.inject.Inject

class GetFinanceNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(scope: NewsScope, forceRefresh: Boolean = false) =
        repository.getFinanceNews(scope, forceRefresh)
}