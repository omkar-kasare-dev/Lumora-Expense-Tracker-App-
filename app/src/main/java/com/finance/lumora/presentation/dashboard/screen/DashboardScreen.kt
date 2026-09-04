package com.finance.lumora.presentation.dashboard.screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.finance.lumora.navigation.BottomNavigationBar
import com.finance.lumora.navigation.Screen
import com.finance.lumora.presentation.dashboard.components.DashboardError
import com.finance.lumora.presentation.dashboard.components.DashboardLoading
import com.finance.lumora.presentation.dashboard.components.DashboardTopBar
import com.finance.lumora.presentation.dashboard.components.EmptyDashboard
import com.finance.lumora.presentation.dashboard.effect.DashboardUiEffect
import com.finance.lumora.presentation.dashboard.event.DashboardEvent
import com.finance.lumora.presentation.dashboard.viewmodel.DashboardViewModel
import com.finance.lumora.presentation.transaction.viewmodel.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavHostController,
    viewModel: DashboardViewModel = hiltViewModel(),
    transactionViewModel: TransactionViewModel = hiltViewModel()
) {

    //----------------------------------------------------
    // State
    //----------------------------------------------------

    val state by viewModel.state.collectAsState()
    var isAddTransactionDialogOpen by remember { mutableStateOf(false) }
    val transactionState by transactionViewModel.state.collectAsStateWithLifecycle()

    //----------------------------------------------------
    // Snackbar
    //----------------------------------------------------

    val snackbarHostState = remember { SnackbarHostState() }

    //----------------------------------------------------
    // UI Effects
    //----------------------------------------------------

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is DashboardUiEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                DashboardUiEffect.NavigateToAddTransaction -> {
                    // TODO Navigation
                }
                DashboardUiEffect.NavigateToTransactions -> {
                    // TODO Navigation
                }
                is DashboardUiEffect.NavigateToTransactionDetails -> {
                    // TODO Navigation
                }
            }
        }
    }

    //----------------------------------------------------
    // Screen
    //----------------------------------------------------

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(top = 10.dp)
            ) {
                DashboardTopBar(
                    userName = "Omkar",
                    onSearchClick = {
                        if (navController.currentDestination?.route != Screen.Search.route) {
                            navController.navigate(Screen.Search.route)
                        }
                    },
                    onNotificationClick = {
                        navController.navigate("Notifications")
                    },
                    onProfileClick = {
                        navController.navigate("Profile")
                    }
                )
            }
        },
        floatingActionButton = {
            AurixFloatingActionButton(
                onClick = {
                    if (navController.currentDestination?.route != Screen.Aurix.route) {
                        navController.navigate(Screen.Aurix.route)
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->

        when {
            //------------------------------------------
            // Loading
            //------------------------------------------
            state.isLoading -> {
                DashboardLoading()
            }

            //------------------------------------------
            // Error
            //------------------------------------------
            state.error != null -> {
                DashboardError(
                    message = state.error!!,
                    onRetry = {
                        viewModel.onEvent(DashboardEvent.Retry)
                    }
                )
            }

            //------------------------------------------
            // Empty Dashboard
            //------------------------------------------
            state.statistics.transactionCount == 0 -> {
                EmptyDashboard(
                    onAddTransaction = {
                        viewModel.onEvent(DashboardEvent.AddTransaction)
                        isAddTransactionDialogOpen = true
                    }
                )
            }

            //------------------------------------------
            // Dashboard
            //------------------------------------------
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Spacer(modifier = Modifier.size(4.dp))

                    DashboardContent(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        state = state,
                        onEvent = viewModel::onEvent
                    )
                }
            }
        }
    }
}

@Composable
fun AurixFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "aurix_fab_pulse")

    // Pulse Halo Animation
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.28f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    // Icon Rotation Glow
    val iconRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "iconRotation"
    )

    Box(
        modifier = modifier.padding(bottom = 8.dp, end = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        // Outer Glowing Pulse Halo
        Box(
            modifier = Modifier
                .size(68.dp)
                .scale(pulseScale)
                .clip(CircleShape)
                .background(
                    MaterialTheme.colorScheme.primary.copy(alpha = pulseAlpha)
                )
        )

        // Main Animated Floating Pill Button
        Surface(
            onClick = onClick,
            shape = RoundedCornerShape(28.dp),
            color = Color.Transparent,
            shadowElevation = 8.dp,
            tonalElevation = 6.dp
        ) {
            Row(
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.tertiary
                            )
                        )
                    )
                    .border(
                        width = 1.dp,
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.4f),
                                Color.White.copy(alpha = 0.1f)
                            )
                        ),
                        shape = RoundedCornerShape(28.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Sparkle Icon with animated gradient frame
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .rotate(iconRotation),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Insights,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Text(
                    text = "Ask AURIX",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                // Status Dot indicator
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(Color.Green)
                )
            }
        }
    }
}