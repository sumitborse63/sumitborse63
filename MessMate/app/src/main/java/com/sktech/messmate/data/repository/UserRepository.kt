package com.sktech.messmate.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.sktech.messmate.data.model.User
import com.sktech.messmate.data.model.UserRole
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val usersCollection = firestore.collection("users")

    suspend fun createUser(user: User): Result<Unit> {
        return try {
            usersCollection.document(user.uid).set(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getUser(uid: String): Flow<User?> = callbackFlow {
        val listener = usersCollection.document(uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(null)
                    return@addSnapshotListener
                }
                val user = snapshot?.toObject(User::class.java)
                trySend(user)
            }
        awaitClose { listener.remove() }
    }

    suspend fun getUserOnce(uid: String): Result<User?> {
        return try {
            val snapshot = usersCollection.document(uid).get().await()
            val user = snapshot.toObject(User::class.java)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUserRole(uid: String, role: UserRole): Result<Unit> {
        return try {
            usersCollection.document(uid)
                .update("role", role.name)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateProfile(uid: String, updates: Map<String, Any>): Result<Unit> {
        return try {
            usersCollection.document(uid).update(updates).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun userExists(uid: String): Boolean {
        return try {
            val snapshot = usersCollection.document(uid).get().await()
            snapshot.exists()
        } catch (e: Exception) {
            false
        }
    }
}
