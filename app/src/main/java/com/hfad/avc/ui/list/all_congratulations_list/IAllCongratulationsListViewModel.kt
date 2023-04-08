package com.hfad.avc.ui.list.all_congratulations_list

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IAllCongratulationsListViewModel : MvpView {
   fun setData()
}