package com.hfad.avc.ui.contact;

import com.hfad.avc.ui.database.Contact;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IContactViewModel extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void setData(Contact contact);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void setWorker(String name, String phoneNumber, String dateCon, String TextTemplate);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void setDateNew();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void setTimeNew();
}
