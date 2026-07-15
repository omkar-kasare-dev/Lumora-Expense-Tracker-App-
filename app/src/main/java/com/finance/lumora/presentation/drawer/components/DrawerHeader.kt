package com.finance.lumora.presentation.drawer.components



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DrawerHeader(

    modifier: Modifier = Modifier,

    userName: String = "Guest",

    userTagLine: String = "Track every rupee wisely"

) {

    Surface(

        modifier = modifier.fillMaxWidth(),

        color = MaterialTheme.colorScheme.primaryContainer

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 28.dp
                ),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.Center

        ) {

            Box(

                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(
                        MaterialTheme.colorScheme.primary
                    ),

                contentAlignment = Alignment.Center

            ) {

                Icon(

                    imageVector = Icons.Default.Person,

                    contentDescription = "Profile",

                    tint = Color.White,

                    modifier = Modifier.size(36.dp)

                )

            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(

                text = userName,

                style = MaterialTheme.typography.titleLarge,

                fontWeight = FontWeight.Bold

            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(

                text = userTagLine,

                style = MaterialTheme.typography.bodyMedium,

                color = MaterialTheme.colorScheme.onSurfaceVariant

            )

        }

    }

}