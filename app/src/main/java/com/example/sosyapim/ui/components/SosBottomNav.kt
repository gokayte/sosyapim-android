package com.example.sosyapim.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sosyapim.data.AppScreen
import com.example.sosyapim.ui.theme.DarkSurface
import com.example.sosyapim.ui.theme.SosRed

@Composable
fun SosBottomNav(
    currentScreen: AppScreen,
    onScreenSelected: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier.testTag("sos_bottom_nav"),
        containerColor = DarkSurface,
        tonalElevation = 8.dp
    ) {
        NavItem(
            screen = AppScreen.CLIPS,
            label = "Klipler",
            icon = Icons.Default.PlayCircle,
            isSelected = currentScreen == AppScreen.CLIPS,
            onClick = { onScreenSelected(AppScreen.CLIPS) }
        )
        NavItem(
            screen = AppScreen.ARTISTS,
            label = "Sanatçılar",
            icon = Icons.Default.Group,
            isSelected = currentScreen == AppScreen.ARTISTS,
            onClick = { onScreenSelected(AppScreen.ARTISTS) }
        )
        NavItem(
            screen = AppScreen.YERALTI,
            label = "YERALTI",
            icon = Icons.Default.Album,
            isSelected = currentScreen == AppScreen.YERALTI,
            onClick = { onScreenSelected(AppScreen.YERALTI) }
        )
        NavItem(
            screen = AppScreen.SERVICES,
            label = "Hizmet & Demo",
            icon = Icons.Default.Email,
            isSelected = currentScreen == AppScreen.SERVICES,
            onClick = { onScreenSelected(AppScreen.SERVICES) }
        )
    }
}

@Composable
private fun RowScope.NavItem(
    screen: AppScreen,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = isSelected,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(22.dp)
            )
        },
        label = {
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.White,
            selectedTextColor = Color.White,
            indicatorColor = SosRed,
            unselectedIconColor = Color(0xFF8E929B),
            unselectedTextColor = Color(0xFF8E929B)
        ),
        modifier = Modifier.testTag("nav_item_${screen.name.lowercase()}")
    )
}
