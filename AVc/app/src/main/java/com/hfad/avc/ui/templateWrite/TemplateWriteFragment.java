package com.hfad.avc.ui.templateWrite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        this.binding.setTemplateDetail(template);
        this.binding.setPresenterTemplate(this.presenter);
    }

    @Override
    public void onInsertDB(String newId) {
        this.presenter.setNewTemplateText();
    }

    @Override
    public void sendOkUsers() {
          Snackbar.make(requireView(), "Сохранено", BaseTransientBottomBar.LENGTH_LONG).show();
    }

}