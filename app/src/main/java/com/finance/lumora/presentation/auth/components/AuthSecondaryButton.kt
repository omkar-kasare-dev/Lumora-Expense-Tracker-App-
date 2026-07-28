package com.finance.lumora.presentation.auth.components



import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp



@Composable
fun AuthSecondaryButton(

    text: String,

    onClick: () -> Unit,

    modifier: Modifier = Modifier,

    enabled: Boolean = true,

    loading: Boolean = false,

    icon: ImageVector? = null

) {

    OutlinedButton(

        onClick = onClick,

        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),

        enabled = enabled && !loading,

        shape = RoundedCornerShape(16.dp),
/*
        colors = OutlinedButtonDefaults.outlinedButtonColors(

            contentColor = MaterialTheme.colorScheme.primary

        )

 */



    ) {

        if (loading) {

            CircularProgressIndicator(

                modifier = Modifier.size(22.dp),

                strokeWidth = 2.dp,

                color = MaterialTheme.colorScheme.primary

            )

        } else {

            Row(

                verticalAlignment = Alignment.CenterVertically

            ) {

                icon?.let {

                    Icon(

                        imageVector = it,

                        contentDescription = null,

                        modifier = Modifier.size(20.dp)

                    )

                }

                if (icon != null) {

                    androidx.compose.foundation.layout.Spacer(
                        modifier = Modifier.size(8.dp)
                    )

                }

                Text(

                    text = text,

                    style = MaterialTheme.typography.titleMedium

                )

            }

        }

    }

}