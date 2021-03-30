package com.hfad.avc.ui.template;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
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
        View inflate = inflater.inflate(R.layout.fragment_template, container, false);
        this.recyclerView = inflate.findViewById(R.id.TemplateOnList);
        recyclerView.addItemDecoration(new SpaceItemDecoration());
        return inflate;
    }

    private class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            // вычисление пикселей по DP. Здесь отступ будет *8dp*
            int margin = 88;
            int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin, view.getResources().getDisplayMetrics());
            if(parent.getChildLayoutPosition(view) == (parent.getAdapter().getItemCount()-1)){
                outRect.top = 0;
                outRect.bottom = space;
            }
        }
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
        this.presenter.back();
        return true;
    }
}