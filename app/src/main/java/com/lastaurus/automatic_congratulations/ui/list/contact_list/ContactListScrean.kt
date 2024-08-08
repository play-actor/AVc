package com.lastaurus.automatic_congratulations.ui.list.contact_list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lastaurus.automatic_congratulations.data.model.Contact
import com.lastaurus.automatic_congratulations.ui.contact.compose.ContactScreen
import com.vomisareg.ui.compose.ContactItem
import com.vomisareg.ui.compose.ContactList
import com.vomisareg.ui.compose.ContactListData
import com.vomisareg.ui.theme.AppTheme

@Composable
fun ContactListScrean(
    listViewModel: ContactListViewModel = viewModel(),

    ) {
    val contactList by listViewModel.getContactList().observeAsState()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "contactList") {
        composable("contactList") {
            ContactListContent(prepareContactList(contactList)) {
                navController.navigate("contact/$it")
            }
        }
        composable(
            route = "contact/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val userId = it.arguments?.getInt("id")
            ContactScreen(id = userId, onBackCLick = { navController.navigate("contactList") })
        }
    }
}

fun prepareContactList(listInput: List<Contact>?): List<ContactListData> {
    val list = mutableListOf<ContactListData>()
    listInput?.forEachIndexed { index, element ->
        list.add(
            index,
            ContactListData(
                id = element.id,
                name = element.name,
                uriThumbnail = element.uriThumbnail,
                uriFull = element.uriFull,
                phone = element.phoneList[0],
                favorite = element.favorite
            )
        )
    }
    return list
}

@Composable
fun ContactListContent(employees: List<ContactListData>, click: (Int) -> Unit) {

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            employees.count()
        ) {
            key(it) {
                ContactItem(emp = employees[it]) {
                    click.invoke(employees[it].id)
                }
            }
        }
    }
}

@Preview
@Composable
fun ContactListScreenPreview() {
    val employees = remember { ContactList.DetailsList }
    AppTheme(false) {
        ContactListContent(employees) {}
    }
}

@Preview
@Composable
fun ContactListScreenDarkPreview() {
    val employees = remember { ContactList.DetailsList }
    AppTheme(true) {
        ContactListContent(employees) {}
    }
}