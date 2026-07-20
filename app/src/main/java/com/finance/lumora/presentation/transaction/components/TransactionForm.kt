package com.finance.lumora.presentation.transaction.components



import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance.lumora.data.local.enums.TransactionType
import com.finance.lumora.domain.model.Category
import com.finance.lumora.presentation.transaction.state.TransactionState

/**
 * Transaction input form.
 *
 * This composable only arranges UI components.
 * All business logic remains inside TransactionViewModel.
 */


@Composable
fun TransactionForm(
    state: TransactionState,
    onAmountChanged: (String) -> Unit,
    onTypeChanged: (TransactionType) -> Unit,
    onCategoryChanged: (Category) -> Unit,
    onAddCategoryClick: () -> Unit,
    onDateChanged: (Long) -> Unit,
    onNoteChanged: (String) -> Unit,
    onSaveClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.d(
        "TRANSACTION_FORM",
        "Categories received: ${state.categories.size}"
    )

    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            AmountTextField(
                amount = state.amount,
                onAmountChanged = onAmountChanged
            )

            TransactionTypeSelector(
                selectedType = state.transactionType,
                onTypeSelected = onTypeChanged
            )

            CategoryDropDown(
                categories = state.categories,
                selectedCategory = state.selectedCategory,
                onCategorySelected = onCategoryChanged,
                onAddCategoryClick = onAddCategoryClick
            )

            DatePickerField(
                selectedDate = state.selectedDate,
                onDateSelected = onDateChanged
            )

            NoteTextField(
                note = state.note,
                onNoteChanged = onNoteChanged
            )

            SaveTransactionButton(
                isEditMode = state.isEditMode,
                onClick = onSaveClicked
            )
        }
    }
}