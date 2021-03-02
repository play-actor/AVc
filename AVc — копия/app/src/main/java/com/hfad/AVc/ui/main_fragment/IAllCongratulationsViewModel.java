package com.hfad.AVc.ui.main_fragment;

import com.hfad.AVc.ui.database.Contact;

import java.util.ArrayList;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IAllCongratulationsViewModel extends MvpView {

    void setData(ArrayList<Contact> contactsList);
}