package com.hfad.avc.ui.list.favorite_contact_list

import com.hfad.avc.data.model.Contact
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IFavoriteContactListViewModel : MvpView {
   fun setData(contactList: List<Contact>)
}