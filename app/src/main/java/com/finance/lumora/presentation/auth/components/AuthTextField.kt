package com.finance.lumora.presentation.auth.components



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AuthTextField(

    value: String,

    onValueChange: (String) -> Unit,

    label: String,

    leadingIcon: ImageVector,

    modifier: Modifier = Modifier,

    placeholder: String = "",

    keyboardType: KeyboardType = KeyboardType.Text,

    imeAction: ImeAction = ImeAction.Next,

    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,

    enabled: Boolean = true,

    readOnly: Boolean = false,

    isError: Boolean = false,

    errorMessage: String? = null

) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        OutlinedTextField(

            value = value,

            onValueChange = onValueChange,

            modifier = Modifier.fillMaxWidth(),

            label = {

                Text(label)

            },

            placeholder = {

                if (placeholder.isNotBlank()) {

                    Text(placeholder)

                }

            },

            leadingIcon = {

                Icon(

                    imageVector = leadingIcon,

                    contentDescription = null

                )

            },

            singleLine = true,

            enabled = enabled,

            readOnly = readOnly,

            isError = isError,

            shape = RoundedCornerShape(16.dp),

            keyboardOptions = KeyboardOptions(

                keyboardType = keyboardType,

                imeAction = imeAction,

                capitalization = capitalization

            ),

            colors = OutlinedTextFieldDefaults.colors()

        )

        if (isError && !errorMessage.isNullOrBlank()) {

            Text(

                text = errorMessage,

                color = MaterialTheme.colorScheme.error,

                style = MaterialTheme.typography.bodySmall,

                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 4.dp
                )

            )

        }

    }

}