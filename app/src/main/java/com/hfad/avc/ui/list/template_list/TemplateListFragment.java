package com.hfad.avc.ui.list.template_list;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.hfad.avc.BaseFragment;
import com.hfad.avc.R;
import com.hfad.avc.data.model.Template;
import com.hfad.avc.ui.list.TemplateListAdapter;

import java.util.ArrayList;
import java.util.List;

import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class TemplateListFragment extends BaseFragment implements ITemplateListViewModel {

   @InjectPresenter
   TemplateListPresenter presenter;
   private String TAG = "AVc";
   private RecyclerView recyclerView;
   private List<Template> templatesList = new ArrayList<>();
   int id = 0;

   @ProvidePresenter
   TemplateListPresenter ProvidePresenterTemplatePresenter() {
      return new TemplateListPresenter();
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
         int margin = 88;
         int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin, view.getResources().getDisplayMetrics());
         if (parent.getChildLayoutPosition(view) == (parent.getAdapter().getItemCount() - 1)) {
            outRect.top = 0;
            outRect.bottom = space;
         }
      }
   }

   @Override
   public void setData(List<Template> templateList) {
      if (templateList != null) {
         this.templatesList = templateList;
         TemplateListAdapter adapter = new TemplateListAdapter(templatesList);
         recyclerView.setAdapter(adapter);
         adapter.setClick(id -> {
            this.presenter.openTemplate(id);
         });
      }
   }

}