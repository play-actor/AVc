package com.hfad.avc.ui.template;

import com.hfad.avc.ui.database.Template;

import java.util.ArrayList;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ITemplateViewModel extends MvpView {

    void setData(ArrayList<Template> templatesList);
}