package com.hfad.avc.ui.template;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hfad.avc.R;
import com.hfad.avc.data.model.Template;
import com.hfad.avc.databinding.FragmentTemplateBinding;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;


public class СhangeTemplateFragment extends MvpAppCompatFragment implements IСhangeTemplateViewModel {

   @InjectPresenter
   СhangeTemplatePresenter presenter;

   private Toolbar toolbar;
   private MenuItem favoriteTemplate;
   private MenuItem changeTemplate;
   private EditText textTemplate;
   private FragmentTemplateBinding binding;

   @ProvidePresenter
   СhangeTemplatePresenter ProvidePresenterContactPresenter() {
      return new СhangeTemplatePresenter(getArguments());
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      binding = DataBindingUtil.inflate(inflater, R.layout.fragment_template, container, false);
      final View view = binding.getRoot();
      this.toolbar = view.findViewById(R.id.toolbar);
      this.toolbar.inflateMenu(R.menu.menu_contact);
      this.toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
      this.favoriteTemplate = toolbar.getMenu().findItem(R.id.favoriteObject);
      this.changeTemplate = toolbar.getMenu().findItem(R.id.changeObject);
      this.textTemplate = binding.textTemplate;
      this.presenter.setFavoriteTemplate(favoriteTemplate);
      this.favoriteTemplate.setOnMenuItemClickListener(v -> {
         this.presenter.setOnClickFavoriteTemplate();
         this.presenter.setFavoriteTemplate(favoriteTemplate);
         this.presenter.updateTemplateDB();
         return false;
      });
      this.changeTemplate.setOnMenuItemClickListener(v -> {
         boolean isCursorVisible =this.textTemplate.isCursorVisible();
         if (isCursorVisible) {
            this.presenter.updateTemplateDB();
         }
         this.textTemplate.setCursorVisible(!isCursorVisible);
         this.textTemplate.setFocusable(!isCursorVisible);
         this.textTemplate.setClickable(!isCursorVisible);

         return false;
      });
      return view;
   }

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setHasOptionsMenu(true);
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