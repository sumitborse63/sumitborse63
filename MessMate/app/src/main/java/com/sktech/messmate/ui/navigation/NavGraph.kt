package com.sktech.messmate.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sktech.messmate.data.model.UserRole
import com.sktech.messmate.ui.auth.*
import com.sktech.messmate.ui.components.MessMateBottomBar
import com.sktech.messmate.ui.customer.*
import com.sktech.messmate.ui.owner.*
import com.sktech.messmate.ui.personal.*

@Composable
fun MessMateNavGraph(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val authState by authViewModel.authState.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Determine if bottom bar should show
    val showBottomBar = currentRoute != null &&
            currentRoute != Routes.Splash.route &&
            currentRoute != Routes.Login.route &&
            currentRoute != Routes.Register.route &&
            currentRoute != Routes.RoleSelection.route &&
            currentRoute != Routes.QRScanner.route

    val userRole = authState.user?.getUserRole() ?: UserRole.NONE

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = Routes.Splash.route,
            modifier = Modifier.fillMaxSize()
        ) {
            // ═══════════════════════════════
            // Auth Flow
            // ═══════════════════════════════
            composable(Routes.Splash.route) {
                SplashScreen(
                    onNavigate = { isLoggedIn, needsRole ->
                        navController.popBackStack()
                        when {
                            isLoggedIn && needsRole -> navController.navigate(Routes.RoleSelection.route)
                            isLoggedIn -> navigateToDashboard(navController, authState.user?.getUserRole() ?: UserRole.NONE)
                            else -> navController.navigate(Routes.Login.route)
                        }
                    },
                    authState = authState
                )
            }

            composable(Routes.Login.route) {
                LoginScreen(
                    authState = authState,
                    onSignIn = { email, pwd -> authViewModel.signInWithEmail(email, pwd) },
                    onGoogleSignIn = { token -> authViewModel.signInWithGoogle(token) },
                    onNavigateToRegister = {
                        navController.navigate(Routes.Register.route)
                    },
                    onClearError = { authViewModel.clearError() }
                )

                // Navigate on successful auth
                LaunchedEffect(authState.isLoggedIn, authState.needsRoleSelection) {
                    if (authState.isLoggedIn) {
                        navController.popBackStack(Routes.Login.route, true)
                        if (authState.needsRoleSelection) {
                            navController.navigate(Routes.RoleSelection.route)
                        } else {
                            navigateToDashboard(navController, authState.user?.getUserRole() ?: UserRole.NONE)
                        }
                    }
                }
            }

            composable(Routes.Register.route) {
                RegisterScreen(
                    authState = authState,
                    onSignUp = { name, email, pwd ->
                        authViewModel.signUpWithEmail(name, email, pwd)
                    },
                    onNavigateToLogin = {
                        navController.popBackStack()
                    }
                )

                LaunchedEffect(authState.isLoggedIn) {
                    if (authState.isLoggedIn && authState.needsRoleSelection) {
                        navController.popBackStack(Routes.Register.route, true)
                        navController.navigate(Routes.RoleSelection.route)
                    }
                }
            }

            composable(Routes.RoleSelection.route) {
                RoleSelectionScreen(
                    authState = authState,
                    onRoleSelected = { role ->
                        authViewModel.selectRole(role)
                    }
                )

                LaunchedEffect(authState.user?.role, authState.needsRoleSelection) {
                    val role = authState.user?.getUserRole()
                    if (role != null && role != UserRole.NONE && !authState.needsRoleSelection) {
                        navController.popBackStack(Routes.RoleSelection.route, true)
                        navigateToDashboard(navController, role)
                    }
                }
            }

            // ═══════════════════════════════
            // Customer Screens
            // ═══════════════════════════════
            composable(Routes.CustomerDashboard.route) {
                val customerVM: CustomerViewModel = hiltViewModel()
                val state by customerVM.state.collectAsState()

                CustomerDashboard(
                    user = authState.user,
                    state = state,
                    onMarkMeal = { type, value -> customerVM.toggleMeal(type, value) },
                    onNavigateToMeals = { navController.navigate(Routes.MyMeals.route) },
                    onNavigateToMenu = { navController.navigate(Routes.TodayMenu.route) }
                )
            }

            composable(Routes.MyMeals.route) {
                val customerVM: CustomerViewModel = hiltViewModel()
                val state by customerVM.state.collectAsState()

                MyMealsScreen(
                    state = state,
                    onDateClick = { dateStr ->
                        customerVM.selectDate(com.sktech.messmate.utils.DateUtils.parseDate(dateStr))
                    },
                    onMonthChange = { year, month ->
                        customerVM.loadMonthlyStats(year, month)
                    }
                )
            }

            composable(Routes.TodayMenu.route) {
                PlaceholderScreen(title = "Today's Menu", subtitle = "Coming soon")
            }

            composable(Routes.Discovery.route) {
                DiscoveryScreen()
            }

            composable(Routes.MealHistory.route) {
                MealHistoryScreen()
            }

            // ═══════════════════════════════
            // Owner Screens
            // ═══════════════════════════════
            composable(Routes.OwnerDashboard.route) {
                OwnerDashboard(
                    user = authState.user,
                    onNavigateToMembers = { navController.navigate(Routes.Members.route) },
                    onNavigateToAttendance = { navController.navigate(Routes.Attendance.route) },
                    onNavigateToSetup = { navController.navigate(Routes.MessSetup.route) }
                )
            }

            composable(Routes.Members.route) {
                ManageMembersScreen(
                    onMemberClick = { memberId ->
                        navController.navigate(Routes.MemberProfile.createRoute(memberId))
                    }
                )
            }

            composable(Routes.Attendance.route) {
                AttendanceScreen()
            }

            composable(Routes.MessSetup.route) {
                PlaceholderScreen(title = "Setup Mess", subtitle = "Mess setup coming soon")
            }

            composable(Routes.MenuManagement.route) {
                MenuManagementScreen()
            }

            composable(Routes.QRScanner.route) {
                QRScannerScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Routes.MemberProfile.route,
                arguments = listOf(navArgument("memberId") { type = NavType.StringType })
            ) { backStackEntry ->
                val memberId = backStackEntry.arguments?.getString("memberId") ?: ""
                MemberProfileScreen(
                    memberId = memberId,
                    onBack = { navController.popBackStack() }
                )
            }

            // ═══════════════════════════════
            // Personal Screens
            // ═══════════════════════════════
            composable(Routes.PersonalDashboard.route) {
                val customerVM: CustomerViewModel = hiltViewModel()
                val state by customerVM.state.collectAsState()

                PersonalDashboard(
                    user = authState.user,
                    state = state,
                    onMarkMeal = { type, value -> customerVM.toggleMeal(type, value) },
                    onNavigateToStats = { navController.navigate(Routes.PersonalStats.route) }
                )
            }

            composable(Routes.PersonalStats.route) {
                val customerVM: CustomerViewModel = hiltViewModel()
                val state by customerVM.state.collectAsState()

                MyMealsScreen(
                    state = state,
                    onDateClick = { dateStr ->
                        customerVM.selectDate(com.sktech.messmate.utils.DateUtils.parseDate(dateStr))
                    },
                    onMonthChange = { year, month ->
                        customerVM.loadMonthlyStats(year, month)
                    }
                )
            }

            // ═══════════════════════════════
            // Common
            // ═══════════════════════════════
            composable(Routes.Settings.route) {
                SettingsScreen(
                    user = authState.user,
                    onSignOut = {
                        authViewModel.signOut()
                        navController.popBackStack(navController.graph.startDestinationId, true)
                        navController.navigate(Routes.Login.route)
                    }
                )
            }
        }

        // Bottom Nav Bar
        if (showBottomBar && userRole != UserRole.NONE) {
            MessMateBottomBar(
                role = userRole,
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

private fun navigateToDashboard(navController: NavHostController, role: UserRole) {
    val destination = when (role) {
        UserRole.CUSTOMER -> Routes.CustomerDashboard.route
        UserRole.OWNER -> Routes.OwnerDashboard.route
        UserRole.PERSONAL -> Routes.PersonalDashboard.route
        else -> Routes.Login.route
    }
    navController.navigate(destination) {
        popUpTo(0) { inclusive = true }
    }
}

@Composable
fun PlaceholderScreen(title: String, subtitle: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SettingsScreen(
    user: com.sktech.messmate.data.model.User?,
    onSignOut: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp, start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = com.sktech.messmate.ui.theme.SurfaceContainerLowest
            ),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = user?.name ?: "User",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = user?.email ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Role: ${user?.role ?: "None"}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onSignOut,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = com.sktech.messmate.ui.theme.Error
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Sign Out", modifier = Modifier.padding(vertical = 8.dp))
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}
