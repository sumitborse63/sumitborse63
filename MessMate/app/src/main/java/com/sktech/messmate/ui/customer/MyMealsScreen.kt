package com.sktech.messmate.ui.customer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sktech.messmate.ui.components.*
import com.sktech.messmate.ui.theme.*
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun MyMealsScreen(
    state: MealTrackingState,
    onDateClick: (String) -> Unit,
    onMonthChange: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentYearMonth by remember { mutableStateOf(YearMonth.now()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 48.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Title
        Text(
            text = "My Meals",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "View your meal history",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Month selector
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = {
                        currentYearMonth = currentYearMonth.minusMonths(1)
                        onMonthChange(currentYearMonth.year, currentYearMonth.monthValue)
                    }) {
                        Text("← Prev")
                    }

                    Text(
                        text = "${currentYearMonth.month.name.lowercase()
                            .replaceFirstChar { it.uppercase() }} ${currentYearMonth.year}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    TextButton(
                        onClick = {
                            if (currentYearMonth < YearMonth.now()) {
                                currentYearMonth = currentYearMonth.plusMonths(1)
                                onMonthChange(currentYearMonth.year, currentYearMonth.monthValue)
                            }
                        },
                        enabled = currentYearMonth < YearMonth.now()
                    ) {
                        Text("Next →")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Calendar Grid
                val mealData = state.monthlyMeals.associate { it.date to it.totalMeals }
                MonthCalendarGrid(
                    year = currentYearMonth.year,
                    month = currentYearMonth.monthValue,
                    mealData = mealData,
                    selectedDate = state.selectedDate.toString(),
                    onDateClick = onDateClick
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Legend
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Legend",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    LegendItem(color = MealPresent, label = "All meals")
                    LegendItem(color = MealPending, label = "Partial")
                    LegendItem(color = MealAbsent, label = "Missed")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Monthly Summary Stats
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text(
                text = "Monthly Summary",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatsCard(
                    title = "Total Meals",
                    value = "${state.mealStats.totalMeals}",
                    icon = "🍽️",
                    modifier = Modifier.weight(1f)
                )
                StatsCard(
                    title = "Skipped",
                    value = "${state.mealStats.totalSkipped}",
                    icon = "⏭️",
                    accentColor = MealPending,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatsCard(
                    title = "Streak",
                    value = "${state.mealStats.currentStreak}",
                    icon = "🔥",
                    subtitle = "days",
                    accentColor = Error,
                    modifier = Modifier.weight(1f)
                )
                StatsCard(
                    title = "Spent",
                    value = "₹${state.mealStats.totalCost.toInt()}",
                    icon = "💰",
                    accentColor = Secondary,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
private fun LegendItem(
    color: androidx.compose.ui.graphics.Color,
    label: String
) {
    Row(
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .padding(1.dp)
                .then(
                    Modifier.aspectRatio(1f)
                )
        ) {
            androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(color = color)
            }
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
