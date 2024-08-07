package com.vomisareg.ui.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vomisareg.ui.theme.colorPurple
import com.vomisareg.ui.theme.colorTextWhite
import com.vomisareg.ui.theme.fontInterReg
import com.vomisareg.ui.theme.grayDisabledText
import com.vomisareg.ui.theme.purpleText
import com.vomisareg.ui.theme.reGray
import com.vomisareg.ui.theme.reGrayButton
import com.vomisareg.ui.theme.reGreenAccent

@Composable
fun ButtonPurple(text: String, isEnabled: Boolean = true, onClick: () -> Unit) {
    val colors = ButtonDefaults.buttonColors(
        containerColor = colorPurple,
        contentColor = colorTextWhite,
        disabledContainerColor = reGray,
        disabledContentColor = grayDisabledText
    )
    CustomButton(
        text = text,
        buttonColors = colors,
        isEnabled = isEnabled,
        onClick = onClick
    )
}

@Composable
fun ButtonPurpleLight(text: String, isEnabled: Boolean = true, onClick: () -> Unit) {
    val colors = ButtonDefaults.buttonColors(
        containerColor = colorPurple,
        contentColor = colorTextWhite,
        disabledContainerColor = reGray,
        disabledContentColor = grayDisabledText
    )
    CustomButton(
        text = text,
        buttonColors = colors,
        isEnabled = isEnabled,
        onClick = onClick
    )
}

@Composable
fun ButtonGreen(text: String, isEnabled: Boolean = true, onClick: () -> Unit) {
    val colors = ButtonDefaults.buttonColors(
        containerColor = reGreenAccent,
        contentColor = purpleText,
        disabledContainerColor = reGray,
        disabledContentColor = grayDisabledText
    )
    CustomButton(
        text = text,
        buttonColors = colors,
        isEnabled = isEnabled,
        onClick = onClick
    )
}

@Composable
fun ButtonTransparent(text: String, isEnabled: Boolean = true, onClick: () -> Unit) {
    val colors = ButtonDefaults.outlinedButtonColors(
        containerColor = Color.Transparent,
        contentColor = purpleText,
        disabledContentColor = grayDisabledText
    )
    CustomButton(
        text = text,
        buttonColors = colors,
        isEnabled = isEnabled,
        onClick = onClick
    )
}

@Composable
fun ButtonGray(text: String, isEnabled: Boolean = true, onClick: () -> Unit) {
    val colors = ButtonDefaults.buttonColors(
        containerColor = reGrayButton,
        contentColor = purpleText,
        disabledContainerColor = reGray,
        disabledContentColor = grayDisabledText
    )
    CustomButton(
        text = text,
        buttonColors = colors,
        isEnabled = isEnabled,
        onClick = onClick
    )
}

@Composable
fun CustomButton(
    text: String,
    buttonColors: ButtonColors,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = buttonColors,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth(),
        enabled = isEnabled,
        elevation = null
    ) {
        Text(
            text = text,
            style = TextStyle(fontSize = 16.sp, fontFamily = fontInterReg),
            modifier = Modifier.padding(8.dp)
        )
    }
}