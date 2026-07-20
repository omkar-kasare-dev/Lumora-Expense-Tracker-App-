package com.finance.lumora.presentation.transaction.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance.lumora.domain.model.Category
import com.finance.lumora.domain.model.Transaction

/**
 * Adds transaction items to a LazyColumn.
 *
 * This is implemented as a LazyListScope extension so it can be
 * composed inside a parent LazyColumn, avoiding nested scrolling.
 */
fun LazyListScope.transactionList(

    transactions: List<Transaction>,

    categories: List<Category>,

    onEditClick: (Transaction) -> Unit,

    onDeleteClick: (Transaction) -> Unit

) {

    items(

        items = transactions,

        key = { transaction ->
            transaction.id
        }

    ) { transaction ->

        val category = categories.firstOrNull {

            it.id == transaction.categoryId

        }

        TransactionItem(

            transaction = transaction,

            category = category,

            onEditClick = onEditClick,

            onDeleteClick = onDeleteClick

        )
        Spacer(
            modifier = Modifier.height(12.dp)
        )

    }

}