package com.finance.lumora.presentation.auth.components



import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.finance.lumora.R
import kotlinx.coroutines.delay

@Composable
fun AuthHeader(

    title: String,

    subtitle: String,

    modifier: Modifier = Modifier

) {

    var visible by remember {

        mutableStateOf(false)

    }

    LaunchedEffect(Unit) {

        delay(150)

        visible = true

    }

    AnimatedVisibility(

        visible = visible,

        enter = fadeIn() +
                scaleIn(initialScale = 0.90f) +
                slideInVertically {

                    -it / 3

                }

    ) {

        Column(

            modifier = modifier.fillMaxWidth(),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.Center

        ) {

            Image(

                painter = painterResource(

                    id = R.drawable.lumora1_logo
                ),

                contentDescription = "Lumora Logo",

                modifier = Modifier.size(96.dp)

            )

            Spacer(

                modifier = Modifier.height(20.dp)

            )

            Text(

                text = "Lumora",

                style = MaterialTheme.typography.headlineMedium,

                fontWeight = FontWeight.Bold,

                textAlign = TextAlign.Center

            )

            Spacer(

                modifier = Modifier.height(6.dp)

            )

            Text(

                text = "Smart Expense Tracker",

                style = MaterialTheme.typography.bodyMedium,

                color = MaterialTheme.colorScheme.onSurfaceVariant,

                textAlign = TextAlign.Center

            )

            Spacer(

                modifier = Modifier.height(32.dp)

            )

            Text(

                text = title,

                style = MaterialTheme.typography.headlineSmall,

                fontWeight = FontWeight.SemiBold,

                textAlign = TextAlign.Center

            )

            Spacer(

                modifier = Modifier.height(8.dp)

            )

            Text(

                text = subtitle,

                style = MaterialTheme.typography.bodyMedium,

                color = MaterialTheme.colorScheme.onSurfaceVariant,

                textAlign = TextAlign.Center

            )

        }

    }

}