package com.sktech.messmate.data.model

import com.google.firebase.Timestamp

enum class UserRole {
    NONE, CUSTOMER, OWNER, PERSONAL
}

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val role: String = UserRole.NONE.name,
    val profileImageUrl: String = "",
    val currentMessId: String? = null,
    val createdAt: Timestamp = Timestamp.now()
) {
    fun getUserRole(): UserRole = try {
        UserRole.valueOf(role)
    } catch (e: Exception) {
        UserRole.NONE
    }
}
