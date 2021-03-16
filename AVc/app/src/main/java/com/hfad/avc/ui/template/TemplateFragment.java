package com.hfad.avc.ui.template;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.hfad.avc.BackButtonListener;
import com.hfad.avc.BaseFragment;
import com.hfad.avc.R;
import com.hfad.avc.ui.database.AppDatabase;
import com.hfad.avc.ui.database.Template;

import java.util.ArrayList;

import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class TemplateFragment extends BaseFragment implements ITemplateViewModel, BackButtonListener {

    @InjectPresenter
    TemplatePresenter presenter;
    private String TAG = "AVc";
    public AppDatabase db;
    private RecyclerView recyclerView;
    private ArrayList<Template> templatesList = new ArrayList<>();
    int id = 0;

    @ProvidePresenter
    TemplatePresenter ProvidePresenterTemplatePresenter() {
        return new TemplatePresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_template, container, false);
        this.recyclerView = inflate.findViewById(R.id.TemplateOnList);
        return inflate;
    }

    @Override
    public void setData(ArrayList<Template> templateList) {
        if (templateList != null) {
            this.templatesList = templateList;
            TemplateAdapter adapter = new TemplateAdapter(templatesList);
            recyclerView.setAdapter(adapter);
            adapter.setClick(id -> {
                this.presenter.openTemplate(id);
            });
        }
    }


    @Override
    public boolean onBackPressed() {
       // this.presenter.back();
        return false;
    }
}