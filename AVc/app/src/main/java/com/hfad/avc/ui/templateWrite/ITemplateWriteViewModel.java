package com.hfad.avc.ui.templateWrite;

import com.hfad.avc.ui.database.Template;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ITemplateWriteViewModel extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void setData(Template template);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onInsertDB(String newId);
}
