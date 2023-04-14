package com.lastaurus.automatic_congratulations.ui.template;

import com.lastaurus.automatic_congratulations.data.model.Template;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IСhangeTemplateViewModel extends MvpView {

   @StateStrategyType(OneExecutionStateStrategy.class)
   void setData(Template template);

   @StateStrategyType(OneExecutionStateStrategy.class)
   void onInsertDB(String newId);

   @StateStrategyType(OneExecutionStateStrategy.class)
   void sendOkUsers();
}
