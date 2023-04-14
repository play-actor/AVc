package com.lastaurus.automatic_congratulations.ui.template

import com.lastaurus.automatic_congratulations.data.model.Template
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType


@StateStrategyType(AddToEndSingleStrategy::class)
interface IСhangeTemplateViewModel : MvpView {
   @StateStrategyType(OneExecutionStateStrategy::class)
   fun setData(template: Template?)

   @StateStrategyType(OneExecutionStateStrategy::class)
   fun onInsertDB(newId: String?)

   @StateStrategyType(OneExecutionStateStrategy::class)
   fun sendOkUsers()
}