package com.finance.lumora.presentation.drawer.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Model representing one Drawer Menu Item.
 */
data class DrawerMenuData(

    val title: String,

    val icon: ImageVector,

    val selected: Boolean = false,

    val onClick: () -> Unit

)

/**
 * Reusable Navigation Drawer Section.
 *
 * Example:
 *
 * Main
 *  • Home
 *  • Transactions
 *  • Categories
 *
 * Tools
 *  • Backup
 *  • Export
 */
@Composable
fun DrawerMenuSection(

    title: String,

    menuItems: List<DrawerMenuData>,

    modifier: Modifier = Modifier

) {

    Column(
        modifier = modifier
    ) {

        //--------------------------------------------------
        // Section Title
        //--------------------------------------------------

        Text(

            text = title,

            modifier = Modifier.padding(
                horizontal = 20.dp,
                vertical = 8.dp
            ),

            style = MaterialTheme.typography.labelLarge,

            fontWeight = FontWeight.Bold,

            color = MaterialTheme.colorScheme.primary

        )

        //--------------------------------------------------
        // Menu Items
        //--------------------------------------------------

        menuItems.forEach { item ->

            DrawerMenuItem(

                modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 2.dp
                ),

                title = item.title,

                icon = item.icon,

                selected = item.selected,

                onClick = item.onClick

            )

        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

    }

}