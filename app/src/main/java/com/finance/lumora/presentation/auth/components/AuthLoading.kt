package com.finance.lumora.presentation.auth.components



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AuthLoading(

    modifier: Modifier = Modifier,

    message: String = "Please wait..."

) {

    Box(

        modifier = modifier
            .fillMaxSize()
            .background(
                Color.Black.copy(alpha = 0.45f)
            ),

        contentAlignment = Alignment.Center

    ) {

        Card(

            shape = RoundedCornerShape(20.dp),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),

            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )

        ) {

            Column(

                horizontalAlignment = Alignment.CenterHorizontally,

                verticalArrangement = Arrangement.Center,

                modifier = Modifier
                    .size(180.dp)

            ) {

                CircularProgressIndicator()

                androidx.compose.foundation.layout.Spacer(
                    modifier = Modifier.size(20.dp)
                )

                Text(

                    text = message,

                    style = MaterialTheme.typography.bodyLarge,

                    color = MaterialTheme.colorScheme.onSurface

                )

            }

        }

    }

}