package com.vomisareg.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vomisareg.ui.theme.AppTheme
import com.vomisareg.ui.theme.IconPack
import com.vomisareg.ui.theme.iconpack.Phonepickup

data class ContactPhone(
    val phone: String,
    val type: String = "Мобильный",
)

@Composable
fun EmployeeCard(emp: ContactPhone) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
//        elevation = 2.dp,
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))

    ) {

        Row(modifier = Modifier.padding(20.dp)) {
            Icon(
                imageVector = IconPack.Phonepickup,
                contentDescription = "Назад",
                tint = MaterialTheme.colorScheme.background,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp, 0.dp, 0.dp, 0.dp),
                Arrangement.Center
            ) {
                Text(
                    text = emp.phone,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 18.sp,
                    )
                )
                Text(
                    modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp),
                    text = emp.type,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 16.sp
                    )
                )
            }

        }
    }
}

@Composable
fun DetailsContent(employees: List<ContactPhone>) {

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            employees.count()
        ) {
            EmployeeCard(emp = employees[it])
        }
    }
}

@Preview
@Composable
fun ContactScreenCardPreview() {
    AppTheme(false) {
        EmployeeCard(
            ContactPhone(
                phone = "8(999)686-80-36"
            )
        )
    }
}

@Preview
@Composable
fun ContactScreenCardFDarkPreview() {
    AppTheme(true) {
        EmployeeCard(
            ContactPhone(
                phone = "8(999)686-80-36"
            )
        )
    }
}

@Preview
@Composable
fun ContactScreenPreview() {
    val employees = remember { Details.EmployDetailsList }
    AppTheme(false) {
        DetailsContent(employees)
    }
}

@Preview
@Composable
fun ContactScreenDarkPreview() {
    val employees = remember { Details.EmployDetailsList }
    AppTheme(true) {
        DetailsContent(employees)
    }
}

object Details {

    val EmployDetailsList = listOf(
        ContactPhone(
            phone = "8(999)686-80-36"
        ),
        ContactPhone(
            phone = "8(999)686-80-37"
        ),
        ContactPhone(
            phone = "8(999)686-80-38"
        ),
        ContactPhone(
            phone = "8(999)686-80-39"
        ),
        ContactPhone(
            phone = "8(999)686-80-40"
        ),
        ContactPhone(
            phone = "8(999)686-80-41"
        )
    )
}