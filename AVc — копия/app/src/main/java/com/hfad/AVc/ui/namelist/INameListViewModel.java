package com.hfad.AVc.ui.namelist;

import com.hfad.AVc.ui.database.Contact;

import java.util.ArrayList;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface INameListViewModel extends MvpView {

    void setData(ArrayList<Contact> contactsList);
}
