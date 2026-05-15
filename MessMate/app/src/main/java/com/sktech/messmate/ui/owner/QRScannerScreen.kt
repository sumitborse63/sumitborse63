package com.sktech.messmate.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sktech.messmate.ui.theme.*

@Composable
fun QRScannerScreen(
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
    ) {
        // ───── Simulated Camera Background ─────
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2A2A2A).copy(alpha = 0.7f))
        )

        // ───── Top Navigation ─────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
            ) {
                Icon(Icons.Outlined.ArrowBack, "Go back", tint = Color.White)
            }
            Text(
                text = "Scan for Meal",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(40.dp))
        }

        // ───── Scanner Frame ─────
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // QR Frame
            Box(
                modifier = Modifier.size(260.dp),
                contentAlignment = Alignment.Center
            ) {
                // Scanning line
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Secondary.copy(alpha = 0.5f))
                )

                // Corner brackets
                // Top Left
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.TopStart)
                        .border(
                            width = 4.dp,
                            color = Primary,
                            shape = RoundedCornerShape(topStart = 12.dp)
                        )
                )
                // Top Right
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.TopEnd)
                        .border(
                            width = 4.dp,
                            color = Primary,
                            shape = RoundedCornerShape(topEnd = 12.dp)
                        )
                )
                // Bottom Left
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.BottomStart)
                        .border(
                            width = 4.dp,
                            color = Primary,
                            shape = RoundedCornerShape(bottomStart = 12.dp)
                        )
                )
                // Bottom Right
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.BottomEnd)
                        .border(
                            width = 4.dp,
                            color = Primary,
                            shape = RoundedCornerShape(bottomEnd = 12.dp)
                        )
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Instruction text
            Text(
                text = "Align the QR code within the frame\nto mark attendance",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Controls
            Row(
                horizontalArrangement = Arrangement.spacedBy(40.dp)
            ) {
                ScannerControl(icon = Icons.Outlined.FlashlightOn, label = "Flashlight")
                ScannerControl(icon = Icons.Outlined.Image, label = "Gallery")
            }
        }
    }
}

@Composable
private fun ScannerControl(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = { },
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.2f))
                .border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape)
        ) {
            Icon(icon, label, tint = Color.White)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}
