package com.finance.lumora.presentation.transaction.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import com.finance.lumora.data.local.enums.TransactionType
import com.finance.lumora.domain.model.Category
import com.finance.lumora.domain.model.SubCategory
import com.finance.lumora.presentation.transaction.state.TransactionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionDialog(
    showDialog: Boolean,
    state: TransactionState,
    onAmountChanged: (String) -> Unit,
    onTypeChanged: (TransactionType) -> Unit,
    onCategoryChanged: (Category) -> Unit,
    onSubCategoryChanged: (SubCategory) -> Unit,
    onDateChanged: (Long) -> Unit,
    onNoteChanged: (String) -> Unit,
    onSaveClicked: () -> Unit,
    onAddCategoryClick: () -> Unit,
    onAddSubCategoryClick: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {

    if (showDialog) {
        // BasicAlertDialog allows nested popups like Dropdowns and DatePickers to render properly
        BasicAlertDialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                usePlatformDefaultWidth = true,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            modifier = modifier.fillMaxWidth()
        ) {
            /*
            TransactionForm(
                state = state,
                onAmountChanged = onAmountChanged,
                onTypeChanged = onTypeChanged,
                onCategoryChanged = onCategoryChanged,
                onAddCategoryClick = {
                    Log.d("CATEGORY_DIALOG", "Add Category Clicked")
                    onAddCategoryClick()
                },
                onDateChanged = onDateChanged,
                onNoteChanged = onNoteChanged,
                onSaveClicked = {
                    onSaveClicked()
                    onDismissRequest() // Closes the dialog after hitting save
                }
            )

             */

            TransactionForm(

                state = state,

                onAmountChanged = onAmountChanged,

                onTypeChanged = onTypeChanged,

                onCategoryChanged = onCategoryChanged,

                onSubCategoryChanged = onSubCategoryChanged,

                onAddCategoryClick = onAddCategoryClick,

                onAddSubCategoryClick = onAddSubCategoryClick,

                onDateChanged = onDateChanged,

                onNoteChanged = onNoteChanged,

                onSaveClicked = {
                    onSaveClicked()
                    onDismissRequest()
                }

            )
        }
    }
}