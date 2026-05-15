package com.sktech.messmate.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sktech.messmate.ui.components.*
import com.sktech.messmate.ui.theme.*

data class MealHistoryDay(
    val day: Int,
    val month: String,
    val dayName: String,
    val breakfast: Boolean?,
    val lunch: Boolean?,
    val dinner: Boolean?
)

@Composable
fun MealHistoryScreen(
    modifier: Modifier = Modifier
) {
    val sampleDays = remember {
        listOf(
            MealHistoryDay(24, "OCT", "Today", true, true, null),
            MealHistoryDay(23, "OCT", "Wednesday", false, true, true),
            MealHistoryDay(22, "OCT", "Tuesday", true, true, true),
            MealHistoryDay(21, "OCT", "Monday", true, true, true),
            MealHistoryDay(20, "OCT", "Sunday", true, false, true)
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
            // ───── October Summary ─────
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "October Summary",
                        style = MaterialTheme.typography.headlineMedium,
                        color = OnSurface
                    )
                    Text(
                        text = "Oct 1 - Oct 31",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnSurfaceVariant
                    )
                }
            }

            // Summary bento
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Amount Due Card
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Amount Due", style = MaterialTheme.typography.bodyMedium, color = OnSurfaceVariant)
                                StatusBadge(
                                    text = "Pending",
                                    containerColor = ErrorContainer,
                                    contentColor = OnErrorContainer,
                                    icon = Icons.Outlined.Pending
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "₹3,500",
                                style = MaterialTheme.typography.headlineLarge,
                                color = OnSurface
                            )
                            Text(
                                text = "Due by Nov 5th",
                                style = MaterialTheme.typography.bodyMedium,
                                color = OnSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Primary, contentColor = OnPrimary
                                )
                            ) {
                                Text("Pay Now", style = MaterialTheme.typography.labelLarge)
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(Icons.Outlined.ArrowForward, null, Modifier.size(18.dp))
                            }
                        }
                    }

                    // Stats grid
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            StatsCard(
                                title = "Meals",
                                value = "45",
                                materialIcon = Icons.Outlined.Restaurant,
                                iconTint = Primary,
                                modifier = Modifier.weight(1f)
                            )
                            StatsCard(
                                title = "Days",
                                value = "22",
                                materialIcon = Icons.Outlined.EventAvailable,
                                iconTint = Secondary,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            StatsCard(
                                title = "Skipped",
                                value = "3",
                                materialIcon = Icons.Outlined.EventBusy,
                                iconTint = Tertiary,
                                modifier = Modifier.weight(1f)
                            )
                            StatsCard(
                                title = "Avg/Meal",
                                value = "₹78",
                                materialIcon = Icons.Outlined.Payments,
                                iconTint = OnSurfaceVariant,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // ───── Meal History ─────
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Meal History",
                        style = MaterialTheme.typography.headlineMedium,
                        color = OnSurface
                    )
                    TextButton(onClick = { }) {
                        Icon(Icons.Outlined.FilterList, null, Modifier.size(18.dp), tint = Primary)
                        Spacer(Modifier.width(4.dp))
                        Text("Filter", style = MaterialTheme.typography.labelLarge, color = Primary)
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column {
                        sampleDays.forEachIndexed { index, day ->
                            HistoryDayRow(day)
                            if (index < sampleDays.lastIndex) {
                                HorizontalDivider(color = SurfaceVariant)
                            }
                        }
                        // View Older
                        TextButton(
                            onClick = { },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text("View Older Entries", color = Primary, style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
private fun HistoryDayRow(day: MealHistoryDay) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Date box
            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainer)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("${day.day}", style = MaterialTheme.typography.labelLarge, color = OnSurface)
                    Text(day.month, style = MaterialTheme.typography.labelMedium, color = OnSurfaceVariant)
                }
            }
            Text(
                text = day.dayName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = OnSurface
            )
        }

        // Meal status icons
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            MealStatusIcon("B'fast", day.breakfast)
            MealStatusIcon("Lunch", day.lunch)
            MealStatusIcon("Dinner", day.dinner)
        }
    }
}

@Composable
private fun MealStatusIcon(label: String, status: Boolean?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = when (status) {
                true -> Icons.Outlined.CheckCircle
                false -> Icons.Outlined.Cancel
                null -> Icons.Outlined.RadioButtonUnchecked
            },
            contentDescription = null,
            tint = when (status) {
                true -> Secondary
                false -> Error
                null -> OutlineVariant
            },
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(label, style = MaterialTheme.typography.labelMedium, color = OnSurfaceVariant)
    }
}
