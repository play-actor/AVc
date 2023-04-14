package com.lastaurus.automatic_congratulations.ui.list.favorite_contact_list

import com.lastaurus.automatic_congratulations.data.model.Contact
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IFavoriteContactListViewModel : MvpView {
   fun setData(contactList: List<Contact>)
}