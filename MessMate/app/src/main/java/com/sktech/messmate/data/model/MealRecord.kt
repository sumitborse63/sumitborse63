package com.sktech.messmate.data.model

import com.google.firebase.Timestamp

data class MealRecord(
    val recordId: String = "",
    val userId: String = "",
    val messId: String? = null,
    val date: String = "",  // "2026-05-14"
    val breakfast: Boolean = false,
    val lunch: Boolean = false,
    val dinner: Boolean = false,
    val extraMeal: Boolean = false,
    val breakfastCost: Double = 0.0,
    val lunchCost: Double = 0.0,
    val dinnerCost: Double = 0.0,
    val extraMealCost: Double = 0.0,
    val markedBy: String = "SELF",  // SELF, OWNER, QR
    val notes: String = "",
    val createdAt: Timestamp = Timestamp.now()
) {
    val totalMeals: Int
        get() = listOf(breakfast, lunch, dinner, extraMeal).count { it }

    val totalCost: Double
        get() = breakfastCost + lunchCost + dinnerCost + extraMealCost
}

data class MealStats(
    val totalBreakfast: Int = 0,
    val totalLunch: Int = 0,
    val totalDinner: Int = 0,
    val totalExtraMeals: Int = 0,
    val totalMeals: Int = 0,
    val totalSkipped: Int = 0,
    val totalDays: Int = 0,
    val totalCost: Double = 0.0,
    val currentStreak: Int = 0
)
