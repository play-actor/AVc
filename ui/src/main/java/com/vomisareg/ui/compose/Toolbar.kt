package com.vomisareg.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vomisareg.ui.theme.AppTheme
import com.vomisareg.ui.theme.IconPack
import com.vomisareg.ui.theme.iconpack.Arrowleft
import com.vomisareg.ui.theme.iconpack.Star
import com.vomisareg.ui.theme.iconpack.Starfilled
import com.vomisareg.ui.theme.outlineDark
import com.vomisareg.ui.theme.primaryLight

@Composable
fun ToolbarWithLoadLine(
    title: String,
    showBackButton: Boolean = true,
    endButton: Boolean = false,
    stateSuccessLineCount: Int = 1,
    lineCount: Int = 0,
    onBackClick: () -> Unit,
    onEndButtonClick: (Boolean) -> Unit,
    imageFilled: Boolean = false
) {
    val filled = remember(imageFilled) {
        mutableStateOf(imageFilled)
    }
    Column(
        modifier = Modifier
            .defaultMinSize(minHeight = 54.dp)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp)
            )
            .fillMaxWidth()
            .wrapContentHeight(CenterVertically)
            .padding(top = 4.dp)
    ) {
        Box(modifier = Modifier.padding(10.dp)) {
            if (showBackButton) {
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = { onBackClick() }
                ) {
                    Icon(
                        imageVector = IconPack.Arrowleft,
                        contentDescription = "Назад",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            if (endButton) {

                IconButton(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.TopEnd),
                    onClick = {
                        filled.value = filled.value.not()
                        onEndButtonClick(filled.value)
                    }
                ) {
                    Icon(
                        imageVector = if (filled.value) {
                            IconPack.Starfilled
                        } else {
                            IconPack.Star
                        },
                        contentDescription = "Назад",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            Text(
                text = title.trim(),
                fontSize = 17.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight(400),
                color = MaterialTheme.colorScheme.onBackground,
                letterSpacing = TextUnit(-0.02f, TextUnitType.Sp),
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (lineCount > 0) {
            Row(
                verticalAlignment = CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, bottom = 8.dp)
                    .height(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(20.dp)
                        .background(
                            color = if (stateSuccessLineCount > 0) primaryLight else outlineDark,
                            shape = RoundedCornerShape(16.dp)
                        )
                )
                for (i in 2..lineCount) {
                    Spacer(modifier = Modifier.weight(0.05f))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(20.dp)
                            .background(
                                color = if (i <= stateSuccessLineCount) primaryLight else outlineDark,
                                shape = RoundedCornerShape(16.dp)
                            )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ToolbarWithLoadLineWithEndPreview() {
    ToolbarWithLoadLine(
        title = "Заголовок",
        showBackButton = true,
        endButton = true,
        onBackClick = {},
        onEndButtonClick = {},
        imageFilled = true
    )
}

@Preview
@Composable
fun ToolbarWithLoadLineWithEndPreviewDark() {
    AppTheme(true) {
        ToolbarWithLoadLine(
            title = "Заголовок",
            showBackButton = true,
            endButton = true,
            onBackClick = {},
            onEndButtonClick = {},
            imageFilled = true
        )
    }
}

@Preview
@Composable
fun ToolbarWithLoadLineWithEndPreviewStar() {
    ToolbarWithLoadLine(
        title = "Заголовок",
        showBackButton = true,
        endButton = true,
        onBackClick = {},
        onEndButtonClick = {}
    )
}

@Preview
@Composable
fun ToolbarWithLoadLineWithEndPreviewDarkStar() {
    AppTheme(true) {
        ToolbarWithLoadLine(title = "Заголовок",
            showBackButton = true,
            endButton = true,
            onBackClick = {},
            onEndButtonClick = {}
        )
    }
}

@Preview
@Composable
fun ToolbarWithLoadLinePreview() {
    ToolbarWithLoadLine(
        title = "Заголовок",
        showBackButton = true,
        onBackClick = {},
        onEndButtonClick = {}
    )
}

@Preview
@Composable
fun ToolbarWithLoadLinePreviewDark() {
    AppTheme(true) {
        ToolbarWithLoadLine(
            title = "Заголовок",
            showBackButton = true,
            onBackClick = {},
            onEndButtonClick = {}
        )
    }
}

@Preview
@Composable
fun ToolbarWithLoadLineExpandedWithEndPreviewStar() {
    ToolbarWithLoadLine(
        title = "Заголовок",
        showBackButton = true,
        endButton = true,
        lineCount = 2,
        stateSuccessLineCount = 1,
        onBackClick = {},
        onEndButtonClick = {},
        imageFilled = true
    )
}

@Preview
@Composable
fun ToolbarWithLoadLineExpandedWithEndPreviewDarkStar() {
    AppTheme(true) {
        ToolbarWithLoadLine(
            title = "Заголовок",
            showBackButton = true,
            endButton = true,
            lineCount = 2,
            stateSuccessLineCount = 1,
            onBackClick = {},
            onEndButtonClick = {},
            imageFilled = true
        )
    }
}

@Preview
@Composable
fun ToolbarWithLoadLineExpandedWithEndPreview() {
    ToolbarWithLoadLine(
        title = "Заголовок",
        showBackButton = true,
        endButton = true,
        lineCount = 2,
        stateSuccessLineCount = 1,
        onBackClick = {},
        onEndButtonClick = {}
    )
}

@Preview
@Composable
fun ToolbarWithLoadLineExpandedWithEndPreviewDark() {
    AppTheme(true) {
        ToolbarWithLoadLine(
            title = "Заголовок",
            showBackButton = true,
            endButton = true,
            lineCount = 2,
            stateSuccessLineCount = 1,
            onBackClick = {},
            onEndButtonClick = {}
        )
    }
}

@Preview
@Composable
fun ToolbarWithLoadLineExpandedPreview() {
    ToolbarWithLoadLine(
        title = "Заголовок",
        showBackButton = true,
        lineCount = 2,
        stateSuccessLineCount = 1,
        onBackClick = {},
        onEndButtonClick = {}
    )
}

@Preview
@Composable
fun ToolbarWithLoadLineExpandedPreviewDark() {
    AppTheme(true) {
        ToolbarWithLoadLine(
            title = "Заголовок",
            showBackButton = true,
            lineCount = 2,
            stateSuccessLineCount = 1,
            onBackClick = {},
            onEndButtonClick = {}
        )
    }
}

