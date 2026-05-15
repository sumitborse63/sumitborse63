package com.sktech.messmate.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.sktech.messmate.ui.components.*
import com.sktech.messmate.ui.theme.*

data class AttendanceMember(
    val name: String,
    val id: String,
    val breakfast: Boolean = false,
    val lunch: Boolean = false,
    val dinner: Boolean = false
)

@Composable
fun AttendanceScreen(
    modifier: Modifier = Modifier
) {
    val sampleMembers = remember {
        mutableStateListOf(
            AttendanceMember("Rahul Sharma", "MM-4921", breakfast = true, lunch = true),
            AttendanceMember("Priya Patel", "MM-4922", breakfast = true),
            AttendanceMember("Amit Kumar", "MM-4923"),
            AttendanceMember("Sneha Desai", "MM-4924", breakfast = true, lunch = true, dinner = true),
            AttendanceMember("Vikram Singh", "MM-4925", lunch = true)
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Daily Attendance",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                        Text(
                            text = "Mark attendance for today",
                            style = MaterialTheme.typography.bodyLarge,
                            color = OnSurfaceVariant
                        )
                    }
                    // QR Scanner button
                    FilledIconButton(
                        onClick = { },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Primary,
                            contentColor = OnPrimary
                        )
                    ) {
                        Icon(Icons.Outlined.QrCodeScanner, contentDescription = "QR Scanner")
                    }
                }
            }

            // Date header
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainer)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { }) {
                            Icon(Icons.Outlined.ChevronLeft, null, tint = OnSurfaceVariant)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Today, October 24",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = OnSurface
                            )
                            Text(
                                text = "Thursday",
                                style = MaterialTheme.typography.labelMedium,
                                color = OnSurfaceVariant
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(Icons.Outlined.ChevronRight, null, tint = OnSurfaceVariant)
                        }
                    }
                }
            }

            // Summary stats
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val presentCount = sampleMembers.count { it.breakfast || it.lunch || it.dinner }
                    StatsCard(
                        title = "Present",
                        value = "$presentCount",
                        materialIcon = Icons.Outlined.CheckCircle,
                        iconTint = Secondary,
                        modifier = Modifier.weight(1f)
                    )
                    StatsCard(
                        title = "Absent",
                        value = "${sampleMembers.size - presentCount}",
                        materialIcon = Icons.Outlined.Cancel,
                        iconTint = Error,
                        modifier = Modifier.weight(1f)
                    )
                    StatsCard(
                        title = "Total",
                        value = "${sampleMembers.size}",
                        materialIcon = Icons.Outlined.People,
                        iconTint = OnSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Member attendance list
            items(sampleMembers) { member ->
                AttendanceMemberCard(member = member)
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
private fun AttendanceMemberCard(member: AttendanceMember) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(PrimaryFixed),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = member.name.first().uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        color = OnPrimaryContainer
                    )
                }
                Column {
                    Text(
                        text = member.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = OnSurface
                    )
                    Text(
                        text = member.id,
                        style = MaterialTheme.typography.labelMedium,
                        color = OnSurfaceVariant
                    )
                }
            }

            // Meal check icons
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MealCheckbox("B", member.breakfast)
                MealCheckbox("L", member.lunch)
                MealCheckbox("D", member.dinner)
            }
        }
    }
}

@Composable
private fun MealCheckbox(label: String, isChecked: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(if (isChecked) SecondaryContainer else SurfaceContainer),
            contentAlignment = Alignment.Center
        ) {
            if (isChecked) {
                Icon(
                    Icons.Outlined.Check,
                    contentDescription = null,
                    tint = OnSecondaryContainer,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(label, style = MaterialTheme.typography.labelMedium, color = OnSurfaceVariant)
    }
}
