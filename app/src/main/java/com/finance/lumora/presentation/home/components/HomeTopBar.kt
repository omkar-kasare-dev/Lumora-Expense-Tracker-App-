package com.finance.lumora.presentation.home.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/**
 * Top App Bar used on the Home Screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(

    onMenuClick: () -> Unit = {},

    onNotificationClick: () -> Unit = {},

    onProfileClick: () -> Unit = {}

) {

    CenterAlignedTopAppBar(

        title = {

            Text(

                text = "Lumora",

                style = MaterialTheme.typography.titleLarge

            )

        },

        navigationIcon = {

            IconButton(

                onClick = onMenuClick

            ) {

                Icon(

                    imageVector = Icons.Default.Menu,

                    contentDescription = "Menu"

                )

            }

        },

        actions = {

            IconButton(

                onClick = onNotificationClick

            ) {

                Icon(

                    imageVector = Icons.Outlined.Notifications,

                    contentDescription = "Notifications"

                )

            }

            IconButton(

                onClick = onProfileClick

            ) {

                Icon(

                    imageVector = Icons.Outlined.Person,

                    contentDescription = "Profile"

                )

            }

        }

    )

}