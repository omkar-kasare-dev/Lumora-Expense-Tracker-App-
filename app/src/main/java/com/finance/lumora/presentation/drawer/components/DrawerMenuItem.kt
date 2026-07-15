package com.finance.lumora.presentation.drawer.components



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Reusable Navigation Drawer Menu Item.
 *
 * Used for:
 * - Home
 * - Transactions
 * - Categories
 * - Reports
 * - Settings
 * - About
 * etc.
 */
@Composable
fun DrawerMenuItem(

    title: String,

    icon: ImageVector,

    modifier: Modifier = Modifier,

    selected: Boolean = false,

    onClick: () -> Unit

) {

    Surface(

        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(14.dp),

        color =
            if (selected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surface

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),

            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.Start

        ) {

            Icon(

                imageVector = icon,

                contentDescription = title,

                tint =
                    if (selected)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant

            )

            Spacer(
                modifier = Modifier.width(18.dp)
            )

            Text(

                text = title,

                style = MaterialTheme.typography.bodyLarge,

                fontWeight =
                    if (selected)
                        FontWeight.SemiBold
                    else
                        FontWeight.Normal,

                color =
                    if (selected)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurface

            )

        }

    }

}