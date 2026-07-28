package com.finance.lumora.presentation.auth.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthTopBar(

    title: String,

    showBackButton: Boolean = false,

    onBackClick: () -> Unit = {}

) {

    CenterAlignedTopAppBar(

        title = {

            Text(

                text = title,

                style = MaterialTheme.typography.titleLarge

            )

        },

        navigationIcon = {

            if (showBackButton) {

                IconButton(

                    onClick = onBackClick

                ) {

                    Icon(

                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,

                        contentDescription = "Back"

                    )

                }

            }

        },

        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(

            containerColor = MaterialTheme.colorScheme.background,

            titleContentColor = MaterialTheme.colorScheme.onBackground,

            navigationIconContentColor = MaterialTheme.colorScheme.onBackground

        )

    )

}