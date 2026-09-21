package com.finance.lumora.presentation.news
/*
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.finance.lumora.domain.model.NewsScope

@Composable
fun NewsScreen(viewModel: NewsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onIntent(NewsIntent.LoadNews(NewsScope.GLOBAL))
        viewModel.onIntent(NewsIntent.LoadNews(NewsScope.LOCAL))
        viewModel.effect.collect { effect ->
            when (effect) {
                is NewsEffect.OpenBrowser ->
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(effect.url)))
                is NewsEffect.ShowError -> { /* show snackbar */ }
            }
        }
    }

    Column {
        Text("Local Finance News", style = MaterialTheme.typography.titleMedium)
        LazyRow {
            items(state.localNews) { article ->
                NewsCard(article) { viewModel.onIntent(NewsIntent.OpenArticle(article.url)) }
            }
        }
        Text("Global Finance News", style = MaterialTheme.typography.titleMedium)
        LazyColumn {
            items(state.globalNews) { article ->
                NewsCard(article) { viewModel.onIntent(NewsIntent.OpenArticle(article.url)) }
            }
        }
        if (state.isLoading) CircularProgressIndicator()
    }
}

 */




import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.finance.lumora.domain.model.NewsScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    onBackClick: () -> Unit = {},
    viewModel: NewsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onIntent(NewsIntent.LoadNews(NewsScope.GLOBAL))
        viewModel.onIntent(NewsIntent.LoadNews(NewsScope.LOCAL))
        viewModel.effect.collect { effect ->
            when (effect) {
                is NewsEffect.OpenBrowser ->
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(effect.url)))
                is NewsEffect.ShowError -> { /* show snackbar */ }
            }
        }
    }

    // Broadcast Color Palette
    val darkBroadcastBg = Color(0xFFB3D6D6)
    val topBarBg = Color(0xFFB3D6D6)
    val cardBg = Color(0xFFA8B6CD)
    val broadcastRed = Color(0xFFD32F2F)
    val textPrimary = Color(0xFF000407)
    val textSecondary = Color(0xFF94A3B8)
    val accentIndigo = Color(0xFF00010A)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "LUMORA FINANCIAL NETWORK",
                            style = MaterialTheme.typography.labelSmall,
                            color = accentIndigo,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp
                        )
                        Text(
                            text = "Expense & Wire Desk",
                            style = MaterialTheme.typography.titleMedium,
                            color = textPrimary,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = textPrimary
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.onIntent(NewsIntent.LoadNews(NewsScope.GLOBAL))
                            viewModel.onIntent(NewsIntent.LoadNews(NewsScope.LOCAL))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh News",
                            tint = textPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = topBarBg
                )
            )
        },
        containerColor = darkBroadcastBg
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // --- TICKER / BULLETIN BANNER ---
                Surface(
                    color = broadcastRed,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        PulsingLiveBadge(badgeColor = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (state.localNews.isNotEmpty()) {
                                "BULLETIN: ${state.localNews.firstOrNull()?.title ?: ""}"
                            } else {
                                "TRACKING GLOBAL INFLATION & PERSONAL EXPENSE TRENDS..."
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // --- MAIN BROADCAST FEEDS ---
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    // SECTION 1: Local Finance News
                    item {
                        BroadcastSectionHeader(
                            title = "LOCAL FINANCE DISPATCH",
                            subtitle = "Regional economic impacts & consumer alerts",
                            badgeColor = Color(0xFF0EA5E9),
                            textColor = textPrimary
                        )
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(state.localNews) { article ->
                                NewsCard(article = article) {
                                    viewModel.onIntent(NewsIntent.OpenArticle(article.url))
                                }
                            }
                        }
                    }

                    // SECTION 2: Global Finance News
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        BroadcastSectionHeader(
                            title = "GLOBAL MARKET WIRE",
                            subtitle = "Worldwide economic updates affecting your wallet",
                            badgeColor = Color(0xFF10B981),
                            textColor = textPrimary
                        )
                    }

                    items(state.globalNews) { article ->
                        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                            NewsCard(article = article) {
                                viewModel.onIntent(NewsIntent.OpenArticle(article.url))
                            }
                        }
                    }
                }
            }

            // Loading Overlay
            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = cardBg),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(
                                color = accentIndigo,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "FETCHING WIRE DATA...",
                                color = textPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

// --- HELPER COMPONENTS ---

@Composable
private fun PulsingLiveBadge(badgeColor: Color) {
    val infiniteTransition = rememberInfiniteTransition(label = "Pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "AlphaPulse"
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(badgeColor.copy(alpha = alpha))
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "LIVE",
            color = Color.White,
            fontWeight = FontWeight.Black,
            fontSize = 9.sp,
            letterSpacing = 0.8.sp
        )
    }
}

@Composable
private fun BroadcastSectionHeader(
    title: String,
    subtitle: String,
    badgeColor: Color,
    textColor: Color
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(4.dp, 16.dp)
                    .background(badgeColor, RoundedCornerShape(2.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = textColor,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
            )
        }
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF64748B),
            modifier = Modifier.padding(start = 12.dp, top = 2.dp)
        )
    }
}