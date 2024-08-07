package com.lastaurus.automatic_congratulations.ui.contact.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lastaurus.automatic_congratulations.R
import com.vomisareg.ui.compose.ContactPhone
import com.vomisareg.ui.compose.Details
import com.vomisareg.ui.compose.DetailsContent
import com.vomisareg.ui.compose.ToolbarWithLoadLine
import com.vomisareg.ui.theme.AppTheme
import com.vomisareg.ui.theme.fontGerberReg

@Preview
@Composable
fun ContactScreenPreview() {
    AppTheme(false) {
        ContactScreen(
            employees = remember { Details.EmployDetailsList },
            onBackCLick = {},
            onEndButtonClick = {},
        )
    }
}

@Preview
@Composable
fun ContactScreenDarkPreview() {
    AppTheme(true) {
        ContactScreen(
            employees = remember { Details.EmployDetailsList },
            onBackCLick = {},
            onEndButtonClick = {},
        )
    }
}

@Composable
fun ContactScreen(
    title: String = "",
    name: String = "",
    employees: List<ContactPhone> = listOf(),
    onBackCLick: () -> Unit,
    onEndButtonClick: (Boolean) -> Unit,
    imageFilled: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(0.dp)

    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ToolbarWithLoadLine(
                title = title,
                showBackButton = true,
                endButton = true,
                onBackClick = { onBackCLick.invoke() },
                onEndButtonClick = {
                    onEndButtonClick.invoke(it)
                },
                imageFilled = imageFilled
            )
            Image(
                painterResource(R.drawable.no_foto),
                contentDescription = "Ok",
                contentScale = ContentScale.FillWidth,
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = name,
                        fontSize = 24.sp,
                        lineHeight = 32.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight(400),
                        fontFamily = fontGerberReg,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .padding(horizontal = 16.dp)
                            .background(MaterialTheme.colorScheme.onBackground)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Контактная информация",
                        fontSize = 14.sp,
                        lineHeight = 32.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight(400),
                        fontFamily = fontGerberReg,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 24.dp)
                    )
                    DetailsContent(remember { employees })
                }
            }
        }
    }
}