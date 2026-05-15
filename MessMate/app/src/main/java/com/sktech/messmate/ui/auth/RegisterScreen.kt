package com.sktech.messmate.ui.auth

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sktech.messmate.ui.components.GradientButton
import com.sktech.messmate.ui.components.LoadingOverlay
import com.sktech.messmate.ui.theme.*

@Composable
fun RegisterScreen(
    authState: AuthState,
    onSignUp: (String, String, String) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // ───── Gradient Header ─────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.30f)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Secondary,
                                Secondary.copy(alpha = 0.85f),
                                PrimaryContainer
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(Color.White.copy(alpha = 0.2f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🎉", fontSize = 32.sp)
                    }
                    Text(
                        text = "Create Account",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Text(
                        text = "Join MessMate today",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            }

            // ───── Registration Form ─────
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .offset(y = (-20).dp),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                colors = CardDefaults.cardColors(containerColor = Background),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Get Started",
                        style = MaterialTheme.typography.headlineMedium,
                        color = OnSurface
                    )

                    // Name
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Full Name") },
                        leadingIcon = {
                            Icon(Icons.Outlined.Person, contentDescription = null,
                                tint = OnSurfaceVariant)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = SurfaceVariant
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        singleLine = true
                    )

                    // Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        leadingIcon = {
                            Icon(Icons.Outlined.Email, contentDescription = null,
                                tint = OnSurfaceVariant)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = SurfaceVariant
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true
                    )

                    // Password
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        leadingIcon = {
                            Icon(Icons.Outlined.Lock, contentDescription = null,
                                tint = OnSurfaceVariant)
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    if (passwordVisible) Icons.Outlined.VisibilityOff
                                    else Icons.Outlined.Visibility,
                                    contentDescription = "Toggle",
                                    tint = OnSurfaceVariant
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = SurfaceVariant
                        ),
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true
                    )

                    // Confirm Password
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm Password") },
                        leadingIcon = {
                            Icon(Icons.Outlined.Lock, contentDescription = null,
                                tint = OnSurfaceVariant)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = SurfaceVariant
                        ),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        isError = confirmPassword.isNotEmpty() && password != confirmPassword,
                        supportingText = if (confirmPassword.isNotEmpty() && password != confirmPassword) {
                            { Text("Passwords do not match", color = Error) }
                        } else null
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Sign Up Button
                    GradientButton(
                        text = "Create Account",
                        onClick = { onSignUp(name, email, password) },
                        isLoading = authState.isLoading,
                        enabled = name.isNotBlank() &&
                                email.isNotBlank() &&
                                password.length >= 6 &&
                                password == confirmPassword
                    )

                    // Login link
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Already have an account? ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnSurfaceVariant
                        )
                        Text(
                            text = "Sign In",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Primary,
                            modifier = Modifier.clickable(onClick = onNavigateToLogin)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        // Error Banner
        AnimatedVisibility(
            visible = authState.error != null,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Error),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = authState.error ?: "",
                    color = OnError,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        LoadingOverlay(isLoading = authState.isLoading)
    }
}
