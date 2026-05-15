package com.sktech.messmate.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sktech.messmate.data.model.User
import com.sktech.messmate.ui.components.*
import com.sktech.messmate.ui.theme.*

@Composable
fun CustomerDashboard(
    user: User?,
    state: MealTrackingState,
    onMarkMeal: (String, Boolean) -> Unit,
    onNavigateToMeals: () -> Unit,
    onNavigateToMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // ───── Top App Bar ─────
        MessMateTopBar(
            profileInitial = user?.name?.firstOrNull()?.uppercase() ?: "U"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // ───── Section: My Daily Meals ─────
            Text(
                text = "My Daily Meals",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = OnSurface
            )
            Text(
                text = "Track your meals for today",
                style = MaterialTheme.typography.bodyLarge,
                color = OnSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ───── Active Plan Summary Card ─────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SurfaceContainerLowest
                ),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Active Plan",
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnSurfaceVariant
                        )
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = SecondaryContainer
                        ) {
                            Text(
                                text = "Active",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = OnSecondaryContainer
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Monthly Plan",
                        style = MaterialTheme.typography.headlineMedium,
                        color = OnSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Breakfast • Lunch • Dinner",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ───── Today's Meals ─────
            SectionHeader(
                title = "Today's Status",
                action = "History",
                onAction = onNavigateToMeals
            )

            Spacer(modifier = Modifier.height(8.dp))

            val todayMeal = state.todayMeal

            MealToggleCard(
                title = "Breakfast",
                icon = "🌅",
                isChecked = todayMeal?.breakfast ?: false,
                onToggle = { onMarkMeal("breakfast", it) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            MealToggleCard(
                title = "Lunch",
                icon = "☀️",
                isChecked = todayMeal?.lunch ?: false,
                onToggle = { onMarkMeal("lunch", it) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            MealToggleCard(
                title = "Dinner",
                icon = "🌙",
                isChecked = todayMeal?.dinner ?: false,
                onToggle = { onMarkMeal("dinner", it) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            MealToggleCard(
                title = "Extra Meal",
                icon = "➕",
                isChecked = todayMeal?.extraMeal ?: false,
                onToggle = { onMarkMeal("extraMeal", it) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ───── Monthly Stats ─────
            SectionHeader(title = "This Month")

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatsCard(
                    title = "Total Meals",
                    value = "${state.mealStats.totalMeals}",
                    materialIcon = Icons.Outlined.Restaurant,
                    iconTint = Primary,
                    modifier = Modifier.weight(1f)
                )
                StatsCard(
                    title = "Days Attended",
                    value = "${state.mealStats.totalDays}",
                    materialIcon = Icons.Outlined.EventAvailable,
                    iconTint = Secondary,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatsCard(
                    title = "Skipped",
                    value = "${state.mealStats.totalSkipped}",
                    materialIcon = Icons.Outlined.EventBusy,
                    iconTint = Tertiary,
                    modifier = Modifier.weight(1f)
                )
                StatsCard(
                    title = "Total Cost",
                    value = "₹${state.mealStats.totalCost.toInt()}",
                    materialIcon = Icons.Outlined.Payments,
                    iconTint = OnSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
            }

            // Bottom padding for nav bar
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
