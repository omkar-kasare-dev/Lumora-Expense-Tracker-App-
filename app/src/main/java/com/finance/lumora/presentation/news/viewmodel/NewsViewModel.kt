package com.finance.lumora.presentation.news.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.lumora.domain.model.NewsArticle
import com.finance.lumora.domain.model.NewsScope
import com.finance.lumora.domain.model.Result
import com.finance.lumora.domain.usecase.news.GetFinanceNewsUseCase
import com.finance.lumora.presentation.news.NewsEffect
import com.finance.lumora.presentation.news.intent.NewsIntent
import com.finance.lumora.presentation.news.state.NewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getFinanceNews: GetFinanceNewsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NewsState())
    val state: StateFlow<NewsState> = _state.asStateFlow()

    // Buffered so an effect is never lost or blocks while the UI is briefly not collecting.
    private val _effect = Channel<NewsEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    // One live collector per scope; a new load cancels the previous one.
    private val jobs = mutableMapOf<NewsScope, Job>()

    init {
        // Loading here (not in the screen) means rotation or navigating back doesn't reload.
        NewsScope.values().forEach { load(it, forceRefresh = false) }
    }

    fun onIntent(intent: NewsIntent) {
        when (intent) {
            is NewsIntent.LoadNews -> {
                if (jobs[intent.scope]?.isActive != true) load(intent.scope, forceRefresh = false)
            }
            is NewsIntent.Refresh -> load(intent.scope, forceRefresh = true)
            is NewsIntent.OpenArticle -> {
                _effect.trySend(NewsEffect.OpenBrowser(intent.url))
            }
        }
    }

    private fun load(scope: NewsScope, forceRefresh: Boolean) {
        jobs[scope]?.cancel()
        jobs[scope] = getFinanceNews(scope, forceRefresh)
            .onEach { result -> handle(scope, result) }
            .catch {
                setLoading(scope, false)
                _effect.trySend(NewsEffect.ShowError("Couldn't load the news. Try again in a moment."))
            }
            .launchIn(viewModelScope)
    }

    private fun handle(scope: NewsScope, result: Result<List<NewsArticle>>) {
        when (result) {
            is Result.Loading -> setLoading(scope, true)
            is Result.Success -> _state.update { current ->
                if (scope == NewsScope.GLOBAL) {
                    current.copy(isGlobalLoading = false, globalNews = result.data)
                } else {
                    current.copy(isLocalLoading = false, localNews = result.data)
                }
            }
            is Result.Error -> {
                // Cached stories stay on screen; the message is shown as a snackbar.
                setLoading(scope, false)
                _effect.trySend(NewsEffect.ShowError(result.message))
            }
        }
    }

    private fun setLoading(scope: NewsScope, loading: Boolean) {
        _state.update { current ->
            if (scope == NewsScope.GLOBAL) current.copy(isGlobalLoading = loading)
            else current.copy(isLocalLoading = loading)
        }
    }
}