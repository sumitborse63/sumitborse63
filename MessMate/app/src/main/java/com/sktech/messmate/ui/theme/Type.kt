package com.sktech.messmate.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.sktech.messmate.R

// Google Fonts provider
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val PlusJakartaSans = GoogleFont("Plus Jakarta Sans")

val PlusJakartaSansFamily = FontFamily(
    Font(googleFont = PlusJakartaSans, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = PlusJakartaSans, fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = PlusJakartaSans, fontProvider = provider, weight = FontWeight.SemiBold),
    Font(googleFont = PlusJakartaSans, fontProvider = provider, weight = FontWeight.Bold),
    Font(googleFont = PlusJakartaSans, fontProvider = provider, weight = FontWeight.ExtraBold)
)

// Fallback: use default if Google Fonts provider is unavailable
val HeadingFont = PlusJakartaSansFamily
val BodyFont = PlusJakartaSansFamily

val MessMateTypography = Typography(
    // headline-lg: 30/38, Bold, -0.02em
    displayLarge = TextStyle(
        fontFamily = HeadingFont,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = HeadingFont,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp
    ),
    displaySmall = TextStyle(
        fontFamily = HeadingFont,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp
    ),
    // headline-lg: 30/38, Bold, -0.02em
    headlineLarge = TextStyle(
        fontFamily = HeadingFont,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        lineHeight = 38.sp,
        letterSpacing = (-0.02).sp
    ),
    // headline-md: 20/28, SemiBold
    headlineMedium = TextStyle(
        fontFamily = HeadingFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),
    // headline-lg-mobile: 24/32, Bold, -0.01em
    headlineSmall = TextStyle(
        fontFamily = HeadingFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = (-0.01).sp
    ),
    titleLarge = TextStyle(
        fontFamily = HeadingFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = BodyFont,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = BodyFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    // body-lg: 16/24, Normal
    bodyLarge = TextStyle(
        fontFamily = BodyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    // body-sm: 14/20, Normal
    bodyMedium = TextStyle(
        fontFamily = BodyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    // label-md: 12/16, Medium
    bodySmall = TextStyle(
        fontFamily = BodyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    // label-bold variant
    labelLarge = TextStyle(
        fontFamily = BodyFont,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    // label-md: 12/16, Medium
    labelMedium = TextStyle(
        fontFamily = BodyFont,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    // label-bold: 12/16, Bold
    labelSmall = TextStyle(
        fontFamily = BodyFont,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp
    )
)
