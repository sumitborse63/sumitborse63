package com.sktech.messmate.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sktech.messmate.ui.components.MessMateTopBar
import com.sktech.messmate.ui.components.StatusBadge
import com.sktech.messmate.ui.theme.*

data class MenuItem(
    val name: String,
    val description: String,
    val type: String // "Regular" or "Special"
)

data class DaySelector(
    val dayName: String,
    val dayNumber: Int,
    val isSelected: Boolean = false,
    val isWeekend: Boolean = false
)

@Composable
fun MenuManagementScreen(
    modifier: Modifier = Modifier
) {
    var selectedDay by remember { mutableIntStateOf(1) }

    val weekDays = remember {
        listOf(
            DaySelector("Mon", 12),
            DaySelector("Tue", 13, isSelected = true),
            DaySelector("Wed", 14),
            DaySelector("Thu", 15),
            DaySelector("Fri", 16),
            DaySelector("Sat", 17, isWeekend = true),
            DaySelector("Sun", 18, isWeekend = true)
        )
    }

    val breakfastItems = remember {
        listOf(
            MenuItem("Masala Poha", "Flattened rice, peanuts, mild spices.", "Regular"),
            MenuItem("Aloo Paratha", "Stuffed flatbread with curd & pickle.", "Special")
        )
    }
    val lunchItems = remember {
        listOf(
            MenuItem("Rajma Chawal", "Kidney bean curry, basmati rice, salad.", "Regular"),
            MenuItem("Paneer Butter Masala", "Rich paneer curry with 3 rotis.", "Special")
        )
    }
    val dinnerItems = remember {
        listOf(
            MenuItem("Dal Tadka & Rice", "Yellow lentils tempered with ghee.", "Regular")
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        MessMateTopBar()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "Menu Management",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        Text(
                            text = "Plan and edit your weekly offerings.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = OnSurfaceVariant
                        )
                    }
                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Primary, contentColor = OnPrimary
                        ),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Icon(Icons.Outlined.Publish, null, Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Publish", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }

            // Weekly Calendar Selector
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        weekDays.forEachIndexed { index, day ->
                            DaySelectorChip(
                                day = day,
                                isSelected = index == selectedDay,
                                onClick = { selectedDay = index }
                            )
                        }
                    }
                }
            }

            // Meal Sections
            item {
                MealSection(
                    title = "Breakfast",
                    time = "07:00 - 09:30",
                    icon = Icons.Outlined.FreeBreakfast,
                    items = breakfastItems
                )
            }
            item {
                MealSection(
                    title = "Lunch",
                    time = "12:30 - 15:00",
                    icon = Icons.Outlined.LunchDining,
                    items = lunchItems
                )
            }
            item {
                MealSection(
                    title = "Dinner",
                    time = "19:30 - 22:00",
                    icon = Icons.Outlined.DinnerDining,
                    items = dinnerItems
                )
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
private fun DaySelectorChip(day: DaySelector, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) SecondaryContainer else SurfaceContainer
        ),
        border = if (isSelected) {
            androidx.compose.foundation.BorderStroke(1.dp, Secondary)
        } else null
    ) {
        Column(
            modifier = Modifier
                .widthIn(min = 56.dp)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = day.dayName.uppercase(),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (day.isWeekend) Primary
                else if (isSelected) OnSecondaryContainer else OnSurfaceVariant
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "${day.dayNumber}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (day.isWeekend) Primary else OnSurface
            )
            if (isSelected) {
                Spacer(Modifier.height(2.dp))
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(Secondary)
                )
            }
        }
    }
}

@Composable
private fun MealSection(
    title: String,
    time: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    items: List<MenuItem>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            // Section header
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = SurfaceContainerLow
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(icon, null, tint = Primary)
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineMedium,
                            color = OnSurface
                        )
                    }
                    StatusBadge(
                        text = time,
                        containerColor = SecondaryContainer,
                        contentColor = OnSecondaryContainer
                    )
                }
            }

            // Menu items
            Column(modifier = Modifier.padding(16.dp)) {
                items.forEach { item ->
                    MenuItemCard(item)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Add Item button
            HorizontalDivider(color = SurfaceContainerHighest)
            TextButton(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Icon(Icons.Outlined.Add, null, Modifier.size(18.dp), tint = Primary)
                Spacer(Modifier.width(4.dp))
                Text("Add Item", style = MaterialTheme.typography.labelMedium, color = Primary)
            }
        }
    }
}

@Composable
private fun MenuItemCard(item: MenuItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceContainerHighest)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = OnSurface
                )
                IconButton(onClick = { }, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Outlined.Edit, null, Modifier.size(18.dp), tint = OnSurfaceVariant)
                }
            }
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                color = OnSurfaceVariant
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = item.type.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = if (item.type == "Special") Primary else Secondary
            )
        }
    }
}
