package com.finance.lumora.presentation.news.components

import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.finance.lumora.domain.model.NewsArticle

// Minimal Warm Cream & White Card Palette
private object CardPalette {
    val background = Color(0xFFFFFFFF)       // Pure White Card Canvas
    val imagePlaceholder = Color(0xFFF3EFF8) // Soft Cashmere Grey Placeholder
    val textPrimary = Color(0xFF121110)      // Sharp Deep Obsidian Typography
    val textSecondary = Color(0xFF5E5C56)    // Refined Muted Taupe Secondary Text
    val badgeBg = Color(0xFFF4F1EA)          // Soft Cream Satin Badge
    val border = Color(0xFFECE8DF)           // Subtle Architectural Line Border
    val accentDot = Color(0xFF9E7A47)        // Champagne Gold / Bronze Accent Dot
}

/**
 * The caller decides the width: in the horizontal "local" row pass Modifier.width(280.dp),
 * in the vertical "global" list pass Modifier.fillMaxWidth().
 * (A LazyRow gives children unbounded width, so without a fixed width the headline
 * would never wrap and each card would stretch to the length of its title.)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsCard(
    article: NewsArticle,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val timeAgo = remember(article.publishedAt) {
        val now = System.currentTimeMillis()
        DateUtils.getRelativeTimeSpanString(
            minOf(article.publishedAt, now),
            now,
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.FORMAT_ABBREV_RELATIVE
        ).toString()
    }

    // Some providers repeat the headline as the description; don't show it twice.
    val showSnippet = article.snippet.isNotBlank() &&
            !article.title.contains(article.snippet.take(30), ignoreCase = true)

    Card(
        onClick = onClick,
        modifier = modifier.border(1.dp, CardPalette.border, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardPalette.background),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // The image slot always exists, so cards keep a consistent layout with or without a picture.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(CardPalette.imagePlaceholder)
            ) {
                article.imageUrl?.let { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = null, // decorative: the headline below says it all
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Surface(
                    color = CardPalette.background.copy(alpha = 0.92f),
                    shape = RoundedCornerShape(100.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopStart)
                        .border(1.dp, CardPalette.border, RoundedCornerShape(100.dp))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(CardPalette.accentDot)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "WIRE DESK",
                            color = CardPalette.textPrimary,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = article.title,
                style = MaterialTheme.typography.titleSmall,
                color = CardPalette.textPrimary,
                fontWeight = FontWeight.Bold,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 19.sp
            )

            if (showSnippet) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = article.snippet,
                    style = MaterialTheme.typography.bodySmall,
                    color = CardPalette.textSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (article.source.isNotBlank()) {
                    Surface(
                        color = CardPalette.badgeBg,
                        shape = RoundedCornerShape(100.dp),
                        modifier = Modifier
                            .border(1.dp, CardPalette.border, RoundedCornerShape(100.dp))
                            .weight(1f, fill = false)
                    ) {
                        Text(
                            text = article.source.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = CardPalette.textPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 9.sp,
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = timeAgo,
                    style = MaterialTheme.typography.labelSmall,
                    color = CardPalette.textSecondary,
                    fontSize = 10.sp,
                    maxLines = 1
                )
            }
        }
    }
}