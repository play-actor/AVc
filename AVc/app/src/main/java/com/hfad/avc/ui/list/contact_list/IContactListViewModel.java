package com.lastaurus.automatic_congratulations.ui.list.contact_list;

import com.lastaurus.automatic_congratulations.data.model.Contact;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IContactListViewModel extends MvpView {

   void setData(List<Contact> contactsList, int typesort);
}
