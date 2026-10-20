package com.junkfood.seal.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val SettingsCardShape = RoundedCornerShape(16.dp)

val LocalInSettingsGroup = compositionLocalOf { false }

/**
 * Wraps a group of preference rows (PreferenceItem, PreferenceSwitch, etc.) in a single
 * rounded card instead of each row floating as its own separate surface. Insert
 * [SettingsGroupDivider] between rows for the hairline divider.
 */
@Composable
fun SettingsGroupCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
        shape = SettingsCardShape,
        color = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        CompositionLocalProvider(LocalInSettingsGroup provides true) { Column(content = content) }
    }
}

/** Insert between preference rows inside a [SettingsGroupCard] to render a hairline divider. */
@Composable
fun SettingsGroupDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
    )
}
