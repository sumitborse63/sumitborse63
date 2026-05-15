package com.sktech.messmate.ui.navigation

sealed class Routes(val route: String) {
    // Auth
    object Splash : Routes("splash")
    object Login : Routes("login")
    object Register : Routes("register")
    object RoleSelection : Routes("role_selection")

    // Customer
    object CustomerDashboard : Routes("customer/dashboard")
    object MealTracking : Routes("customer/meals")
    object MyMeals : Routes("customer/my_meals")
    object TodayMenu : Routes("customer/menu")
    object Discovery : Routes("customer/discovery")
    object MealHistory : Routes("customer/history")

    // Owner
    object OwnerDashboard : Routes("owner/dashboard")
    object Members : Routes("owner/members")
    object Attendance : Routes("owner/attendance")
    object MessSetup : Routes("owner/setup")
    object MenuManagement : Routes("owner/menu")
    object QRScanner : Routes("owner/qr_scanner")
    object MemberProfile : Routes("owner/member/{memberId}") {
        fun createRoute(memberId: String) = "owner/member/$memberId"
    }

    // Personal
    object PersonalDashboard : Routes("personal/dashboard")
    object PersonalMealEntry : Routes("personal/entry")
    object PersonalStats : Routes("personal/stats")

    // Common
    object Settings : Routes("settings")
}
