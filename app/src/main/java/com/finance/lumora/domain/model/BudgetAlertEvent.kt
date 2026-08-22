package com.finance.lumora.domain.model

sealed interface BudgetAlertEvent {

    data object None : BudgetAlertEvent

    data object Warning : BudgetAlertEvent

    data object Critical : BudgetAlertEvent

    data object Exceeded : BudgetAlertEvent
}

