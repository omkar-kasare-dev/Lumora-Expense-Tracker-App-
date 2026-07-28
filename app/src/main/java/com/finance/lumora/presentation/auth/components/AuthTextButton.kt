package com.finance.lumora.presentation.auth.components



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

@Composable
fun AuthTextButton(

    prefixText: String,

    actionText: String,

    onClick: () -> Unit,

    modifier: Modifier = Modifier

) {

    Row(

        modifier = modifier.fillMaxWidth(),

        horizontalArrangement = Arrangement.Center

    ) {

        Text(

            text = prefixText,

            style = MaterialTheme.typography.bodyMedium,

            color = MaterialTheme.colorScheme.onSurfaceVariant

        )

        val annotatedString = buildAnnotatedString {

            pushStringAnnotation(

                tag = "ACTION",

                annotation = "ACTION"

            )

            withStyle(

                SpanStyle(

                    color = MaterialTheme.colorScheme.primary,

                    fontWeight = FontWeight.SemiBold,

                    textDecoration = TextDecoration.None

                )

            ) {

                append(actionText)

            }

            pop()

        }

        ClickableText(

            text = annotatedString,

            style = MaterialTheme.typography.bodyMedium,

            onClick = { offset ->

                annotatedString

                    .getStringAnnotations(

                        tag = "ACTION",

                        start = offset,

                        end = offset

                    )

                    .firstOrNull()

                    ?.let {

                        onClick()

                    }

            }

        )

    }

}