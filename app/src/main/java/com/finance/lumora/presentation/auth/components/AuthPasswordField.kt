package com.finance.lumora.presentation.auth.components



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AuthPasswordField(

    value: String,

    onValueChange: (String) -> Unit,

    passwordVisible: Boolean,

    onTogglePasswordVisibility: () -> Unit,

    modifier: Modifier = Modifier,

    label: String = "Password",

    placeholder: String = "",

    imeAction: ImeAction = ImeAction.Done,

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

            singleLine = true,

            enabled = enabled,

            readOnly = readOnly,

            isError = isError,

            shape = RoundedCornerShape(16.dp),

            leadingIcon = {

                Icon(

                    imageVector = Icons.Default.Lock,

                    contentDescription = null

                )

            },

            trailingIcon = {

                IconButton(

                    onClick = onTogglePasswordVisibility

                ) {

                    Icon(

                        imageVector = if (passwordVisible)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff,

                        contentDescription =
                            if (passwordVisible)
                                "Hide Password"
                            else
                                "Show Password"

                    )

                }

            },

            visualTransformation =

                if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),

            keyboardOptions = KeyboardOptions(

                keyboardType = KeyboardType.Password,

                imeAction = imeAction

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