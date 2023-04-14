package com.lastaurus.automatic_congratulations.ui.list.contact

import com.lastaurus.automatic_congratulations.data.model.Contact
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IСhangeContactViewModel : MvpView {
   @StateStrategyType(OneExecutionStateStrategy::class)
   fun setData(contact: Contact?)
   //   @StateStrategyType(OneExecutionStateStrategy.class)
   //   void setWorker(String id, String name, String phoneNumber, String dateCon, String TextTemplate);
   //   @StateStrategyType(OneExecutionStateStrategy.class)
   //   void setDateNew();
   //   @StateStrategyType(OneExecutionStateStrategy.class)
   //   void setTimeNew();
   //   @StateStrategyType(OneExecutionStateStrategy.class)
   //   void setWorker();
}