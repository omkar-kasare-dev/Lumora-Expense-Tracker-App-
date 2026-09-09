package com.finance.lumora.presentation.ai.model

sealed interface AurixPersistenceState {

    data object Idle : AurixPersistenceState

    data object Saving : AurixPersistenceState

    data object Saved : AurixPersistenceState

    data class Error(
        val message: String
    ) : AurixPersistenceState
}