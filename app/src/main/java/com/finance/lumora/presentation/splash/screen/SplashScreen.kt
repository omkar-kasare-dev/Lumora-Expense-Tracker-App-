package com.finance.lumora.presentation.splash.screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.finance.lumora.R
import com.finance.lumora.navigation.Screen
import com.finance.lumora.presentation.auth.session.SessionState
import com.finance.lumora.presentation.auth.session.SessionViewModel
import com.finance.lumora.presentation.splash.components.SplashBackground
import com.finance.lumora.presentation.splash.components.WaveBackground
import com.finance.lumora.presentation.splash.viewmodel.SplashViewModel
import kotlin.math.roundToInt

@Composable
fun SplashScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    sessionViewModel: SessionViewModel = hiltViewModel(),
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    val sessionState by sessionViewModel
        .sessionState
        .collectAsState()

    val splashState by splashViewModel
        .uiState
        .collectAsState()

    var startAnimation by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    val logoScale by animateFloatAsState(
        targetValue =
            if (startAnimation) 1f
            else 0.6f,
        animationSpec = tween(
            durationMillis = 900,
            easing = FastOutSlowInEasing
        ),
        label = "LogoScale"
    )

    val logoAlpha by animateFloatAsState(
        targetValue =
            if (startAnimation) 1f
            else 0f,
        animationSpec = tween(
            durationMillis = 900,
            easing = FastOutSlowInEasing
        ),
        label = "LogoAlpha"
    )

    // SubTitle Animation:
    val titleAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 700,
            delayMillis = 250,
            easing = LinearOutSlowInEasing
        ),
        label = "TitleAlpha"
    )

    val titleOffsetY by animateFloatAsState(
        targetValue = if (startAnimation) 0f else 40f,
        animationSpec = tween(
            durationMillis = 700,
            delayMillis = 250,
            easing = LinearOutSlowInEasing
        ),
        label = "TitleOffset"
    )

    val subtitleAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 700,
            delayMillis = 500,
            easing = LinearOutSlowInEasing
        ),
        label = "SubtitleAlpha"
    )

    val subtitleOffsetY by animateFloatAsState(
        targetValue = if (startAnimation) 0f else 30f,
        animationSpec = tween(
            durationMillis = 700,
            delayMillis = 500,
            easing = LinearOutSlowInEasing
        ),
        label = "SubtitleOffset"
    )

    // Sub Title Animation END:

    // Infinite Transition Animation Section:

    val infiniteTransition = rememberInfiniteTransition(
        label = "FloatingAnimation"
    )

    val floatingOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -8f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2200,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "FloatingOffset"
    )

    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2200,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "PulseScale"
    )
    // Infinite Transition Animation Section: END

    LaunchedEffect(sessionState, splashState.isLoading) {
        android.util.Log.d(
            "SPLASH",
            "session=$sessionState, splashLoading=${splashState.isLoading}"
        )

        // Hold on the splash until its minimum display time is over,
        // even if the session resolved earlier.
        if (splashState.isLoading) return@LaunchedEffect

        when (sessionState) {
            SessionState.Loading -> {
                // Keep showing Splash
            }

            SessionState.Authenticated -> {
                navController.navigate(
                    Screen.Dashboard.route
                ) {
                    popUpTo(Screen.Splash.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }

            SessionState.Unauthenticated -> {
                navController.navigate(
                    Screen.Login.route
                ) {
                    popUpTo(Screen.Splash.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFFFFF),
                        Color(0xFFF8FAFC),
                        Color(0xFFF1F5F9)
                    )
                )
            )
    ) {
        /*
         * Decorative Background
         */
        SplashBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(
                modifier = Modifier.height(110.dp)
            )

            /*
             * Logo
             */
            Image(
                painter = painterResource(
                    id = R.drawable.lumora_logo
                ),
                contentDescription = "Lumora Logo",
                modifier = Modifier
                    .size(210.dp)
                    .graphicsLayer {
                        scaleX = logoScale * pulseScale
                        scaleY = logoScale * pulseScale
                        translationY = floatingOffset
                        alpha = logoAlpha
                    }
            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            /*
             * App Name
             */
            Text(
                text = "Lumora",
                modifier = Modifier
                    .alpha(titleAlpha)
                    .offset {
                        IntOffset(
                            0,
                            titleOffsetY.roundToInt()
                        )
                    },
                fontSize = 56.sp,
                fontWeight = FontWeight.ExtraBold,
                style = TextStyle(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color(0xFF0F172A),
                            Color(0xFF2563EB),
                            Color(0xFF3B82F6)
                        )
                    )
                )
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            /*
             * Subtitle
             */
            Row(
                modifier = Modifier
                    .alpha(subtitleAlpha)
                    .offset {
                        IntOffset(
                            0,
                            subtitleOffsetY.roundToInt()
                        )
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.width(60.dp),
                    color = Color(0xFFCBD5E1)
                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Text(
                    text = "Daily Expense Tracker",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = Color(0xFF475569)
                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                HorizontalDivider(
                    modifier = Modifier.width(60.dp),
                    color = Color(0xFFCBD5E1)
                )
            }

            Spacer(
                modifier = Modifier.height(70.dp)
            )

            /*
             * Caption
             */
            Text(
                text = "Track Today.",
                modifier = Modifier.alpha(subtitleAlpha),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "Plan Tomorrow. Achieve More.",
                modifier = Modifier.alpha(subtitleAlpha),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF64748B)
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            /*
             * Bottom Tagline
             */
            Text(
                text = "Your Journey to Financial Clarity",
                modifier = Modifier.alpha(subtitleAlpha),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF94A3B8)
            )

            Spacer(
                modifier = Modifier.height(40.dp)
            )
        }

        /*
         * Bottom Waves
         */
        WaveBackground(
            modifier = Modifier.align(
                Alignment.BottomCenter
            )
        )
    }
}