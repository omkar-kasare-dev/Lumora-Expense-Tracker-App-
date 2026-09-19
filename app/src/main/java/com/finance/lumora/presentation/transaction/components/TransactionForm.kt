package com.finance.lumora.presentation.transaction.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.lumora.data.local.enums.TransactionType
import com.finance.lumora.domain.model.Category
import com.finance.lumora.domain.model.SubCategory
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
    onSubCategoryChanged: (SubCategory) -> Unit,
    onAddSubCategoryClick: () -> Unit,
    onDateChanged: (Long) -> Unit,
    onNoteChanged: (String) -> Unit,
    onSaveClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.d(
        "TRANSACTION_FORM",
        "Categories received: ${state.categories.size}"
    )

    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = 0.8.dp,
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f)
        ),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            //--------------------------------------------------
            // Section 1: Transaction Type & Amount
            //--------------------------------------------------
            FormSectionHeader(
                title = "Amount & Type",
                icon = Icons.Outlined.Payments
            )

            AmountTextField(
                amount = state.amount,
                onAmountChanged = onAmountChanged
            )

            TransactionTypeSelector(
                selectedType = state.transactionType,
                onTypeSelected = onTypeChanged
            )

            HorizontalDivider(
                thickness = 0.6.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
                modifier = Modifier.padding(vertical = 2.dp)
            )

            //--------------------------------------------------
            // Section 2: Category & SubCategory
            //--------------------------------------------------
            FormSectionHeader(
                title = "Classification",
                icon = Icons.Outlined.Category
            )

            CategoryDropDown(
                categories = state.categories,
                selectedCategory = state.selectedCategory,
                onCategorySelected = onCategoryChanged,
                onAddCategoryClick = onAddCategoryClick
            )

            SubCategoryDropDown(
                subCategories = state.subCategories,
                selectedSubCategory = state.selectedSubCategory,
                enabled = state.selectedCategory != null,
                onSubCategorySelected = onSubCategoryChanged,
                onAddSubCategoryClick = onAddSubCategoryClick
            )

            HorizontalDivider(
                thickness = 0.6.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
                modifier = Modifier.padding(vertical = 2.dp)
            )

            //--------------------------------------------------
            // Section 3: Date, Notes & Actions
            //--------------------------------------------------
            FormSectionHeader(
                title = "Details",
                icon = Icons.Outlined.Description
            )

            DatePickerField(
                selectedDate = state.selectedDate,
                onDateSelected = onDateChanged
            )

            NoteTextField(
                note = state.note,
                onNoteChanged = onNoteChanged
            )

            Spacer(modifier = Modifier.height(6.dp))

            SaveTransactionButton(
                isEditMode = state.isEditMode,
                onClick = onSaveClicked
            )
        }
    }
}

@Composable
private fun FormSectionHeader(
    title: String,
    icon: ImageVector
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 11.5.sp,
                letterSpacing = 0.2.sp
            ),
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}