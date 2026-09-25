package com.finance.lumora.presentation.news


sealed interface NewsEffect {
    data class OpenBrowser(val url: String) : NewsEffect
    data class ShowError(val message: String) : NewsEffect
}