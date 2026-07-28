package com.finance.lumora.presentation.splash.screen



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.lumora.R
import com.finance.lumora.presentation.splash.components.SplashBackground
import com.finance.lumora.presentation.splash.components.WaveBackground
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.finance.lumora.navigation.Screen
import com.finance.lumora.presentation.splash.viewmodel.SplashViewModel

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.LinearEasing
import androidx.compose.ui.graphics.graphicsLayer



@Composable
fun SplashScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel()
){
    val uiState by viewModel
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


    LaunchedEffect(uiState.isLoading) {

        if (!uiState.isLoading) {

            navController.navigate(
                Screen.Dashboard.route
            ) {

                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }

                launchSingleTop = true

            }

        }

    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF040816),
                        Color(0xFF081223),
                        Color(0xFF03060E)
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
                    id = R.drawable.lumora1_logo
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

                fontWeight = FontWeight.Bold,

                style = TextStyle(

                    brush = Brush.horizontalGradient(

                        listOf(

                            Color(0xFFFFC857),

                            Color(0xFFFF78C8),

                            Color(0xFF5E82FF)

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
                    color = Color(0xFFFF69D5)
                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Text(

                    text = "Daily Expense Tracker",

                    style = MaterialTheme.typography.titleMedium,

                    color = Color(0xFFAAB4D6)

                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                HorizontalDivider(
                    modifier = Modifier.width(60.dp),
                    color = Color(0xFF3DBBFF)
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

                fontWeight = FontWeight.SemiBold,

                color = Color.White

            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(

                text = "Plan Tomorrow. Achieve More.",
                modifier = Modifier.alpha(subtitleAlpha),

                fontSize = 20.sp,

                color = Color(0xFF96A4C9)

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

                color = Color(0xFF7F89A8)

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