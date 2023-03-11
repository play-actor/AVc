package com.hfad.avc.ui.list.template_list;

import com.hfad.avc.data.model.Template;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ITemplateListViewModel extends MvpView {

   void setData(List<Template> templatesList);
}