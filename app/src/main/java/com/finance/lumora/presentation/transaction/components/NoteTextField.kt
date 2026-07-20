package com.finance.lumora.presentation.transaction.components



import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp


/**
 * Transaction note input field.
 *
 * Optional field used for additional
 * transaction information.
 *
 * UI responsibility only.
 */
@Composable
fun NoteTextField(

    note: String,

    onNoteChanged: (
        String
    ) -> Unit,

    modifier: Modifier = Modifier

) {


    OutlinedTextField(

        value = note,

        onValueChange = {

            onNoteChanged(it)

        },

        modifier = modifier
            .fillMaxWidth()
            .heightIn(
                min = 56.dp,
                max = 120.dp
            ),


        label = {

            Text(
                text = "Note"
            )

        },


        placeholder = {

            Text(
                text = "Add description (optional)"
            )

        },


        keyboardOptions = KeyboardOptions(

            capitalization = KeyboardCapitalization.Sentences

        ),


        maxLines = 4,

        singleLine = false

    )

}