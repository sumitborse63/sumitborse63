package com.sktech.messmate.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

object DateUtils {
    private val isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    fun today(): String = LocalDate.now().format(isoFormatter)

    fun formatDate(date: LocalDate): String = date.format(isoFormatter)

    fun parseDate(dateStr: String): LocalDate = LocalDate.parse(dateStr, isoFormatter)

    fun getDisplayDate(dateStr: String): String {
        val date = parseDate(dateStr)
        return "${date.dayOfMonth} ${date.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())} ${date.year}"
    }

    fun getDayName(date: LocalDate): String {
        return date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    }

    fun getWeekDates(centerDate: LocalDate = LocalDate.now()): List<LocalDate> {
        val startOfWeek = centerDate.minusDays(centerDate.dayOfWeek.value.toLong() - 1)
        return (0L..6L).map { startOfWeek.plusDays(it) }
    }

    fun getMonthDates(year: Int, month: Int): List<LocalDate> {
        val yearMonth = java.time.YearMonth.of(year, month)
        return (1..yearMonth.lengthOfMonth()).map { yearMonth.atDay(it) }
    }
}

object Constants {
    const val COLLECTION_USERS = "users"
    const val COLLECTION_MEALS = "meals"
    const val COLLECTION_MESSES = "messes"
    const val COLLECTION_SUBSCRIPTIONS = "subscriptions"
    const val COLLECTION_PAYMENTS = "payments"
}
