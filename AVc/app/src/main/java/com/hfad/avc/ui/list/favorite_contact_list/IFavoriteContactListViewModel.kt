package com.lastaurus.avc.ui.list.favorite_contact_list

import com.lastaurus.avc.data.model.Contact
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IFavoriteContactListViewModel : MvpView {
   fun setData(contactsList: List<Contact>)
}