package org.akai.sciclubhub.ui.components.navigationbottombar

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomBarIcon(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val destination: String,
)
