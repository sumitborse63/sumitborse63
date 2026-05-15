package com.sktech.messmate.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.sktech.messmate.data.model.MealRecord
import com.sktech.messmate.data.model.MealStats
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val mealsCollection = firestore.collection("meals")

    private fun getDocId(userId: String, date: String) = "${userId}_${date}"

    suspend fun markMeal(
        userId: String,
        date: String,
        mealType: String,
        value: Boolean,
        messId: String? = null
    ): Result<Unit> {
        return try {
            val docId = getDocId(userId, date)
            val docRef = mealsCollection.document(docId)
            val snapshot = docRef.get().await()

            if (snapshot.exists()) {
                docRef.update(mealType, value).await()
            } else {
                val record = MealRecord(
                    recordId = docId,
                    userId = userId,
                    messId = messId,
                    date = date
                )
                val map = mutableMapOf<String, Any?>(
                    "recordId" to record.recordId,
                    "userId" to record.userId,
                    "messId" to record.messId,
                    "date" to record.date,
                    "breakfast" to (if (mealType == "breakfast") value else false),
                    "lunch" to (if (mealType == "lunch") value else false),
                    "dinner" to (if (mealType == "dinner") value else false),
                    "extraMeal" to (if (mealType == "extraMeal") value else false),
                    "markedBy" to "SELF",
                    "notes" to "",
                    "createdAt" to com.google.firebase.Timestamp.now()
                )
                docRef.set(map).await()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getMealRecord(userId: String, date: String): Flow<MealRecord?> = callbackFlow {
        val docId = getDocId(userId, date)
        val listener = mealsCollection.document(docId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(null)
                    return@addSnapshotListener
                }
                val record = snapshot?.toObject(MealRecord::class.java)
                trySend(record)
            }
        awaitClose { listener.remove() }
    }

    suspend fun getMonthlyMeals(
        userId: String,
        year: Int,
        month: Int
    ): Result<List<MealRecord>> {
        return try {
            val yearMonth = YearMonth.of(year, month)
            val startDate = yearMonth.atDay(1).format(DateTimeFormatter.ISO_LOCAL_DATE)
            val endDate = yearMonth.atEndOfMonth().format(DateTimeFormatter.ISO_LOCAL_DATE)

            val snapshot = mealsCollection
                .whereEqualTo("userId", userId)
                .whereGreaterThanOrEqualTo("date", startDate)
                .whereLessThanOrEqualTo("date", endDate)
                .get()
                .await()

            val records = snapshot.toObjects(MealRecord::class.java)
            Result.success(records)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMealStats(
        userId: String,
        year: Int,
        month: Int
    ): Result<MealStats> {
        return try {
            val result = getMonthlyMeals(userId, year, month)
            val records = result.getOrDefault(emptyList())

            val today = LocalDate.now()
            val yearMonth = YearMonth.of(year, month)
            val daysInMonth = if (yearMonth == YearMonth.from(today)) {
                today.dayOfMonth
            } else {
                yearMonth.lengthOfMonth()
            }

            val totalBreakfast = records.count { it.breakfast }
            val totalLunch = records.count { it.lunch }
            val totalDinner = records.count { it.dinner }
            val totalExtra = records.count { it.extraMeal }
            val totalMeals = totalBreakfast + totalLunch + totalDinner + totalExtra
            val totalPossible = daysInMonth * 3  // 3 main meals per day
            val totalSkipped = totalPossible - (totalBreakfast + totalLunch + totalDinner)

            // Calculate streak
            var streak = 0
            var checkDate = today
            while (true) {
                val dateStr = checkDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
                val dayRecord = records.find { it.date == dateStr }
                if (dayRecord != null && (dayRecord.breakfast || dayRecord.lunch || dayRecord.dinner)) {
                    streak++
                    checkDate = checkDate.minusDays(1)
                } else {
                    break
                }
            }

            Result.success(
                MealStats(
                    totalBreakfast = totalBreakfast,
                    totalLunch = totalLunch,
                    totalDinner = totalDinner,
                    totalExtraMeals = totalExtra,
                    totalMeals = totalMeals,
                    totalSkipped = totalSkipped.coerceAtLeast(0),
                    totalDays = daysInMonth,
                    totalCost = records.sumOf { it.totalCost },
                    currentStreak = streak
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateMealCost(
        userId: String,
        date: String,
        costField: String,
        cost: Double
    ): Result<Unit> {
        return try {
            val docId = getDocId(userId, date)
            mealsCollection.document(docId).update(costField, cost).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
