package com.sktech.messmate.ui.auth

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sktech.messmate.data.model.UserRole
import com.sktech.messmate.ui.components.LoadingOverlay
import com.sktech.messmate.ui.theme.*

@Composable
fun RoleSelectionScreen(
    authState: AuthState,
    onRoleSelected: (UserRole) -> Unit
) {
    var selectedRole by remember { mutableStateOf<UserRole?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
        ) {
            // ───── Header ─────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(PrimaryContainer, Primary)
                        )
                    )
                    .padding(top = 56.dp, bottom = 32.dp, start = 20.dp, end = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "How will you use\nMessMate?",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Choose your role to get started",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            }

            // ───── Role Cards ─────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                RoleCard(
                    title = "Mess Owner",
                    subtitle = "Manage your mess, members, menu & billing",
                    icon = Icons.Outlined.Storefront,
                    iconEmoji = "🏠",
                    isSelected = selectedRole == UserRole.OWNER,
                    accentColor = PrimaryContainer,
                    onClick = { selectedRole = UserRole.OWNER }
                )

                RoleCard(
                    title = "Customer",
                    subtitle = "Find messes, track meals & manage subscriptions",
                    icon = Icons.Outlined.Restaurant,
                    iconEmoji = "🍽️",
                    isSelected = selectedRole == UserRole.CUSTOMER,
                    accentColor = SecondaryContainer,
                    onClick = { selectedRole = UserRole.CUSTOMER }
                )

                RoleCard(
                    title = "Personal Tracker",
                    subtitle = "Track your own meals, expenses & diet habits",
                    icon = Icons.Outlined.EditNote,
                    iconEmoji = "📝",
                    isSelected = selectedRole == UserRole.PERSONAL,
                    accentColor = TertiaryFixed,
                    onClick = { selectedRole = UserRole.PERSONAL }
                )

                Spacer(modifier = Modifier.weight(1f))

                // Continue button
                Button(
                    onClick = { selectedRole?.let { onRoleSelected(it) } },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    enabled = selectedRole != null && !authState.isLoading,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = OnPrimary,
                        disabledContainerColor = Outline.copy(alpha = 0.2f)
                    ),
                    elevation = ButtonDefaults.buttonElevation(4.dp)
                ) {
                    Text(
                        text = "Continue",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        LoadingOverlay(isLoading = authState.isLoading)
    }
}

@Composable
private fun RoleCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconEmoji: String,
    isSelected: Boolean,
    accentColor: Color,
    onClick: () -> Unit
) {
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Primary else SurfaceVariant,
        label = "role_border"
    )
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) accentColor.copy(alpha = 0.15f)
        else SurfaceContainerLowest,
        label = "role_bg"
    )

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = borderColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 4.dp else 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Icon circle
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) accentColor else SurfaceContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(text = iconEmoji, fontSize = 24.sp)
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = OnSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnSurfaceVariant
                )
            }

            if (isSelected) {
                Icon(
                    Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
