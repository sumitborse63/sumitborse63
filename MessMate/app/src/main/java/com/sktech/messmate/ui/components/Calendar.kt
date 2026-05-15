package com.sktech.messmate.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sktech.messmate.ui.theme.*
import com.sktech.messmate.utils.DateUtils
import java.time.LocalDate

@Composable
fun CalendarStrip(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val weekDates = DateUtils.getWeekDates(selectedDate)
    val listState = rememberLazyListState()

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(weekDates) { date ->
            CalendarDayItem(
                date = date,
                isSelected = date == selectedDate,
                isToday = date == LocalDate.now(),
                onClick = { onDateSelected(date) }
            )
        }
    }
}

@Composable
private fun CalendarDayItem(
    date: LocalDate,
    isSelected: Boolean,
    isToday: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = when {
            isSelected -> Primary
            isToday -> PrimaryContainer
            else -> Color.Transparent
        },
        label = "bg"
    )
    val textColor = when {
        isSelected -> Color.White
        else -> MaterialTheme.colorScheme.onSurface
    }

    Column(
        modifier = Modifier
            .width(52.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = DateUtils.getDayName(date),
            style = MaterialTheme.typography.labelSmall,
            color = textColor.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
        Text(
            text = "${date.dayOfMonth}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = textColor,
            textAlign = TextAlign.Center
        )
        if (isToday && !isSelected) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(Primary)
            )
        }
    }
}

@Composable
fun MonthCalendarGrid(
    year: Int,
    month: Int,
    mealData: Map<String, Int>, // date string -> meal count (0-4)
    selectedDate: String?,
    onDateClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val dates = DateUtils.getMonthDates(year, month)
    val firstDayOfWeek = dates.first().dayOfWeek.value // 1=Mon, 7=Sun
    val paddingDays = firstDayOfWeek - 1

    Column(modifier = modifier.fillMaxWidth()) {
        // Day headers
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").forEach { day ->
                Text(
                    text = day,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Calendar grid
        val allCells = MutableList(paddingDays) { null as LocalDate? } + dates
        val rows = allCells.chunked(7)

        rows.forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                week.forEach { date ->
                    if (date != null) {
                        val dateStr = DateUtils.formatDate(date)
                        val mealCount = mealData[dateStr] ?: 0
                        val isSelected = dateStr == selectedDate
                        val isToday = date == LocalDate.now()

                        CalendarGridCell(
                            day = date.dayOfMonth,
                            mealCount = mealCount,
                            isSelected = isSelected,
                            isToday = isToday,
                            isFuture = date.isAfter(LocalDate.now()),
                            onClick = { onDateClick(dateStr) },
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                // Pad remaining cells in last row
                repeat(7 - week.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun CalendarGridCell(
    day: Int,
    mealCount: Int,
    isSelected: Boolean,
    isToday: Boolean,
    isFuture: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor = when {
        isSelected -> Primary
        mealCount >= 3 -> MealPresent.copy(alpha = 0.15f)
        mealCount in 1..2 -> MealPending.copy(alpha = 0.15f)
        !isFuture && mealCount == 0 -> MealAbsent.copy(alpha = 0.08f)
        else -> Color.Transparent
    }

    val dotColor = when {
        mealCount >= 3 -> MealPresent
        mealCount in 1..2 -> MealPending
        !isFuture && mealCount == 0 -> MealAbsent
        else -> Color.Transparent
    }

    Column(
        modifier = modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .clickable(enabled = !isFuture, onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$day",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = if (isToday || isSelected) FontWeight.Bold else FontWeight.Normal,
            color = when {
                isSelected -> Color.White
                isFuture -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                else -> MaterialTheme.colorScheme.onSurface
            }
        )
        if (!isFuture && mealCount > 0) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) Color.White else dotColor)
            )
        }
    }
}
