package com.sktech.messmate.ui.theme

import androidx.compose.ui.graphics.Color

// ============================================================
// MessMate — "Fresh & Reliable" Design System (Stitch Tokens)
// ============================================================

// Primary Palette — Energetic Orange
val Primary = Color(0xFFA14000)
val PrimaryContainer = Color(0xFFF26D21)
val PrimaryFixed = Color(0xFFFFDBCC)
val PrimaryFixedDim = Color(0xFFFFB694)
val OnPrimary = Color(0xFFFFFFFF)
val OnPrimaryContainer = Color(0xFF521D00)
val OnPrimaryFixed = Color(0xFF351000)
val OnPrimaryFixedVariant = Color(0xFF7B2F00)

// Secondary Palette — Vibrant Green
val Secondary = Color(0xFF006E25)
val SecondaryContainer = Color(0xFF82FC8E)
val SecondaryFixed = Color(0xFF82FC8E)
val SecondaryFixedDim = Color(0xFF66DF75)
val OnSecondary = Color(0xFFFFFFFF)
val OnSecondaryContainer = Color(0xFF007528)
val OnSecondaryFixed = Color(0xFF002106)
val OnSecondaryFixedVariant = Color(0xFF00531A)

// Tertiary Palette — Cool Blue-Grey
val Tertiary = Color(0xFF526070)
val TertiaryContainer = Color(0xFF8896A7)
val TertiaryFixed = Color(0xFFD5E4F7)
val TertiaryFixedDim = Color(0xFFB9C8DA)
val OnTertiary = Color(0xFFFFFFFF)
val OnTertiaryContainer = Color(0xFF212F3D)
val OnTertiaryFixed = Color(0xFF0E1D2A)
val OnTertiaryFixedVariant = Color(0xFF3A4857)

// Error Palette
val Error = Color(0xFFBA1A1A)
val ErrorContainer = Color(0xFFFFDAD6)
val OnError = Color(0xFFFFFFFF)
val OnErrorContainer = Color(0xFF93000A)

// Surface & Background — Light
val Surface = Color(0xFFF8F9FF)
val SurfaceBright = Color(0xFFF8F9FF)
val SurfaceDim = Color(0xFFCBDBF5)
val SurfaceContainerLowest = Color(0xFFFFFFFF)
val SurfaceContainerLow = Color(0xFFEFF4FF)
val SurfaceContainer = Color(0xFFE5EEFF)
val SurfaceContainerHigh = Color(0xFFDCE9FF)
val SurfaceContainerHighest = Color(0xFFD3E4FE)
val SurfaceVariant = Color(0xFFD3E4FE)
val SurfaceTint = Color(0xFFA14000)

val Background = Color(0xFFF8F9FF)
val OnSurface = Color(0xFF0B1C30)
val OnSurfaceVariant = Color(0xFF584238)
val OnBackground = Color(0xFF0B1C30)

// Outline
val Outline = Color(0xFF8C7166)
val OutlineVariant = Color(0xFFE0C0B2)

// Inverse
val InverseSurface = Color(0xFF213145)
val InverseOnSurface = Color(0xFFEAF1FF)
val InversePrimary = Color(0xFFFFB694)

// ============================================================
// Semantic / Convenience Aliases
// ============================================================

// Attendance / Meal Status
val MealPresent = Color(0xFF006E25)    // Secondary green
val MealAbsent = Color(0xFFBA1A1A)     // Error red
val MealPending = Color(0xFFA14000)    // Primary orange
val MealSkipped = Color(0xFF8C7166)    // Outline grey

// Card tints for meal sections
val CardMealBreakfast = Color(0xFFFFDBCC) // PrimaryFixed
val CardMealLunch = Color(0xFF82FC8E)     // SecondaryContainer
val CardMealDinner = Color(0xFFD5E4F7)    // TertiaryFixed
val CardMealExtra = Color(0xFFEFF4FF)     // SurfaceContainerLow
