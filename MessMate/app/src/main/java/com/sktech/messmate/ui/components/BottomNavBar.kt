package com.sktech.messmate.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sktech.messmate.data.model.UserRole
import com.sktech.messmate.ui.theme.*

data class BottomNavItem(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun MessMateBottomBar(
    role: UserRole,
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = when (role) {
        UserRole.CUSTOMER -> listOf(
            BottomNavItem("customer/dashboard", "Home", Icons.Filled.Home, Icons.Outlined.Home),
            BottomNavItem("customer/discovery", "Discovery", Icons.Filled.Explore, Icons.Outlined.Explore),
            BottomNavItem("customer/history", "History", Icons.Filled.History, Icons.Outlined.History),
            BottomNavItem("settings", "Profile", Icons.Filled.Person, Icons.Outlined.Person)
        )
        UserRole.OWNER -> listOf(
            BottomNavItem("owner/dashboard", "Dashboard", Icons.Filled.Dashboard, Icons.Outlined.Dashboard),
            BottomNavItem("owner/members", "Members", Icons.Filled.People, Icons.Outlined.People),
            BottomNavItem("owner/menu", "Menu", Icons.Filled.RestaurantMenu, Icons.Outlined.RestaurantMenu),
            BottomNavItem("settings", "Profile", Icons.Filled.Person, Icons.Outlined.Person)
        )
        UserRole.PERSONAL -> listOf(
            BottomNavItem("personal/dashboard", "Home", Icons.Filled.Home, Icons.Outlined.Home),
            BottomNavItem("personal/stats", "Stats", Icons.Filled.BarChart, Icons.Outlined.BarChart),
            BottomNavItem("settings", "Profile", Icons.Filled.Person, Icons.Outlined.Person)
        )
        else -> emptyList()
    }

    if (items.isEmpty()) return

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                clip = false
            ),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        color = MessMateThemeExt.colors.surfaceContainer,
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route

                BottomNavItemView(
                    item = item,
                    isSelected = isSelected,
                    onClick = { onNavigate(item.route) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun BottomNavItemView(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) OnSecondaryContainer
        else OnSurfaceVariant,
        label = "nav_icon"
    )

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        // Active indicator pill
        Box(
            modifier = Modifier
                .height(32.dp)
                .widthIn(min = if (isSelected) 64.dp else 40.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (isSelected) SecondaryContainer
                    else Color.Transparent
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                contentDescription = item.label,
                tint = iconColor,
                modifier = Modifier.size(22.dp)
            )
        }
        Text(
            text = item.label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = iconColor,
            maxLines = 1
        )
    }
}
