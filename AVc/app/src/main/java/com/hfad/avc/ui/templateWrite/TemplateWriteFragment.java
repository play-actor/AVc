package com.hfad.avc.ui.templateWrite;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hfad.avc.R;
import com.hfad.avc.databinding.FragmentTemplateWriteBinding;
import com.hfad.avc.ui.database.Template;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;


public class TemplateWriteFragment extends MvpAppCompatFragment implements ITemplateWriteViewModel {

    @InjectPresenter
    TemplateWritePresenter presenter;

    private String TAG = "AVc";
    private FragmentTemplateWriteBinding binding;
    public CoordinatorLayout coordLayout;


    public static TemplateWriteFragment newInstance(Integer templateFavorite) {
        TemplateWriteFragment templateWriteFragment = new TemplateWriteFragment();
        Bundle args = new Bundle();
        args.putInt("template_Id", templateFavorite);
        templateWriteFragment.setArguments(args);
        return templateWriteFragment;
    }

    @ProvidePresenter
    TemplateWritePresenter ProvidePresenterContactPresenter() {
        return new TemplateWritePresenter(getArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_template_write, container, false);
        return binding.getRoot();
    }

    @Override
    public void setData(Template template) {
        if (template == null) {
            Template templateNew = new Template();
            templateNew.setTextTemplate("С праздником!");
            Log.i("AVc", "template = " + templateNew.getTextTemplate());
            template = templateNew;
        }
        this.binding.setTemplateDetail(template);
        /*EditText timeView = (EditText) findViewById(R.id.name_template);*/

        this.binding.setPresenterTemplate(this.presenter);
    }

    @Override
    public void onInsertDB(String newId) {
        String s = this.binding.getTemplateDetail().getTextTemplate();
        this.presenter.setNewTemplateText(s);
    }

    @Override
    public void sendOkUsers() {
        coordLayout = getActivity().findViewById(R.id.mainFragApp);
        Snackbar.make(coordLayout, "Сохранено", BaseTransientBottomBar.LENGTH_LONG).show();
    }

}