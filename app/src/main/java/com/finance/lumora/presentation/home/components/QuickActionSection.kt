package com.finance.lumora.presentation.home.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.finance.lumora.presentation.home.model.QuickAction

@Composable
fun QuickActionSection(

    modifier: Modifier = Modifier,

    onAddIncome: () -> Unit = {},

    onAddExpense: () -> Unit = {},

    onAddGoal: () -> Unit = {},

    onAddNote: () -> Unit = {}

) {

    val quickActions = listOf(

        Triple(
            QuickAction(
                title = "Add Income",
                icon = Icons.Default.AddCard,
                backgroundColor = Color(0xFFDDF6E5),
                iconTint = Color(0xFF2EAD55)
            ),
            onAddIncome,
            "income"
        ),

        Triple(
            QuickAction(
                title = "Add Expense",
                icon = Icons.Default.Payments,
                backgroundColor = Color(0xFFFFE4E4),
                iconTint = Color(0xFFE53935)
            ),
            onAddExpense,
            "expense"
        ),

        Triple(
            QuickAction(
                title = "Add Goal",
                icon = Icons.Default.Flag,
                backgroundColor = Color(0xFFE9E1FF),
                iconTint = Color(0xFF6C4CE3)
            ),
            onAddGoal,
            "goal"
        ),

        Triple(
            QuickAction(
                title = "Add Note",
                icon = Icons.Default.EditNote,
                backgroundColor = Color(0xFFFFF3D6),
                iconTint = Color(0xFFE69500)
            ),
            onAddNote,
            "note"
        )

    )

    Column(
        modifier = modifier
    ) {

        Text(

            text = "Quick Actions",

            style = MaterialTheme.typography.titleLarge

        )

        LazyVerticalGrid(

            columns = GridCells.Fixed(2),

            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),

            userScrollEnabled = false,

            horizontalArrangement = Arrangement.spacedBy(16.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {

            items(quickActions) { (action, click, _) ->

                QuickActionCard(

                    action = action,

                    onClick = click

                )

            }

        }

    }

}