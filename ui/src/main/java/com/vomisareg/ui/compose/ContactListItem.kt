package com.vomisareg.ui.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.vomisareg.ui.R
import com.vomisareg.ui.theme.AppTheme

data class ContactListData(
    var id: Int = Int.MIN_VALUE,
    val name: String,
    val phone: String,
    var uriThumbnail: String = "",
    var uriFull: String = "",
    var favorite: Boolean = false
)

@Composable
fun ContactItem(emp: ContactListData, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        onClick = { onClick() },
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))

    ) {

        Row(modifier = Modifier.padding(20.dp)) {
            AsyncImage(
                model = emp.uriThumbnail,
                contentDescription = "avatar",
                placeholder = painterResource(R.drawable.no_foto),
                error = painterResource(R.drawable.no_foto),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.background, CircleShape)
            )
//            Image(
//                painter = painterResource(R.drawable.no_foto),
//                contentDescription = "avatar",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(50.dp)
//                    .clip(CircleShape)
//                    .border(1.dp, MaterialTheme.colorScheme.background, CircleShape)
//            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp, 0.dp, 0.dp, 0.dp),
                Arrangement.Center
            ) {
                Text(
                    text = emp.name,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 18.sp,
                    )
                )
                Text(
                    modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp),
                    text = emp.phone,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 16.sp
                    )
                )
            }

        }
    }
}

@Preview
@Composable
fun ContactListScreenCardPreview() {
    AppTheme(false) {
        ContactItem(
            ContactListData(
                name = "Will Biliinson",
                phone = "8(999)686-80-36"
            )
        ) {}
    }
}

@Preview
@Composable
fun ContactListScreenCardFDarkPreview() {
    AppTheme(true) {
        ContactItem(
            ContactListData(
                name = "Will Biliinson",
                phone = "8(999)686-80-36"
            )
        ) {}
    }
}

object ContactList {

    val DetailsList = listOf(
        ContactListData(
            name = "Will Biliinson",
            phone = "8(999)686-80-36"
        ),
        ContactListData(
            name = "Bill Wiliinson",
            phone = "8(999)686-80-37"
        ),
        ContactListData(
            name = "Gill Jiliinson",
            phone = "8(999)686-80-38"
        ),
    )
}