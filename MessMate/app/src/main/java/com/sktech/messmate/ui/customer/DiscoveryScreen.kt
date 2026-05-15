package com.sktech.messmate.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.sktech.messmate.ui.components.MessMateTopBar
import com.sktech.messmate.ui.theme.*

data class MessItem(
    val name: String,
    val location: String,
    val rating: Float,
    val price: String,
    val type: String, // "Veg", "Non-Veg", "Both"
    val meals: String
)

@Composable
fun DiscoveryScreen(
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Veg", "Non-Veg", "Tiffin", "Nearby")

    val sampleMesses = remember {
        listOf(
            MessItem("Shree Krishna Mess", "Kothrud, Pune", 4.5f, "₹3,500/mo", "Veg", "B • L • D"),
            MessItem("Annapurna Tiffin", "Wakad, Pune", 4.2f, "₹2,800/mo", "Veg", "L • D"),
            MessItem("Royal Mess House", "Hinjewadi, Pune", 4.0f, "₹4,000/mo", "Both", "B • L • D"),
            MessItem("Ghar Ka Khana", "Baner, Pune", 4.7f, "₹3,200/mo", "Veg", "L • D"),
            MessItem("Foodie's Paradise", "Aundh, Pune", 3.8f, "₹3,800/mo", "Non-Veg", "B • L • D"),
            MessItem("Mom's Kitchen", "Karve Nagar", 4.6f, "₹2,500/mo", "Veg", "L • D")
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
                Text(
                    text = "Find a Mess",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Discover the best messes near you",
                    style = MaterialTheme.typography.bodyLarge,
                    color = OnSurfaceVariant
                )
            }

            // Search Bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search by name, area...", color = OnSurfaceVariant) },
                    leadingIcon = {
                        Icon(Icons.Outlined.Search, contentDescription = null, tint = OnSurfaceVariant)
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                        unfocusedBorderColor = SurfaceVariant,
                        unfocusedContainerColor = SurfaceContainerLowest,
                        focusedContainerColor = SurfaceContainerLowest
                    ),
                    singleLine = true
                )
            }

            // Filter Chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filters) { filter ->
                        FilterChip(
                            selected = selectedFilter == filter,
                            onClick = { selectedFilter = filter },
                            label = {
                                Text(
                                    text = filter,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = SecondaryContainer,
                                selectedLabelColor = OnSecondaryContainer,
                                containerColor = SurfaceContainer,
                                labelColor = OnSurfaceVariant
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                    }
                }
            }

            // Mess Cards
            items(sampleMesses) { mess ->
                MessCard(mess = mess)
            }

            // Bottom padding
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
private fun MessCard(mess: MessItem) {
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
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = mess.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = OnSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            Icons.Outlined.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = OnSurfaceVariant
                        )
                        Text(
                            text = mess.location,
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnSurfaceVariant
                        )
                    }
                }

                // Type badge
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = when (mess.type) {
                        "Veg" -> SecondaryContainer
                        "Non-Veg" -> ErrorContainer
                        else -> SurfaceContainer
                    }
                ) {
                    Text(
                        text = mess.type,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = when (mess.type) {
                            "Veg" -> OnSecondaryContainer
                            "Non-Veg" -> OnErrorContainer
                            else -> OnSurface
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Rating, Price, Meals row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Rating
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Outlined.Star,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = PrimaryContainer
                    )
                    Text(
                        text = "${mess.rating}",
                        style = MaterialTheme.typography.labelLarge,
                        color = OnSurface
                    )
                }

                // Meals
                Text(
                    text = mess.meals,
                    style = MaterialTheme.typography.labelMedium,
                    color = OnSurfaceVariant
                )

                // Price
                Text(
                    text = mess.price,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = Primary
                )
            }
        }
    }
}
