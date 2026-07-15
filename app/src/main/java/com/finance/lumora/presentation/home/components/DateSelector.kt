package com.finance.lumora.presentation.home.components



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Date selector displayed on Home Screen.
 *
 * Example:
 *
 * <     July 2026     >
 */
@Composable
fun DateSelector(

    currentDate: String,

    modifier: Modifier = Modifier,

    onPreviousClick: () -> Unit = {},

    onNextClick: () -> Unit = {}

) {

    Card(

        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),

        shape = RoundedCornerShape(16.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),

            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.SpaceBetween

        ) {

            IconButton(
                onClick = onPreviousClick
            ) {

                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "Previous"
                )

            }

            Text(

                text = currentDate,

                style = MaterialTheme.typography.titleMedium

            )

            IconButton(
                onClick = onNextClick
            ) {

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Next"
                )

            }

        }

    }

}