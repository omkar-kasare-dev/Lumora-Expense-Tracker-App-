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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.finance.lumora.navigation.BottomNavigationBar
import com.finance.lumora.navigation.Screen
import com.finance.lumora.presentation.dashboard.components.DashboardError
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
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 4.dp, vertical = 6.dp)
            ) {
                DashboardTopBar(
                    userName = state.userName.ifBlank { "User" },
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
            // Enhanced Loading State
            //------------------------------------------
            state.isLoading -> {
                EnhancedDashboardLoading(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
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
            // Dashboard Content
            //------------------------------------------
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Spacer(modifier = Modifier.size(2.dp))

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

//----------------------------------------------------
// Enhanced Professional Loading Indicator
//----------------------------------------------------

@Composable
private fun EnhancedDashboardLoading(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "dashboard_loading_anim")

    val outerPulseScale by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "outerPulseScale"
    )

    val outerPulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "outerPulseAlpha"
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(72.dp)
            ) {
                // Outer Ambient Ring Pulse
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .scale(outerPulseScale)
                        .clip(CircleShape)
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = outerPulseAlpha)
                        )
                )

                // Secondary Accent Halo
                CircularProgressIndicator(
                    modifier = Modifier.size(52.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                    strokeWidth = 3.dp,
                    trackColor = Color.Transparent
                )

                // Active Progress Ring
                CircularProgressIndicator(
                    modifier = Modifier.size(52.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 3.dp,
                    strokeCap = StrokeCap.Round
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Updating overview...",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 12.sp,
                    letterSpacing = 0.4.sp
                ),
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
    }
}

//----------------------------------------------------
// Aurix FAB (Minimal & Refined)
//----------------------------------------------------

@Composable
fun AurixFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "aurix_fab_pulse")

    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.18f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    val iconRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "iconRotation"
    )

    Box(
        modifier = modifier.padding(bottom = 6.dp, end = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        // Outer Glowing Halo
        Box(
            modifier = Modifier
                .size(58.dp)
                .scale(pulseScale)
                .clip(CircleShape)
                .background(
                    MaterialTheme.colorScheme.primary.copy(alpha = pulseAlpha)
                )
        )

        // Main Floating Pill
        Surface(
            onClick = onClick,
            shape = RoundedCornerShape(20.dp),
            color = Color.Transparent,
            shadowElevation = 6.dp,
            tonalElevation = 2.dp
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
                        width = 0.75.dp,
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.35f),
                                Color.White.copy(alpha = 0.08f)
                            )
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 14.dp, vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .rotate(iconRotation),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Insights,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Text(
                    text = "Ask AURIX",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 12.sp,
                        letterSpacing = 0.2.sp
                    ),
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                // Active Online Dot Indicator
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF10B981))
                )
            }
        }
    }
}