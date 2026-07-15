package com.finance.lumora.presentation.profile



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController
) {

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text("Profile")

                }

            )

        }

    ) { paddingValues ->

        Box(

            modifier = Modifier
                .fillMaxSize(),

            contentAlignment = Alignment.Center

        ) {

            Column(

                horizontalAlignment = Alignment.CenterHorizontally,

                verticalArrangement = Arrangement.spacedBy(16.dp)

            ) {

                Icon(

                    imageVector = Icons.Default.Person,

                    contentDescription = "Profile",

                    tint = MaterialTheme.colorScheme.primary

                )

                Text(

                    text = "Profile Screen",

                    style = MaterialTheme.typography.headlineSmall

                )

                Text(

                    text = "Phase 1.1 Completed",

                    style = MaterialTheme.typography.bodyMedium,

                    color = MaterialTheme.colorScheme.onSurfaceVariant

                )

            }

        }

    }

}