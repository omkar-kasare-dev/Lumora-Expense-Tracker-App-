package com.finance.lumora.presentation.category.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorPicker(

    selectedColor: Long,

    onColorSelected: (Long) -> Unit

) {

    LazyVerticalGrid(

        columns = GridCells.Fixed(4),

        modifier = Modifier.fillMaxWidth(),

        userScrollEnabled = false,

        horizontalArrangement = Arrangement.spacedBy(12.dp),

        verticalArrangement = Arrangement.spacedBy(12.dp),

        contentPadding = PaddingValues(4.dp)

    ) {

        items(CategoryColors) { item ->

            //val colorLong = item.color.value.toLong()
            val colorLong = item.colorLong

            val isSelected = colorLong == selectedColor

            Box(

                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .background(item.color)
                    .border(

                        width = if (isSelected) 3.dp else 1.dp,

                        color = if (isSelected)
                            MaterialTheme.colorScheme.onSurface
                        else
                            MaterialTheme.colorScheme.outline,

                        shape = CircleShape

                    )
                    .clickable {

                        onColorSelected(colorLong)

                    },

                contentAlignment = Alignment.Center

            ) {

                if (isSelected) {

                    Icon(

                        imageVector = Icons.Default.Check,

                        contentDescription = null,

                        tint = Color.White

                    )

                }

            }

        }

    }

}