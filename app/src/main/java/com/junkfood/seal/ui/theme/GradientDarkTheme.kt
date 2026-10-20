package com.junkfood.seal.ui.theme

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Gradient Dark Theme Color Palette
 * Deep charcoal and obsidian backgrounds with vibrant linear gradients
 */
object GradientDarkColors {
    // Near-black backgrounds
    val Background = Color(0xFF0A0A0A)
    val Surface = Color(0xFF141414)
    val SurfaceVariant = Color(0xFF1E1E1E)
    val SurfaceContainer = Color(0xFF1C1C1C)
    val SurfaceContainerLow = Color(0xFF161616)
    val SurfaceContainerHigh = Color(0xFF242424)

    // Flat Two-Tone - dark start, light end
    val GradientPrimaryStart = Color(0xFF2A2A2A)
    val GradientPrimaryEnd = Color(0xFFE0E0E0)
    val GradientSecondaryStart = Color(0xFF242424)
    val GradientSecondaryEnd = Color(0xFFD6D6D6)
    val GradientAccentStart = Color(0xFF333333)
    val GradientAccentEnd = Color(0xFFFFFFFF)

    // Glassmorphism Colors
    val GlassWhiteBorder = Color(0x1AFFFFFF)
    val GlassSurface = Color(0x0DFFFFFF)
    val GlassSurfaceVariant = Color(0x1AFFFFFF)

    // Text Colors
    val OnBackground = Color(0xFFFAFAFA)
    val OnSurface = Color(0xFFF5F5F5)
    val OnPrimary = Color(0xFFFFFFFF)
    // Dark text for use on light (End-tier) backgrounds
    val OnAccentDark = Color(0xFF121212)

    // Additional Accent Colors
    val GradientCyan = Color(0xFFBFBFBF)
    val GradientPurpleBright = Color(0xFFE8E8E8)
    val GradientBlueBright = Color(0xFFD0D0D0)
}

/**
 * Gradient Brushes for primary, secondary, and accent colors
 */
object GradientBrushes {
    val Primary = Brush.linearGradient(
        colors = listOf(
            GradientDarkColors.GradientPrimaryStart,
            GradientDarkColors.GradientPrimaryEnd
        ),
        start = Offset.Zero,
        end = Offset.Infinite
    )
    
    val Secondary = Brush.linearGradient(
        colors = listOf(
            GradientDarkColors.GradientSecondaryStart,
            GradientDarkColors.GradientSecondaryEnd,
            GradientDarkColors.GradientAccentStart
        ),
        start = Offset.Zero,
        end = Offset.Infinite
    )
    
    val Accent = Brush.linearGradient(
        colors = listOf(
            GradientDarkColors.GradientAccentStart,
            GradientDarkColors.GradientAccentEnd
        ),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, 0f)
    )
    
    val Vibrant = Brush.linearGradient(
        colors = listOf(
            GradientDarkColors.GradientBlueBright,
            GradientDarkColors.GradientPurpleBright,
            GradientDarkColors.GradientAccentEnd
        )
    )
}

/**
 * Glassmorphism Card with backdrop blur, subtle borders, and shadows
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    elevation: Dp = 4.dp,
    borderWidth: Dp = 1.dp,
    cornerRadius: Dp = 20.dp,
    blurRadius: Dp = 16.dp,
    alpha: Float = 0.05f,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .border(
                width = borderWidth,
                color = GradientDarkColors.GlassWhiteBorder,
                shape = RoundedCornerShape(cornerRadius)
            ),
        shape = RoundedCornerShape(cornerRadius),
        colors = CardDefaults.cardColors(
            containerColor = GradientDarkColors.GlassSurface.copy(alpha = alpha)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            content()
        }
    }
}

/**
 * Premium Glass Card with elevated appearance
 */
@Composable
fun GlassCardElevated(
    modifier: Modifier = Modifier,
    elevation: Dp = 8.dp,
    borderWidth: Dp = 1.5.dp,
    cornerRadius: Dp = 24.dp,
    alpha: Float = 0.1f,
    content: @Composable ColumnScope.() -> Unit
) {
    GlassCard(
        modifier = modifier,
        elevation = elevation,
        borderWidth = borderWidth,
        cornerRadius = cornerRadius,
        alpha = alpha,
        content = content
    )
}

/**
 * Gradient Surface with animated gradient background
 */
@Composable
fun GradientSurface(
    modifier: Modifier = Modifier,
    brush: Brush = GradientBrushes.Primary,
    cornerRadius: Dp = 16.dp,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(brush),
        color = Color.Transparent,
        content = content
    )
}

/**
 * Animated fade-in scale effect for cards and elements
 */
@Composable
fun animatedCardAppearance(): State<Float> {
    val scale = remember { Animatable(0.95f) }
    val alpha = remember { Animatable(0f) }
    
    LaunchedEffect(Unit) {
        // Run animations in parallel using coroutines
        coroutineScope {
            launch {
                scale.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                )
            }
            launch {
                alpha.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            }
        }
    }
    
    return derivedStateOf { scale.value * alpha.value }
}

/**
 * Modifier extension for glassmorphism effect
 */
fun Modifier.glassmorphism(
    cornerRadius: Dp = 20.dp,
    borderWidth: Dp = 1.dp,
    alpha: Float = 0.05f,
    blurRadius: Dp = 16.dp
): Modifier = this
    .clip(RoundedCornerShape(cornerRadius))
    .background(GradientDarkColors.GlassSurface.copy(alpha = alpha))
    .border(
        width = borderWidth,
        color = GradientDarkColors.GlassWhiteBorder,
        shape = RoundedCornerShape(cornerRadius)
    )

/**
 * Modifier extension for gradient background
 */
fun Modifier.gradientBackground(
    brush: Brush = GradientBrushes.Primary,
    cornerRadius: Dp = 16.dp
): Modifier = this
    .clip(RoundedCornerShape(cornerRadius))
    .background(brush)

/**
 * Animated button press effect
 */
@Composable
fun animatedButtonPress(pressed: Boolean): State<Float> {
    val scale = animateFloatAsState(
        targetValue = if (pressed) 0.96f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "button_press"
    )
    return scale
}

/**
 * Smooth page transition animation
 */
@Composable
fun pageTransitionAnimation(
    visible: Boolean
): State<Float> {
    val animatedProgress = animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing
        ),
        label = "page_transition"
    )
    return animatedProgress
}
