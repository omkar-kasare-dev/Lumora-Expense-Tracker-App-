package com.finance.lumora.presentation.profile.components


import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Represents a single menu item displayed
 * on the Profile screen.
 */
data class ProfileMenu(

    /**
     * Title displayed to the user.
     */
    val title: String,

    /**
     * Optional subtitle shown below the title.
     */
    val subTitle: String? = null,

    /**
     * Leading icon.
     */
    val icon: ImageVector,

    /**
     * Whether to show the navigation arrow.
     */
    val showArrow: Boolean = true,

    /**
     * Action performed when the item is clicked.
     */
    val onClick: () -> Unit = {}

)