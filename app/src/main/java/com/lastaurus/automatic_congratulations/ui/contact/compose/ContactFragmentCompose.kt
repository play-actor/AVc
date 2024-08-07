package com.lastaurus.automatic_congratulations.ui.contact.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lastaurus.automatic_congratulations.ui.contact.ContactViewModel
import com.vomisareg.ui.compose.ContactPhone
import com.vomisareg.ui.compose.Details
import com.vomisareg.ui.theme.AppTheme

class ContactFragmentCompose : Fragment() {

    private var viewModel: ContactViewModel? = null
    private var composeView: ComposeView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).also { composeView = it }

    override fun onDestroyView() {
        super.onDestroyView()
        composeView = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setContent()
    }

    private fun setContent() {
        composeView?.setContent {
            AppTheme {
                ContactScreen(
                    employees = remember { contactPhones() },
                    name = viewModel?.getName() ?: "",
                    onBackCLick = { requireActivity().onBackPressed() },
                    onEndButtonClick = {
                        viewModel?.apply {
                            setFavoriteContactCompose(it)
                            update()
                        }
                    },
                    imageFilled = viewModel?.getFavorite() ?: false
                )
            }
        }
    }

    private fun contactPhones(): MutableList<ContactPhone> {
        val list = mutableListOf<ContactPhone>()
        viewModel?.getPhoneListFromContact()?.forEachIndexed { index, element ->
            list.add(index, ContactPhone(element))
        }
        return list
    }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel = ViewModelProvider(this)[ContactViewModel::class.java]
        viewModel?.initContact(arguments?.getInt("contactId", -1))
    }
}