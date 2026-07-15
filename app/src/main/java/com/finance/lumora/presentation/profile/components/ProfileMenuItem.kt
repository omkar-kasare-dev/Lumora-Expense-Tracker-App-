package com.finance.lumora.presentation.profile.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.finance.lumora.presentation.profile.components.ProfileColors

/**
 * Reusable menu item used throughout
 * the Profile screen.
 */
@Composable
fun ProfileMenuItem(

    title: String,

    icon: ImageVector,

    modifier: Modifier = Modifier,

    subTitle: String? = null,

    showArrow: Boolean = true,

    onClick: () -> Unit = {}

) {

    Surface(

        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },

        color = MaterialTheme.colorScheme.surface

    ) {

        Column {

            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 16.dp
                    ),

                verticalAlignment = Alignment.CenterVertically,

                horizontalArrangement = Arrangement.SpaceBetween

            ) {

                //--------------------------------------------------
                // Left Side
                //--------------------------------------------------

                Row(

                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Icon(

                        imageVector = icon,

                        contentDescription = title,

                        tint = ProfileColors.LeadingIcon

                    )

                    Spacer(
                        modifier = Modifier.width(18.dp)
                    )

                    Column {

                        Text(

                            text = title,

                            style = MaterialTheme.typography.titleMedium,

                            color = ProfileColors.Title

                        )

                        if (subTitle != null) {

                            Text(

                                text = subTitle,

                                style = MaterialTheme.typography.bodySmall,

                                color = ProfileColors.Subtitle

                            )

                        }

                    }

                }

                //--------------------------------------------------
                // Arrow
                //--------------------------------------------------

                if (showArrow) {

                    Icon(

                        imageVector = Icons.Default.KeyboardArrowRight,

                        contentDescription = null,

                        tint = ProfileColors.TrailingIcon

                    )

                }

            }

            HorizontalDivider(

                color = ProfileColors.Divider

            )

        }

    }

}