package com.finance.lumora.presentation.auth.components



import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AuthPrimaryButton(

    text: String,

    onClick: () -> Unit,

    modifier: Modifier = Modifier,

    enabled: Boolean = true,

    loading: Boolean = false

) {

    Button(

        onClick = onClick,

        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),

        enabled = enabled && !loading,

        shape = RoundedCornerShape(16.dp),

        colors = ButtonDefaults.buttonColors(

            containerColor = MaterialTheme.colorScheme.primary,

            contentColor = MaterialTheme.colorScheme.onPrimary,

            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,

            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant

        )

    ) {

        if (loading) {

            CircularProgressIndicator(

                modifier = Modifier.size(22.dp),

                strokeWidth = 2.dp,

                color = MaterialTheme.colorScheme.onPrimary

            )

        } else {

            Text(

                text = text,

                style = MaterialTheme.typography.titleMedium

            )

        }

    }

}