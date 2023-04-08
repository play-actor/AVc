package com.hfad.avc.ui.list.contact_list

import com.hfad.avc.data.model.Contact
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IContactListViewModel : MvpView {
   fun setData(contactsList: List<Contact>, typeSort: Int)
}