package com.finance.lumora.presentation.ai.components
/* Main
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cyclone
import androidx.compose.material.icons.filled.WavingHand
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AurixWelcomeSection(
    onQuestionSelected: (String) -> Unit
) {

    // ---------------------------------------------------------
    // Subtle avatar breathing animation
    // ---------------------------------------------------------

    val infiniteTransition =
        rememberInfiniteTransition(
            label = "aurix_avatar_animation"
        )

    val pulseScale by infiniteTransition.animateFloat(

        initialValue = 0.94f,

        targetValue = 1.06f,

        animationSpec = infiniteRepeatable(

            animation = tween(
                durationMillis = 2200,
                easing = FastOutSlowInEasing
            ),

            repeatMode = RepeatMode.Reverse
        ),

        label = "aurix_avatar_scale"
    )

    // ---------------------------------------------------------
    // Suggested questions
    // ---------------------------------------------------------

    val suggestedQuestions = listOf(
        "How much did I spend this month?",
        "Show my biggest expenses",
        "How am I doing with my budget?"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 160.dp,
                bottom = 18.dp
            ),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // -----------------------------------------------------
        // Greeting
        // -----------------------------------------------------

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Hi, I'm AURIX",

                style = MaterialTheme.typography.headlineSmall,

                color = Color.White,

                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.width(7.dp)
            )

            Icon(
                imageVector = Icons.Default.WavingHand,

                contentDescription = null,

                tint = Color(0xFFFFD54F),

                modifier = Modifier.size(21.dp)
            )
        }

        Spacer(
            modifier = Modifier.size(10.dp)
        )

        Text(
            text = "Ask about your spending, budgets, or transactions.",

            style = MaterialTheme.typography.bodyMedium,

            color = Color.White.copy(
                alpha = 0.65f
            ),

            textAlign = TextAlign.Center,

            modifier = Modifier.padding(
                horizontal = 24.dp
            )
        )

        Spacer(
            modifier = Modifier.size(22.dp)
        )

        // -----------------------------------------------------
        // Suggested questions
        // -----------------------------------------------------

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),

            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {

            suggestedQuestions.forEach { question ->

                AurixSuggestionCard(
                    text = question,

                    onClick = {
                        onQuestionSelected(question)
                    }
                )
            }
        }
    }
}



 */


import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cyclone
import androidx.compose.material.icons.filled.WavingHand
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AurixWelcomeSection(
    smartInsightsEnabled: Boolean,
    onQuestionSelected: (String) -> Unit
) {

    val infiniteTransition =
        rememberInfiniteTransition(
            label = "aurix_avatar_animation"
        )

    val pulseScale by infiniteTransition.animateFloat(

        initialValue = 0.94f,

        targetValue = 1.06f,

        animationSpec = infiniteRepeatable(

            animation = tween(
                durationMillis = 2200,
                easing = FastOutSlowInEasing
            ),

            repeatMode = RepeatMode.Reverse
        ),

        label = "aurix_avatar_scale"
    )

    val suggestedQuestions = listOf(
        "How much did I spend this month?",
        "Show my biggest expenses",
        "How am I doing with my budget?"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 160.dp,
                bottom = 18.dp
            ),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Hi, I'm AURIX",

                style = MaterialTheme.typography.headlineSmall,

                color = Color.White,

                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.width(7.dp)
            )

            Icon(
                imageVector = Icons.Default.WavingHand,

                contentDescription = null,

                tint = Color(0xFFFFD54F),

                modifier = Modifier.size(21.dp)
            )
        }

        Spacer(
            modifier = Modifier.size(10.dp)
        )

        Text(
            text = "Ask about your spending, budgets, or transactions.",

            style = MaterialTheme.typography.bodyMedium,

            color = Color.White.copy(
                alpha = 0.65f
            ),

            textAlign = TextAlign.Center,

            modifier = Modifier.padding(
                horizontal = 24.dp
            )
        )

        Spacer(
            modifier = Modifier.size(22.dp)
        )

        // Smart Insights toggle gates the suggestion chips specifically -
        // when off, the welcome greeting still shows, just without
        // proactive question suggestions.
        if (smartInsightsEnabled) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),

                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {

                suggestedQuestions.forEach { question ->

                    AurixSuggestionCard(
                        text = question,

                        onClick = {
                            onQuestionSelected(question)
                        }
                    )
                }
            }
        }
    }
}