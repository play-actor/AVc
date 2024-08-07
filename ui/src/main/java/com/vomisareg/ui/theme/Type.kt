package com.vomisareg.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.vomisareg.ui.R

//val fontGerberReg = FontFamily(Font(R.font.gerbera_regular))
val fontInterReg = FontFamily(Font(R.font.inter_regular))

val fontGerberReg = FontFamily(
    Font(R.font.gerbera_regular, FontWeight.Light),
    Font(R.font.gerbera_regular, FontWeight.Medium),
    Font(R.font.gerbera_regular, FontWeight.Normal),
    Font(R.font.gerbera_regular, FontWeight.Bold)
)

private val defaultTypography = Typography()

val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = fontGerberReg),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = fontGerberReg),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = fontGerberReg),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = fontGerberReg),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = fontGerberReg),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = fontGerberReg),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = fontGerberReg),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = fontGerberReg),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = fontGerberReg),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = fontGerberReg),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = fontGerberReg),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = fontGerberReg),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = fontGerberReg),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = fontGerberReg),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = fontGerberReg)
)