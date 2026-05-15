package com.sktech.messmate.ui.auth

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.sktech.messmate.R
import com.sktech.messmate.ui.components.GradientButton
import com.sktech.messmate.ui.components.LoadingOverlay
import com.sktech.messmate.ui.theme.*

@Composable
fun LoginScreen(
    authState: AuthState,
    onSignIn: (String, String) -> Unit,
    onGoogleSignIn: (String) -> Unit,
    onNavigateToRegister: () -> Unit,
    onClearError: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }
    val googleSignInClient = remember { GoogleSignIn.getClient(context, gso) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.idToken?.let { onGoogleSignIn(it) }
            } catch (e: ApiException) {
                // Handle error
            }
        }
    }

    // Show error snackbar
    LaunchedEffect(authState.error) {
        if (authState.error != null) {
            kotlinx.coroutines.delay(3000)
            onClearError()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // ───── Gradient Header ─────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.38f)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                PrimaryContainer,
                                Primary,
                                Primary.copy(alpha = 0.85f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Logo circle
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .background(Color.White.copy(alpha = 0.2f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🍽️", fontSize = 36.sp)
                    }

                    Text(
                        text = "MessMate",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        letterSpacing = (-0.5).sp
                    )
                    Text(
                        text = "Your meal management companion",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            }

            // ───── Login Form Card ─────
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .offset(y = (-20).dp),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Background
                ),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Welcome Back",
                        style = MaterialTheme.typography.headlineMedium,
                        color = OnSurface
                    )
                    Text(
                        text = "Sign in to your account",
                        style = MaterialTheme.typography.bodyLarge,
                        color = OnSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Email field
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

                    // Password field
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
                                    contentDescription = "Toggle password",
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
                        else
                            PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Sign In Button
                    GradientButton(
                        text = "Sign In",
                        onClick = { onSignIn(email, password) },
                        isLoading = authState.isLoading,
                        enabled = email.isNotBlank() && password.length >= 6
                    )

                    // Divider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            color = SurfaceVariant
                        )
                        Text(
                            text = "  or  ",
                            style = MaterialTheme.typography.labelMedium,
                            color = OnSurfaceVariant
                        )
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            color = SurfaceVariant
                        )
                    }

                    // Google Sign-In Button
                    OutlinedButton(
                        onClick = {
                            googleSignInClient.signOut()
                            launcher.launch(googleSignInClient.signInIntent)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, SurfaceVariant),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = SurfaceContainerLowest
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "G",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4285F4)
                            )
                            Text(
                                text = "Continue with Google",
                                style = MaterialTheme.typography.labelLarge,
                                color = OnSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Register link
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Don't have an account? ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnSurfaceVariant
                        )
                        Text(
                            text = "Register",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Primary,
                            modifier = Modifier.clickable(onClick = onNavigateToRegister)
                        )
                    }
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
