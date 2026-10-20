package com.junkfood.seal.ui.page.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.junkfood.seal.ui.common.Route

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

/** Floating pill-shaped bottom navigation bar with 4 top-level destinations. */
@Composable
fun BottomNavBar(currentRoute: String?, onNavigate: (String) -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp)) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            color = MaterialTheme.colorScheme.surfaceContainer,
            tonalElevation = 3.dp,
            shadowElevation = 6.dp,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 10.dp),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly,
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
            Modifier.clip(RoundedCornerShape(20.dp))
                .background(
                    if (selected) MaterialTheme.colorScheme.surfaceContainerHigh
                    else androidx.compose.ui.graphics.Color.Transparent
                )
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 8.dp),
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
