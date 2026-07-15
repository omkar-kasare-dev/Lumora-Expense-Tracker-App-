package com.finance.lumora.presentation.profile.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import com.finance.lumora.presentation.profile.components.ProfileColors

/**
 * Header displayed at the top of the
 * Profile screen.
 */
@Composable
fun ProfileHeader(

    userName: String,

    userTagLine: String,

    modifier: Modifier = Modifier

) {

    Surface(

        modifier = modifier.fillMaxWidth(),

        color = MaterialTheme.colorScheme.surface

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth(),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.Center

        ) {

            //--------------------------------------------------
            // Avatar
            //--------------------------------------------------

            Surface(

                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape),

                shape = CircleShape,

                color = ProfileColors.ProfileAvatarBackground

            ) {

                Icon(

                    imageVector = Icons.Default.Person,

                    contentDescription = "Profile",

                    modifier = Modifier
                        .background(Color.Transparent)
                        .padding(24.dp),

                    tint = ProfileColors.ProfileAvatarIcon

                )

            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            //--------------------------------------------------
            // User Name
            //--------------------------------------------------

            Text(

                text = userName,

                style = MaterialTheme.typography.headlineSmall,

                color = ProfileColors.Title

            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            //--------------------------------------------------
            // Tag Line
            //--------------------------------------------------

            Text(

                text = userTagLine,

                style = MaterialTheme.typography.bodyMedium,

                color = ProfileColors.Subtitle

            )

        }

    }

}