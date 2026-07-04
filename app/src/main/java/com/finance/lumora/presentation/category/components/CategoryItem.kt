package com.finance.lumora.presentation.category.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.finance.lumora.domain.model.Category

@Composable
fun CategoryItem(

    category: Category,

    modifier: Modifier = Modifier,

    onClick: () -> Unit = {},

    onDeleteClick: () -> Unit = {}

) {

    ElevatedCard(

        modifier = modifier.fillMaxWidth(),

        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        ),

        onClick = onClick

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.SpaceBetween

        ) {

            // -----------------------------
            // Left Section
            // -----------------------------

            Row(

                verticalAlignment = Alignment.CenterVertically

            ) {

                Box(

                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(Color(category.color)),

                    contentAlignment = Alignment.Center

                ) {

                    Icon(

                        imageVector = categoryIcon(category.icon),

                        contentDescription = category.name,

                        tint = Color.White

                    )

                }

                Spacer(
                    modifier = Modifier.width(16.dp)
                )

                Column {

                    Text(

                        text = category.name,

                        style = MaterialTheme.typography.titleMedium

                    )

                    Text(

                        text = category.icon,

                        style = MaterialTheme.typography.bodySmall,

                        color = MaterialTheme.colorScheme.onSurfaceVariant

                    )

                }

            }

            // -----------------------------
            // Right Section
            // -----------------------------

            Row(

                verticalAlignment = Alignment.CenterVertically

            ) {

                // Delete Button

                IconButton(

                    onClick = onDeleteClick

                ) {

                    Icon(

                        imageVector = Icons.Default.DeleteOutline,

                        contentDescription = "Delete Category",

                        tint = MaterialTheme.colorScheme.error

                    )

                }

                // Edit Button

                IconButton(

                    onClick = onClick

                ) {

                    Icon(

                        imageVector = Icons.Default.KeyboardArrowRight,

                        contentDescription = "Edit Category"

                    )

                }

            }

        }

    }

}