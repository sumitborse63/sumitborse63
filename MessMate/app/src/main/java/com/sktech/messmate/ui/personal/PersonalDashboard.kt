package com.sktech.messmate.ui.personal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sktech.messmate.data.model.User
import com.sktech.messmate.ui.components.*
import com.sktech.messmate.ui.customer.MealTrackingState
import com.sktech.messmate.ui.theme.*

@Composable
fun PersonalDashboard(
    user: User?,
    state: MealTrackingState,
    onMarkMeal: (String, Boolean) -> Unit,
    onNavigateToStats: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // ───── Top App Bar ─────
        MessMateTopBar(
            title = "MessMate",
            profileInitial = user?.name?.firstOrNull()?.uppercase() ?: "U"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // ───── Header ─────
            Text(
                text = "Personal Tracker",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = OnSurface
            )
            Text(
                text = "Track your meals & expenses",
                style = MaterialTheme.typography.bodyLarge,
                color = OnSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ───── Summary Cards Row ─────
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
                    title = "Monthly Spend",
                    value = "₹${state.mealStats.totalCost.toInt()}",
                    materialIcon = Icons.Outlined.Payments,
                    iconTint = Secondary,
                    modifier = Modifier.weight(1f)
                )
                StatsCard(
                    title = "Streak",
                    value = "${state.mealStats.currentStreak}",
                    materialIcon = Icons.Outlined.LocalFireDepartment,
                    iconTint = PrimaryContainer,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ───── Track Today ─────
            SectionHeader(title = "Track Today")

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
                title = "Extra Meal / Snack",
                icon = "🍿",
                isChecked = todayMeal?.extraMeal ?: false,
                onToggle = { onMarkMeal("extraMeal", it) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ───── Monthly Breakdown ─────
            SectionHeader(
                title = "This Month",
                action = "View All",
                onAction = onNavigateToStats
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatsCard(
                    title = "Breakfast",
                    value = "${state.mealStats.totalBreakfast}",
                    icon = "🌅",
                    modifier = Modifier.weight(1f)
                )
                StatsCard(
                    title = "Lunch",
                    value = "${state.mealStats.totalLunch}",
                    icon = "☀️",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatsCard(
                    title = "Dinner",
                    value = "${state.mealStats.totalDinner}",
                    icon = "🌙",
                    modifier = Modifier.weight(1f)
                )
                StatsCard(
                    title = "Avg/Day",
                    value = if (state.mealStats.totalDays > 0)
                        String.format("%.1f", state.mealStats.totalMeals.toFloat() / state.mealStats.totalDays)
                    else "0",
                    materialIcon = Icons.Outlined.TrendingUp,
                    iconTint = Tertiary,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
