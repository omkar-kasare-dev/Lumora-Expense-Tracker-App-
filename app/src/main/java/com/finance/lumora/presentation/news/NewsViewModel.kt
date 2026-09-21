package com.finance.lumora.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.model.NewsScope
import com.finance.lumora.domain.usecase.news.GetFinanceNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.finance.lumora.domain.model.Result
import kotlinx.coroutines.flow.launchIn

// presentation/news/NewsViewModel.kt
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getFinanceNews: GetFinanceNewsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NewsState())
    val state: StateFlow<NewsState> = _state.asStateFlow()

    private val _effect = Channel<NewsEffect>()
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: NewsIntent) {
        when (intent) {
            is NewsIntent.LoadNews -> load(intent.scope, forceRefresh = false)
            is NewsIntent.Refresh -> load(intent.scope, forceRefresh = true)
            is NewsIntent.OpenArticle -> viewModelScope.launch {
                _effect.send(NewsEffect.OpenBrowser(intent.url))
            }
        }
    }

    private fun load(scope: NewsScope, forceRefresh: Boolean) {
        getFinanceNews(scope, forceRefresh).onEach { result ->
            when (result) {
                is Result.Loading -> _state.update { it.copy(isLoading = true) }
                is Result.Success -> _state.update {
                    if (scope == NewsScope.GLOBAL) it.copy(isLoading = false, globalNews = result.data)
                    else it.copy(isLoading = false, localNews = result.data)
                }
                is Result.Error -> {
                    _state.update { it.copy(isLoading = false, error = result.message) }
                    _effect.send(NewsEffect.ShowError(result.message))
                }
            }
        }.launchIn(viewModelScope)
    }
}