package com.hfad.AVc.ui.contact;

import com.hfad.AVc.ui.database.Contact;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IContactViewModel extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void openCalendar();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void setData(Contact contact);

}
