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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sktech.messmate.data.model.User
import com.sktech.messmate.ui.components.*
import com.sktech.messmate.ui.theme.*

@Composable
fun OwnerDashboard(
    user: User?,
    onNavigateToMembers: () -> Unit,
    onNavigateToAttendance: () -> Unit,
    onNavigateToSetup: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // ───── Top App Bar ─────
        MessMateTopBar(
            profileInitial = user?.name?.firstOrNull()?.uppercase() ?: "O"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // ───── Dashboard Header ─────
            Text(
                text = "Dashboard",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = OnSurface
            )
            Text(
                text = "Overview of your mess operations",
                style = MaterialTheme.typography.bodyLarge,
                color = OnSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ───── Metrics Bento Grid ─────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Total Members — large card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(140.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = SurfaceContainerLowest
                    ),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Total Members",
                                style = MaterialTheme.typography.bodyMedium,
                                color = OnSurfaceVariant
                            )
                            Icon(
                                Icons.Outlined.People,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = "0",
                            style = MaterialTheme.typography.headlineLarge,
                            color = OnSurface
                        )
                        StatusBadge(
                            text = "Setup Required",
                            containerColor = PrimaryFixed,
                            contentColor = OnPrimaryContainer
                        )
                    }
                }

                // Right column: 2 mini cards
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatsCard(
                        title = "Today",
                        value = "0",
                        materialIcon = Icons.Outlined.CheckCircle,
                        iconTint = Secondary,
                        modifier = Modifier.fillMaxWidth()
                    )
                    StatsCard(
                        title = "Revenue",
                        value = "₹0",
                        materialIcon = Icons.Outlined.AccountBalanceWallet,
                        iconTint = Tertiary,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ───── Quick Actions ─────
            SectionHeader(title = "Quick Actions")

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionCard(
                    icon = Icons.Outlined.HowToReg,
                    title = "Mark\nAttendance",
                    color = Primary,
                    onClick = onNavigateToAttendance,
                    modifier = Modifier.weight(1f)
                )
                QuickActionCard(
                    icon = Icons.Outlined.People,
                    title = "View\nMembers",
                    color = Secondary,
                    onClick = onNavigateToMembers,
                    modifier = Modifier.weight(1f)
                )
                QuickActionCard(
                    icon = Icons.Outlined.Settings,
                    title = "Setup\nMess",
                    color = Tertiary,
                    onClick = onNavigateToSetup,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ───── Recent Activity ─────
            SectionHeader(title = "Recent Activity")

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SurfaceContainerLowest
                ),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Icon(
                        Icons.Outlined.Inbox,
                        contentDescription = null,
                        tint = OnSurfaceVariant,
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No activity yet",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = OnSurface,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Set up your mess to start managing members and tracking attendance.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnSurfaceVariant,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onNavigateToSetup,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Primary,
                            contentColor = OnPrimary
                        )
                    ) {
                        Text("Setup Mess", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
private fun QuickActionCard(
    icon: ImageVector,
    title: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MessMateThemeExt.colors.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(22.dp))
            }
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium,
                color = OnSurface,
                maxLines = 2,
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}
