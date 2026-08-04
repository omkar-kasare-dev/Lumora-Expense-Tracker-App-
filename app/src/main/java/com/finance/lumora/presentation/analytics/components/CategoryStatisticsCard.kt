package com.finance.lumora.presentation.analytics.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance.lumora.domain.analytics.model.CategorySummary
import com.finance.lumora.navigation.BottomNavItem.Companion.items

@Composable
fun CategoryStatisticsCard(
    categories: List<CategorySummary>,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "Category Statistics",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            LazyColumn {

                items(
                    items = categories,
                    key = { it.categoryId }
                ) { category ->

                    CategoryStatisticItem(
                        category = category
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }

            }

        }

    }

}