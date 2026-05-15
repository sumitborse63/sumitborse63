package com.sktech.messmate.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sktech.messmate.ui.theme.*

/**
 * Top app bar matching the Stitch design — brand title center, profile/notification icons.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessMateTopBar(
    title: String = "MessMate",
    onProfileClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    profileInitial: String = "U"
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Profile avatar
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(PrimaryFixed)
                    .clickable(onClick = onProfileClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = profileInitial,
                    style = MaterialTheme.typography.labelLarge,
                    color = OnPrimaryContainer
                )
            }

            // Brand title
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )

            // Notification bell
            IconButton(onClick = onNotificationClick) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    tint = OnSurfaceVariant
                )
            }
        }
    }
}


/**
 * Meal toggle card matching Stitch surface-container design.
 */
@Composable
fun MealToggleCard(
    title: String,
    icon: String,
    isChecked: Boolean,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedScale by animateFloatAsState(
        targetValue = if (isChecked) 1.02f else 1f,
        animationSpec = spring(dampingRatio = 0.6f),
        label = "scale"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isChecked) Secondary else Color.Transparent,
        label = "border"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .scale(animatedScale)
            .clickable { onToggle(!isChecked) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isChecked)
                MessMateThemeExt.colors.surfaceContainerLowest
            else
                MessMateThemeExt.colors.surfaceContainer
        ),
        border = if (isChecked) BorderStroke(2.dp, borderColor) else
            BorderStroke(1.dp, SurfaceVariant),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isChecked) 4.dp else 0.dp
        )
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
                Text(text = icon, fontSize = 28.sp)
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = if (isChecked) "Marked" else "Not marked",
                        style = MaterialTheme.typography.labelMedium,
                        color = if (isChecked) Secondary else OnSurfaceVariant
                    )
                }
            }

            // Check icon
            Icon(
                imageVector = if (isChecked) Icons.Outlined.Check
                else Icons.Outlined.RadioButtonUnchecked,
                contentDescription = null,
                tint = if (isChecked) Secondary else OutlineVariant,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

/**
 * Stats card matching Stitch bento-grid pattern.
 */
@Composable
fun StatsCard(
    title: String,
    value: String,
    icon: String = "",
    materialIcon: ImageVector? = null,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    subtitle: String = "",
    accentColor: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MessMateThemeExt.colors.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (materialIcon != null) {
                Icon(
                    imageVector = materialIcon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
            } else if (icon.isNotEmpty()) {
                Text(text = icon, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(4.dp))
            }

            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = OnSurfaceVariant,
                textAlign = TextAlign.Center
            )
            if (subtitle.isNotEmpty()) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = OnSurfaceVariant
                )
            }
        }
    }
}

/**
 * Primary action button — solid primary color, no gradient.
 */
@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = OnPrimary,
            disabledContainerColor = Outline.copy(alpha = 0.3f),
            disabledContentColor = OnSurfaceVariant
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 2.dp
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = OnPrimary,
                modifier = Modifier.size(22.dp),
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * Loading overlay with surface card.
 */
@Composable
fun LoadingOverlay(
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MessMateThemeExt.colors.surfaceContainerLowest
                ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CircularProgressIndicator(
                        color = Primary,
                        strokeWidth = 3.dp
                    )
                    Text(
                        text = "Please wait...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnSurface
                    )
                }
            }
        }
    }
}

/**
 * Section header matching Stitch pattern.
 */
@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    action: String? = null,
    onAction: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        if (action != null && onAction != null) {
            TextButton(onClick = onAction) {
                Text(
                    text = action,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

/**
 * Status badge chip matching Stitch label badges.
 */
@Composable
fun StatusBadge(
    text: String,
    containerColor: Color = SecondaryContainer,
    contentColor: Color = OnSecondaryContainer,
    icon: ImageVector? = null
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = containerColor,
        contentColor = contentColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
