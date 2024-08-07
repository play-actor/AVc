package com.vomisareg.ui.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.vomisareg.ui.theme.fontGerberReg
import com.vomisareg.ui.theme.fontInterReg
import com.vomisareg.ui.theme.purpleText

@Composable
fun TextTitle(text: String, textSize: Int = 28) {
    Text(
        text = text,
        fontSize = textSize.sp,
        lineHeight = 32.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight(400),
        fontFamily = fontGerberReg,
        color = purpleText
    )
}

@Composable
fun TextSubTitle(text: String, textSize: Int = 16) {
    Text(
        text = text,
        fontSize = textSize.sp,
        lineHeight = 20.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight(400),
        fontFamily = fontInterReg,
        color = purpleText
    )
}