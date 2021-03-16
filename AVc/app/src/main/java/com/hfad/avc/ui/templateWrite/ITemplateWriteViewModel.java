package com.hfad.avc.ui.templateWrite;

import com.hfad.avc.ui.database.Template;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ITemplateWriteViewModel {
    @StateStrategyType(OneExecutionStateStrategy.class)
    void setData(Template template);
}
