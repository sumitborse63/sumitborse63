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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sktech.messmate.ui.components.*
import com.sktech.messmate.ui.theme.*

data class MemberInfo(
    val name: String,
    val id: String,
    val plan: String,
    val status: String, // "Active", "Pending", "Inactive"
    val paymentStatus: String // "Paid", "Due", "Overdue"
)

@Composable
fun ManageMembersScreen(
    onMemberClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    val tabs = listOf("Active", "Pending", "Inactive")

    val sampleMembers = remember {
        listOf(
            MemberInfo("Rahul Sharma", "MM-4921", "Monthly Fixed", "Active", "Paid"),
            MemberInfo("Priya Patel", "MM-4922", "Monthly Fixed", "Active", "Due"),
            MemberInfo("Amit Kumar", "MM-4923", "Per Meal", "Active", "Paid"),
            MemberInfo("Sneha Desai", "MM-4924", "Monthly Fixed", "Pending", "Paid"),
            MemberInfo("Vikram Singh", "MM-4925", "Monthly Fixed", "Inactive", "Overdue")
        )
    }

    val filteredMembers = sampleMembers.filter {
        it.status == tabs[selectedTab] &&
                (searchQuery.isEmpty() || it.name.contains(searchQuery, ignoreCase = true))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        MessMateTopBar()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Manage Members",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = OnSurface
            )
            Text(
                text = "View and manage your mess members",
                style = MaterialTheme.typography.bodyLarge,
                color = OnSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Search
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search members...", color = OnSurfaceVariant) },
                leadingIcon = { Icon(Icons.Outlined.Search, null, tint = OnSurfaceVariant) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = SurfaceVariant,
                    unfocusedContainerColor = SurfaceContainerLowest,
                    focusedContainerColor = SurfaceContainerLowest
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Tab Row
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = SurfaceContainer,
                contentColor = OnSurface,
                indicator = {
                    TabRowDefaults.SecondaryIndicator(
                        color = Primary
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        selectedContentColor = Primary,
                        unselectedContentColor = OnSurfaceVariant
                    )
                }
            }
        }

        // Member List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredMembers) { member ->
                MemberCard(member = member, onClick = { onMemberClick(member.id) })
            }

            if (filteredMembers.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Outlined.PersonOff,
                                null,
                                modifier = Modifier.size(48.dp),
                                tint = OnSurfaceVariant
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "No ${tabs[selectedTab].lowercase()} members",
                                style = MaterialTheme.typography.bodyLarge,
                                color = OnSurfaceVariant
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
private fun MemberCard(member: MemberInfo, onClick: () -> Unit) {
    Card(
        onClick = onClick,
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
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(44.dp)
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

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = member.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = OnSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(member.id, style = MaterialTheme.typography.labelMedium, color = OnSurfaceVariant)
                    Text("•", color = OnSurfaceVariant)
                    Text(member.plan, style = MaterialTheme.typography.labelMedium, color = OnSurfaceVariant)
                }
            }

            // Payment badge
            StatusBadge(
                text = member.paymentStatus,
                containerColor = when (member.paymentStatus) {
                    "Paid" -> SecondaryContainer
                    "Due" -> PrimaryFixed
                    else -> ErrorContainer
                },
                contentColor = when (member.paymentStatus) {
                    "Paid" -> OnSecondaryContainer
                    "Due" -> OnPrimaryContainer
                    else -> OnErrorContainer
                }
            )
        }
    }
}
