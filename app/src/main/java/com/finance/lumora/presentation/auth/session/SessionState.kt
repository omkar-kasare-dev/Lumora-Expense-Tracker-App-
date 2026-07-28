package com.finance.lumora.presentation.auth.session

import com.finance.lumora.domain.model.User

sealed interface SessionState {

    data object Loading : SessionState

    data object Authenticated : SessionState

    data object Unauthenticated : SessionState



}