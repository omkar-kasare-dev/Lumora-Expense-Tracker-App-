package com.finance.lumora.presentation.news
/*
import android.content.ActivityNotFoundException
import android.content.Context
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.finance.lumora.domain.model.NewsArticle
import com.finance.lumora.domain.model.NewsScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private object NewsPalette {
    val background = Color(0xFFB3D6D6)
    val topBar = Color(0xFFB3D6D6)
    val bulletinRed = Color(0xFFD32F2F)
    val textPrimary = Color(0xFF000407)
    val textSecondary = Color(0xFF334155) // readable on the teal background (~6.6:1)
    val accent = Color(0xFF00010A)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    onBackClick: () -> Unit = {},
    viewModel: NewsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    // Loading is started by the ViewModel, so this only listens for one-off effects.
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is NewsEffect.OpenBrowser -> {
                    if (!openLink(context, effect.url)) {
                        launch { snackbarHostState.showSnackbar("Can't open this link.") }
                    }
                }
                is NewsEffect.ShowError -> {
                    launch { snackbarHostState.showSnackbar(effect.message) }
                }
            }
        }
    }

    val onOpen: (NewsArticle) -> Unit = { article ->
        viewModel.onIntent(NewsIntent.OpenArticle(article.url))
    }

    // Headlines that rotate in the red bulletin bar (local first, then global).
    val tickerItems = remember(state.localNews, state.globalNews) {
        state.localNews.take(3) + state.globalNews.take(3)
    }
    var tickerIndex by remember { mutableStateOf(0) }
    LaunchedEffect(tickerItems.size) {
        tickerIndex = 0
        if (tickerItems.size > 1) {
            while (true) {
                delay(5_000)
                tickerIndex = (tickerIndex + 1) % tickerItems.size
            }
        }
    }
    val currentBulletin = tickerItems.getOrNull(tickerIndex)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "LUMORA FINANCIAL NETWORK",
                            style = MaterialTheme.typography.labelSmall,
                            color = NewsPalette.accent,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp
                        )
                        Text(
                            text = "Expense & Wire Desk",
                            style = MaterialTheme.typography.titleMedium,
                            color = NewsPalette.textPrimary,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = NewsPalette.textPrimary
                        )
                    }
                },
                actions = {
                    IconButton(
                        enabled = !state.isLoading,
                        onClick = {
                            viewModel.onIntent(NewsIntent.Refresh(NewsScope.GLOBAL))
                            viewModel.onIntent(NewsIntent.Refresh(NewsScope.LOCAL))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh news",
                            tint = NewsPalette.textPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = NewsPalette.topBar
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = NewsPalette.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            BulletinBar(
                text = currentBulletin?.let { "BULLETIN: ${it.title}" }
                    ?: "Tracking global markets and your local economy...",
                onClick = currentBulletin?.let { article -> { onOpen(article) } }
            )

            // A thin bar instead of a full-screen dimmer: saved stories stay usable while refreshing.
            if (state.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = NewsPalette.accent,
                    trackColor = Color.Transparent
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                // --- LOCAL ---
                item(key = "local_header") {
                    BroadcastSectionHeader(
                        title = "LOCAL FINANCE DISPATCH",
                        subtitle = "Indian markets and companies in the news",
                        badgeColor = Color(0xFF0EA5E9)
                    )
                }
                item(key = "local_content") {
                    if (state.localNews.isEmpty()) {
                        EmptyHint(
                            if (state.isLocalLoading) "Loading local stories..."
                            else "No local stories yet. Check your connection and tap refresh."
                        )
                    } else {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(state.localNews, key = { it.id }) { article ->
                                NewsCard(
                                    article = article,
                                    modifier = Modifier.width(280.dp),
                                    onClick = { onOpen(article) }
                                )
                            }
                        }
                    }
                }

                // --- GLOBAL ---
                item(key = "global_header") {
                    Spacer(modifier = Modifier.height(16.dp))
                    BroadcastSectionHeader(
                        title = "GLOBAL MARKET WIRE",
                        subtitle = "Worldwide market news",
                        badgeColor = Color(0xFF10B981)
                    )
                }
                if (state.globalNews.isEmpty()) {
                    item(key = "global_empty") {
                        EmptyHint(
                            if (state.isGlobalLoading) "Loading global stories..."
                            else "No global stories yet. Check your connection and tap refresh."
                        )
                    }
                } else {
                    items(state.globalNews, key = { it.id }) { article ->
                        NewsCard(
                            article = article,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                            onClick = { onOpen(article) }
                        )
                    }
                }
            }
        }
    }
}

/** Opens http(s) links only, and never crashes if no browser is installed. */
private fun openLink(context: Context, url: String): Boolean {
    val uri = runCatching { Uri.parse(url) }.getOrNull() ?: return false
    if (uri.scheme != "https" && uri.scheme != "http") return false
    return try {
        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
        true
    } catch (e: ActivityNotFoundException) {
        false
    }
}

// --- HELPER COMPONENTS ---

@Composable
private fun BulletinBar(text: String, onClick: (() -> Unit)?) {
    Surface(
        color = NewsPalette.bulletinRed,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            PulsingLiveBadge(badgeColor = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

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
    badgeColor: Color
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.semantics { heading() }
        ) {
            Box(
                modifier = Modifier
                    .size(4.dp, 16.dp)
                    .background(badgeColor, RoundedCornerShape(2.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = NewsPalette.textPrimary,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
            )
        }
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = NewsPalette.textSecondary,
            modifier = Modifier.padding(start = 12.dp, top = 2.dp)
        )
    }
}

@Composable
private fun EmptyHint(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = NewsPalette.textSecondary,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

 */

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.finance.lumora.domain.model.NewsArticle
import com.finance.lumora.domain.model.NewsScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// 2026 Minimalist Cream & Off-White Editorial Palette
private object NewsPalette {
    val background = Color(0xFFF7F5F0)       // Warm Off-White / Cream Base
    val surface = Color(0xFFFFFFFF)          // Pure White for Cards/Layers
    val topBar = Color(0xFFF8F7F7)           // Warm Cream Tinted Header
    val bulletinBg = Color(0xFF1E1E20)       // Matte Dark Charcoal Ticker
    val bulletinLiveDot = Color(0xFFE56B55)   // Warm Coral Live Dot Accent
    val textPrimary = Color(0xFF18181A)      // Deep Off-Black Charcoal
    val textSecondary = Color(0xFF6B6A65)    // Muted Warm Grey
    val accent = Color(0xFF8C6D46)           // Muted Warm Bronze / Gold Accent
    val subtleBorder = Color(0xFFE8E5DD)     // Ultra Soft Border
    val localBadge = Color(0xFF8C6D46)       // Bronze Accent for Local
    val globalBadge = Color(0xFF5A7B6C)      // Muted Sage Accent for Global
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    onBackClick: () -> Unit = {},
    viewModel: NewsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    // Loading is started by the ViewModel, so this only listens for one-off effects.
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is NewsEffect.OpenBrowser -> {
                    if (!openLink(context, effect.url)) {
                        launch { snackbarHostState.showSnackbar("Can't open this link.") }
                    }
                }
                is NewsEffect.ShowError -> {
                    launch { snackbarHostState.showSnackbar(effect.message) }
                }
            }
        }
    }

    val onOpen: (NewsArticle) -> Unit = { article ->
        viewModel.onIntent(NewsIntent.OpenArticle(article.url))
    }

    // Headlines that rotate in the red bulletin bar (local first, then global).
    val tickerItems = remember(state.localNews, state.globalNews) {
        state.localNews.take(3) + state.globalNews.take(3)
    }
    var tickerIndex by remember { mutableStateOf(0) }
    LaunchedEffect(tickerItems.size) {
        tickerIndex = 0
        if (tickerItems.size > 1) {
            while (true) {
                delay(5_000)
                tickerIndex = (tickerIndex + 1) % tickerItems.size
            }
        }
    }
    val currentBulletin = tickerItems.getOrNull(tickerIndex)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Surface(
                            color = NewsPalette.accent.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(100.dp),
                            modifier = Modifier.padding(bottom = 2.dp)
                        ) {
                            Text(
                                text = "LUMORA FINANCIAL NETWORK",
                                style = MaterialTheme.typography.labelSmall,
                                color = NewsPalette.accent,
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.sp,
                                letterSpacing = 1.6.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                        Text(
                            text = "Expense & Wire Desk",
                            style = MaterialTheme.typography.titleMedium,
                            color = NewsPalette.textPrimary,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 17.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .clip(CircleShape)
                            .background(NewsPalette.surface)
                            .border(1.dp, NewsPalette.subtleBorder, CircleShape)
                            .size(38.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = NewsPalette.textPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                },
                actions = {
                    IconButton(
                        enabled = !state.isLoading,
                        onClick = {
                            viewModel.onIntent(NewsIntent.Refresh(NewsScope.GLOBAL))
                            viewModel.onIntent(NewsIntent.Refresh(NewsScope.LOCAL))
                        },
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .clip(CircleShape)
                            .background(NewsPalette.surface)
                            .border(1.dp, NewsPalette.subtleBorder, CircleShape)
                            .size(38.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh news",
                            tint = NewsPalette.textPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = NewsPalette.topBar
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = NewsPalette.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            BulletinBar(
                text = currentBulletin?.let { "BULLETIN: ${it.title}" }
                    ?: "Tracking global markets and your local economy...",
                onClick = currentBulletin?.let { article -> { onOpen(article) } }
            )

            // A thin bar instead of a full-screen dimmer: saved stories stay usable while refreshing.
            if (state.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = NewsPalette.accent,
                    trackColor = Color.Transparent
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                // --- LOCAL ---
                item(key = "local_header") {
                    BroadcastSectionHeader(
                        title = "LOCAL FINANCE DISPATCH",
                        subtitle = "Indian markets and companies in the news",
                        badgeColor = NewsPalette.localBadge
                    )
                }
                item(key = "local_content") {
                    if (state.localNews.isEmpty()) {
                        EmptyHint(
                            if (state.isLocalLoading) "Loading local stories..."
                            else "No local stories yet. Check your connection and tap refresh."
                        )
                    } else {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            items(state.localNews, key = { it.id }) { article ->
                                NewsCard(
                                    article = article,
                                    modifier = Modifier.width(285.dp),
                                    onClick = { onOpen(article) }
                                )
                            }
                        }
                    }
                }

                // --- GLOBAL ---
                item(key = "global_header") {
                    Spacer(modifier = Modifier.height(20.dp))
                    BroadcastSectionHeader(
                        title = "GLOBAL MARKET WIRE",
                        subtitle = "Worldwide market news",
                        badgeColor = NewsPalette.globalBadge
                    )
                }
                if (state.globalNews.isEmpty()) {
                    item(key = "global_empty") {
                        EmptyHint(
                            if (state.isGlobalLoading) "Loading global stories..."
                            else "No global stories yet. Check your connection and tap refresh."
                        )
                    }
                } else {
                    items(state.globalNews, key = { it.id }) { article ->
                        NewsCard(
                            article = article,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 6.dp),
                            onClick = { onOpen(article) }
                        )
                    }
                }
            }
        }
    }
}

/** Opens http(s) links only, and never crashes if no browser is installed. */
private fun openLink(context: Context, url: String): Boolean {
    val uri = runCatching { url.toUri() }.getOrNull() ?: return false
    if (uri.scheme != "https" && uri.scheme != "http") return false
    return try {
        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
        true
    } catch (_: ActivityNotFoundException) {
        false
    }
}

// --- HELPER COMPONENTS ---

@Composable
private fun BulletinBar(text: String, onClick: (() -> Unit)?) {
    Surface(
        color = NewsPalette.bulletinBg,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            PulsingLiveBadge(badgeColor = NewsPalette.bulletinLiveDot)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFF0EFEA),
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

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
            .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(100.dp))
            .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(100.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(badgeColor.copy(alpha = alpha))
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "LIVE",
            color = Color.White,
            fontWeight = FontWeight.Black,
            fontSize = 9.sp,
            letterSpacing = 1.sp
        )
    }
}

@Composable
private fun BroadcastSectionHeader(
    title: String,
    subtitle: String,
    badgeColor: Color
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.semantics { heading() }
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp, 16.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(badgeColor)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = NewsPalette.textPrimary,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
            )
        }
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = NewsPalette.textSecondary,
            modifier = Modifier.padding(start = 16.dp, top = 2.dp)
        )
    }
}

@Composable
private fun EmptyHint(text: String) {
    Surface(
        color = NewsPalette.surface,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, NewsPalette.subtleBorder),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = NewsPalette.textSecondary,
            modifier = Modifier.padding(16.dp)
        )
    }
}