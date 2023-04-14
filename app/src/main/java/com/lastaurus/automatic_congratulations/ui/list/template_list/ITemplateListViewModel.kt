package com.lastaurus.automatic_congratulations.ui.list.template_list

import com.lastaurus.automatic_congratulations.data.model.Template
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ITemplateListViewModel : MvpView {
   fun setData(templatesList: List<Template>)
}