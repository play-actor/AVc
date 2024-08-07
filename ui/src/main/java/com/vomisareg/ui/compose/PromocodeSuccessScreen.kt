package com.vomisareg.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vomisareg.ui.theme.IconPack
import com.vomisareg.ui.theme.iconpack.Calendar
import com.vomisareg.ui.theme.reGray

@Preview
@Composable
fun PromocodeSuccessScreenPreview() {
    PromocodeSuccessScreen(
        onCLickPromocodes = {},
        onCLickMain = {}
    )
}

@Composable
fun PromocodeSuccessScreen(onCLickMain: () -> Unit, onCLickPromocodes: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(reGray)
            .padding(0.dp)

    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, end = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = IconPack.Calendar,
                    contentDescription = "Назад",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(32.dp))
                TextTitle(text = "R.string.promocode_sucess_title")
            }
        }

        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column {
                ButtonTransparent(
                    text = "R.string.promocode_success_back_to_main",
                    onClick = {
                        onCLickMain()
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                ButtonPurpleLight(
                    text = "R.string.promocode_success_back_to_promocodes)",
                    onClick = {
                        onCLickPromocodes()
                    }
                )
            }
        }
    }
}