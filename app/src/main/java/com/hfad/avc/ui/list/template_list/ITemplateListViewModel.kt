package com.hfad.avc.ui.list.template_list

import com.hfad.avc.data.model.Template
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ITemplateListViewModel : MvpView {
   fun setData(templatesList: List<Template>)
}