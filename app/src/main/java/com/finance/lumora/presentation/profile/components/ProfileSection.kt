package com.finance.lumora.presentation.profile.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance.lumora.presentation.profile.components.ProfileMenu
import com.finance.lumora.presentation.profile.components.ProfileColors

/**
 * Reusable section used on the Profile screen.
 *
 * Example:
 *
 * Account
 * -------------------
 * Personal Information
 * Security
 *
 * Preferences
 * -------------------
 * Appearance
 * Notifications
 */
@Composable
fun ProfileSection(

    title: String,

    menuItems: List<ProfileMenu>,

    modifier: Modifier = Modifier

) {

    Column(

        modifier = modifier.fillMaxWidth()

    ) {

        //--------------------------------------------------
        // Section Title
        //--------------------------------------------------

        Text(

            text = title,

            modifier = Modifier
                .padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                ),

            style = MaterialTheme.typography.titleMedium,

            color = ProfileColors.SectionTitle

        )

        //--------------------------------------------------
        // Menu List
        //--------------------------------------------------

        Surface(

            modifier = Modifier.fillMaxWidth(),

            color = MaterialTheme.colorScheme.surface

        ) {

            Column {

                menuItems.forEach { menu ->

                    ProfileMenuItem(

                        title = menu.title,

                        subTitle = menu.subTitle,

                        icon = menu.icon,

                        showArrow = menu.showArrow,

                        onClick = menu.onClick

                    )

                }

            }

        }

    }

}