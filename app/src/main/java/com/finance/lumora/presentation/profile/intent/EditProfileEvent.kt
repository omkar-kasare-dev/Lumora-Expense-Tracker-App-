package com.finance.lumora.presentation.profile.intent

sealed interface EditProfileEvent {

    /**
     * User changed the full name.
     */
    data class FullNameChanged(
        val fullName: String
    ) : EditProfileEvent

    /**
     * User changed the email.
     * Keep this event available for the UI, but whether
     * Firebase Authentication email is actually changed
     * will be handled separately.
     */
    data class EmailChanged(
        val email: String
    ) : EditProfileEvent


    data class CurrencyChanged(
        val currency: String
    ) : EditProfileEvent


    data class LanguageChanged(
        val language: String
    ) : EditProfileEvent

    data object SaveProfile : EditProfileEvent

    data object Retry : EditProfileEvent


    data object ClearError : EditProfileEvent
}