package com.junkfood.seal.ui.page.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.junkfood.seal.ui.common.Route
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

/**
 * Total space the floating [BottomNavBar] occupies at the bottom of the screen (its own height
 * plus the margin around it), measured live in [AppEntry]. Any screen content or dialog/sheet
 * actions that sit near the bottom of the screen should add this as extra bottom padding so they
 * don't render underneath the bar. Defaults to 0.dp before the first measurement.
 */
val LocalBottomNavBarInset = staticCompositionLocalOf { 0.dp }

/**
 * Signals that the format selection page is active. When true, the nav bar is hidden
 * and replaced with a floating download button at the bottom.
 */
val LocalIsFormatSelectionActive = staticCompositionLocalOf { false }

private data class NavDestination(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

private val destinations =
    listOf(
        NavDestination(Route.HOME, "Home", Icons.Filled.Home, Icons.Outlined.Home),
        NavDestination(
            Route.DOWNLOADS,
            "Downloads",
            Icons.Filled.FileDownload,
            Icons.Outlined.FileDownload,
        ),
        NavDestination(
            Route.MORE_TOOLS,
            "Tools",
            Icons.Filled.GridView,
            Icons.Outlined.GridView,
        ),
        NavDestination(
            Route.SETTINGS_PAGE,
            "Settings",
            Icons.Filled.Settings,
            Icons.Outlined.Settings,
        ),
    )

/**
 * Floating pill-shaped bottom navigation bar with 4 top-level destinations. The bar itself is
 * a frosted-glass blur of whatever scrolls behind it (via [hazeState], shared with the screen
 * content's [dev.chrisbanes.haze.hazeSource]) — the area around it stays fully transparent,
 * matching Nuvio's floating nav bar technique.
 */
@Composable
fun BottomNavBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    hazeState: HazeState? = null,
    modifier: Modifier = Modifier,
) {
    val tintColor = MaterialTheme.colorScheme.surfaceContainer
    val borderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)

    Box(modifier = modifier.fillMaxWidth(0.9f).padding(16.dp)) {
        Box(
            modifier =
                Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(32.dp))
                    .then(
                        if (hazeState != null) {
                            Modifier.hazeEffect(state = hazeState) {
                                blurRadius = 24.dp
                                backgroundColor = tintColor
                                tints = listOf(HazeTint(tintColor.copy(alpha = 0.55f)))
                                noiseFactor = 0f
                            }
                        } else {
                            Modifier.background(tintColor)
                        }
                    )
                    .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(32.dp))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                destinations.forEach { destination ->
                    val selected = destination.route == currentRoute
                    NavItem(
                        label = destination.label,
                        icon = if (selected) destination.selectedIcon else destination.unselectedIcon,
                        selected = selected,
                        onClick = { onNavigate(destination.route) },
                    )
                }
            }
        }
    }
}

@Composable
private fun NavItem(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier.clip(RoundedCornerShape(24.dp))
                .background(
                    if (selected) MaterialTheme.colorScheme.surfaceContainerHigh
                    else Color.Transparent
                )
                .clickable(onClick = onClick)
                .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(22.dp),
            tint =
                if (selected) MaterialTheme.colorScheme.onSurface
                else MaterialTheme.colorScheme.onSurfaceVariant,
        )
        if (selected) {
            Text(
                text = label,
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}
