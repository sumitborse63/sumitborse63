package com.sktech.messmate.ui.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sktech.messmate.data.model.MealRecord
import com.sktech.messmate.data.model.MealStats
import com.sktech.messmate.data.repository.AuthRepository
import com.sktech.messmate.data.repository.MealRepository
import com.sktech.messmate.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

data class MealTrackingState(
    val selectedDate: LocalDate = LocalDate.now(),
    val todayMeal: MealRecord? = null,
    val monthlyMeals: List<MealRecord> = emptyList(),
    val mealStats: MealStats = MealStats(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val mealRepository: MealRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MealTrackingState())
    val state: StateFlow<MealTrackingState> = _state.asStateFlow()

    private val userId: String get() = authRepository.currentUser?.uid ?: ""

    init {
        loadTodayMeal()
        loadMonthlyStats()
    }

    fun selectDate(date: LocalDate) {
        _state.update { it.copy(selectedDate = date) }
        loadMealForDate(date)
    }

    private fun loadTodayMeal() {
        loadMealForDate(LocalDate.now())
    }

    private fun loadMealForDate(date: LocalDate) {
        viewModelScope.launch {
            val dateStr = DateUtils.formatDate(date)
            mealRepository.getMealRecord(userId, dateStr).collect { record ->
                _state.update { it.copy(todayMeal = record) }
            }
        }
    }

    fun loadMonthlyStats(year: Int? = null, month: Int? = null) {
        val now = YearMonth.now()
        val y = year ?: now.year
        val m = month ?: now.monthValue

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val mealsResult = mealRepository.getMonthlyMeals(userId, y, m)
            mealsResult.onSuccess { meals ->
                _state.update { it.copy(monthlyMeals = meals) }
            }

            val statsResult = mealRepository.getMealStats(userId, y, m)
            statsResult.onSuccess { stats ->
                _state.update { it.copy(mealStats = stats, isLoading = false) }
            }
            statsResult.onFailure { e ->
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun toggleMeal(mealType: String, value: Boolean) {
        viewModelScope.launch {
            val dateStr = DateUtils.formatDate(_state.value.selectedDate)
            mealRepository.markMeal(userId, dateStr, mealType, value)
            // Refresh stats after marking
            loadMonthlyStats()
        }
    }

    fun getMealDataForCalendar(): Map<String, Int> {
        return _state.value.monthlyMeals.associate { record ->
            record.date to record.totalMeals
        }
    }
}
