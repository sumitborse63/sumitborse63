package com.sktech.messmate.data.model

import com.google.firebase.Timestamp

data class Mess(
    val messId: String = "",
    val ownerId: String = "",
    val name: String = "",
    val description: String = "",
    val address: String = "",
    val type: String = "VEG",  // VEG, NON_VEG, BOTH
    val monthlyFee: Double = 0.0,
    val perMealFee: Double = 0.0,
    val billingType: String = "FIXED",  // FIXED, PER_MEAL, HYBRID
    val breakfastTime: String = "",
    val lunchTime: String = "",
    val dinnerTime: String = "",
    val photos: List<String> = emptyList(),
    val rating: Double = 0.0,
    val ratingCount: Int = 0,
    val memberCount: Int = 0,
    val isActive: Boolean = true,
    val createdAt: Timestamp = Timestamp.now()
)
