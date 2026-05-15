package com.sktech.messmate.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Extended colors not covered by Material3 ColorScheme
data class ExtendedColors(
    val surfaceContainerLowest: Color,
    val surfaceContainerLow: Color,
    val surfaceContainer: Color,
    val surfaceContainerHigh: Color,
    val surfaceContainerHighest: Color,
    val primaryContainer: Color,
    val secondaryContainer: Color,
    val primaryFixed: Color,
    val primaryFixedDim: Color,
    val secondaryFixed: Color,
    val secondaryFixedDim: Color,
    val outlineVariant: Color,
    val mealPresent: Color,
    val mealAbsent: Color,
    val mealPending: Color,
    val mealSkipped: Color
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        surfaceContainerLowest = Color.White,
        surfaceContainerLow = Color.White,
        surfaceContainer = Color.White,
        surfaceContainerHigh = Color.White,
        surfaceContainerHighest = Color.White,
        primaryContainer = Color.White,
        secondaryContainer = Color.White,
        primaryFixed = Color.White,
        primaryFixedDim = Color.White,
        secondaryFixed = Color.White,
        secondaryFixedDim = Color.White,
        outlineVariant = Color.White,
        mealPresent = Color.Green,
        mealAbsent = Color.Red,
        mealPending = Color.Yellow,
        mealSkipped = Color.Gray
    )
}

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = Tertiary,
    onTertiary = OnTertiary,
    tertiaryContainer = TertiaryContainer,
    onTertiaryContainer = OnTertiaryContainer,
    error = Error,
    onError = OnError,
    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    background = Background,
    onBackground = OnBackground,
    outline = Outline,
    inverseSurface = InverseSurface,
    inverseOnSurface = InverseOnSurface,
    inversePrimary = InversePrimary,
    surfaceTint = SurfaceTint
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryFixedDim,
    onPrimary = OnPrimaryFixed,
    primaryContainer = Primary,
    onPrimaryContainer = PrimaryFixed,
    secondary = SecondaryFixedDim,
    onSecondary = OnSecondaryFixed,
    secondaryContainer = Secondary,
    onSecondaryContainer = SecondaryFixed,
    tertiary = TertiaryFixedDim,
    onTertiary = OnTertiaryFixed,
    tertiaryContainer = Tertiary,
    onTertiaryContainer = TertiaryFixed,
    error = Error,
    onError = OnError,
    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer,
    surface = InverseSurface,
    onSurface = InverseOnSurface,
    surfaceVariant = Color(0xFF2A3A4E),
    onSurfaceVariant = TertiaryFixedDim,
    background = Color(0xFF0B1C30),
    onBackground = InverseOnSurface,
    outline = OutlineVariant,
    inverseSurface = Surface,
    inverseOnSurface = OnSurface,
    inversePrimary = Primary,
    surfaceTint = PrimaryFixedDim
)

private val LightExtendedColors = ExtendedColors(
    surfaceContainerLowest = SurfaceContainerLowest,
    surfaceContainerLow = SurfaceContainerLow,
    surfaceContainer = SurfaceContainer,
    surfaceContainerHigh = SurfaceContainerHigh,
    surfaceContainerHighest = SurfaceContainerHighest,
    primaryContainer = PrimaryContainer,
    secondaryContainer = SecondaryContainer,
    primaryFixed = PrimaryFixed,
    primaryFixedDim = PrimaryFixedDim,
    secondaryFixed = SecondaryFixed,
    secondaryFixedDim = SecondaryFixedDim,
    outlineVariant = OutlineVariant,
    mealPresent = MealPresent,
    mealAbsent = MealAbsent,
    mealPending = MealPending,
    mealSkipped = MealSkipped
)

private val DarkExtendedColors = ExtendedColors(
    surfaceContainerLowest = Color(0xFF0D1F33),
    surfaceContainerLow = Color(0xFF132840),
    surfaceContainer = Color(0xFF1A304D),
    surfaceContainerHigh = Color(0xFF213A5A),
    surfaceContainerHighest = Color(0xFF284367),
    primaryContainer = Color(0xFF7B2F00),
    secondaryContainer = Color(0xFF00531A),
    primaryFixed = PrimaryFixed,
    primaryFixedDim = PrimaryFixedDim,
    secondaryFixed = SecondaryFixed,
    secondaryFixedDim = SecondaryFixedDim,
    outlineVariant = Color(0xFF3A4857),
    mealPresent = SecondaryFixedDim,
    mealAbsent = Error,
    mealPending = PrimaryFixedDim,
    mealSkipped = Color(0xFF6E7A87)
)

@Composable
fun MessMateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val extendedColors = if (darkTheme) DarkExtendedColors else LightExtendedColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = android.graphics.Color.TRANSPARENT
            window.navigationBarColor = android.graphics.Color.TRANSPARENT
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = MessMateTypography,
            content = content
        )
    }
}

// Extension for easy access
object MessMateThemeExt {
    val colors: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current
}
