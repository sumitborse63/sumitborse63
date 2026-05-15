package com.sktech.messmate.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sktech.messmate.ui.components.*
import com.sktech.messmate.ui.theme.*

@Composable
fun MemberProfileScreen(
    memberId: String = "",
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        MessMateTopBar()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ───── Profile Header ─────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Box {
                    // Gradient banner
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(PrimaryFixed, SurfaceVariant)
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(32.dp))

                        // Avatar
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(PrimaryFixed)
                                .then(
                                    Modifier
                                        .clip(CircleShape)
                                        .background(SurfaceContainerLowest, CircleShape)
                                        .padding(3.dp)
                                        .clip(CircleShape)
                                        .background(PrimaryFixed)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "RS", style = MaterialTheme.typography.headlineMedium, color = OnPrimaryContainer)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Rahul Sharma",
                            style = MaterialTheme.typography.headlineMedium,
                            color = OnSurface
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            StatusBadge(
                                text = "MM-4921",
                                containerColor = SurfaceVariant,
                                contentColor = OnSurfaceVariant,
                                icon = Icons.Outlined.Badge
                            )
                            StatusBadge(
                                text = "Joined Oct 12, 2023",
                                containerColor = SurfaceContainer,
                                contentColor = OnSurfaceVariant,
                                icon = Icons.Outlined.CalendarToday
                            )
                            StatusBadge(
                                text = "Active",
                                containerColor = SecondaryContainer,
                                contentColor = OnSecondaryContainer,
                                icon = Icons.Outlined.CheckCircle
                            )
                        }
                    }
                }
            }

            // ───── Quick Actions ─────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, OutlineVariant)
                ) {
                    Icon(Icons.Outlined.Edit, null, Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Edit", style = MaterialTheme.typography.labelLarge)
                }
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, OutlineVariant)
                ) {
                    Icon(Icons.Outlined.PauseCircle, null, Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Pause", style = MaterialTheme.typography.labelLarge)
                }
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary, contentColor = OnPrimary)
                ) {
                    Icon(Icons.Outlined.Chat, null, Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Message", style = MaterialTheme.typography.labelLarge)
                }
            }

            // ───── Bento Grid: Subscription, Preferences, Attendance ─────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Subscription
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Outlined.CardMembership, null, tint = Primary, modifier = Modifier.size(20.dp))
                            Text("Subscription", style = MaterialTheme.typography.headlineMedium, color = OnSurface)
                        }
                        Spacer(Modifier.height(12.dp))
                        DetailRow("Plan Type", "Monthly Fixed")
                        DetailRow("Meals/Day", "2 (L & D)")
                        DetailRow("Renewal", "Nov 12, 2023")
                    }
                }

                // Preferences
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Outlined.Restaurant, null, tint = Primary, modifier = Modifier.size(20.dp))
                            Text("Preferences", style = MaterialTheme.typography.headlineMedium, color = OnSurface)
                        }
                        Spacer(Modifier.height(12.dp))
                        Text("Dietary", style = MaterialTheme.typography.bodyMedium, color = OnSurfaceVariant)
                        StatusBadge("Pure Veg", SecondaryContainer, OnSecondaryContainer)
                        Spacer(Modifier.height(8.dp))
                        Text("Spice Level", style = MaterialTheme.typography.bodyMedium, color = OnSurfaceVariant)
                        StatusBadge("Medium", SurfaceVariant, OnSurface)
                        Spacer(Modifier.height(8.dp))
                        Text("Allergies", style = MaterialTheme.typography.bodyMedium, color = OnSurfaceVariant)
                        StatusBadge("No Peanuts", ErrorContainer, OnErrorContainer)
                    }
                }
            }

            // ───── Attendance Stats ─────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Outlined.FactCheck, null, tint = Primary, modifier = Modifier.size(20.dp))
                        Text("Oct Attendance", style = MaterialTheme.typography.headlineMedium, color = OnSurface)
                    }
                    Spacer(Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        AttendanceStat("42", "Consumed", SecondaryContainer, OnSecondaryContainer)
                        AttendanceStat("4", "Skipped", SurfaceVariant, OnSurface)
                    }
                }
            }

            // ───── Financial Summary ─────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = SurfaceContainerHigh
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
                                Icon(Icons.Outlined.AccountBalanceWallet, null, tint = Primary, modifier = Modifier.size(20.dp))
                                Text("Financial", style = MaterialTheme.typography.headlineMedium, color = OnSurface)
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                Column {
                                    Text("Total Paid", style = MaterialTheme.typography.bodyMedium, color = OnSurfaceVariant)
                                    Text("₹4,500", style = MaterialTheme.typography.headlineMedium, color = OnSurface)
                                }
                                Column {
                                    Text("Dues", style = MaterialTheme.typography.bodyMedium, color = OnSurfaceVariant)
                                    Text("₹0", style = MaterialTheme.typography.headlineMedium, color = Error)
                                }
                            }
                        }
                    }

                    // Transactions
                    TransactionRow("Oct 12, 2023", "Monthly Renewal", "₹4,500", "Paid")
                    HorizontalDivider(color = SurfaceVariant)
                    TransactionRow("Sep 12, 2023", "Monthly Renewal", "₹4,500", "Paid")
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = OnSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = OnSurface)
    }
    HorizontalDivider(color = SurfaceVariant)
}

@Composable
private fun AttendanceStat(
    value: String,
    label: String,
    bgColor: Color,
    textColor: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(bgColor),
            contentAlignment = Alignment.Center
        ) {
            Text(value, style = MaterialTheme.typography.headlineMedium, color = textColor)
        }
        Spacer(Modifier.height(4.dp))
        Text(label, style = MaterialTheme.typography.bodyMedium, color = OnSurfaceVariant)
    }
}

@Composable
private fun TransactionRow(date: String, desc: String, amount: String, status: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(date, style = MaterialTheme.typography.bodyMedium, color = OnSurface)
            Text(desc, style = MaterialTheme.typography.bodyMedium, color = OnSurface)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(amount, style = MaterialTheme.typography.bodyMedium, color = OnSurface)
            StatusBadge(status, SecondaryContainer, OnSecondaryContainer)
        }
    }
}
