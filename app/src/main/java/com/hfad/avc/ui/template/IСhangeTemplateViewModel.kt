package com.hfad.avc.ui.template

import com.hfad.avc.data.model.Template
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