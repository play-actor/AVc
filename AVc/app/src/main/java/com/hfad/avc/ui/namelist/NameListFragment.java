package com.hfad.avc.ui.namelist;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.avc.Applications;
import com.hfad.avc.BackButtonListener;
import com.hfad.avc.BaseFragment;
import com.hfad.avc.R;
import com.hfad.avc.interactor.LoadDBInteractor;
import com.hfad.avc.ui.database.AppDatabase;
import com.hfad.avc.ui.database.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class NameListFragment extends BaseFragment implements INameListViewModel, BackButtonListener {

   @InjectPresenter
   NameListPresenter presenter;
   private String TAG = "AVc";
   public AppDatabase db;
   private RecyclerView recyclerView;
   public ArrayList<Contact> contactsList = new ArrayList<>();
   private Toolbar mActionBarToolbar;
   LoadDBInteractor interactorLoad;
   private MenuItem sortAbMenuItem;
   private MenuItem searchMenuItem;
   private SearchView mSearchView;


   @ProvidePresenter
   NameListPresenter ProvidePresenterNameListPresenter() {
      return new NameListPresenter();
   }


   @Override
   public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
      this.mActionBarToolbar.inflateMenu(R.menu.menu_contact);
      menu = mActionBarToolbar.getMenu();
      super.onCreateOptionsMenu(menu, inflater);

      searchMenuItem = menu.findItem(R.id.action_search);
      mSearchView = (SearchView) searchMenuItem.getActionView();

      SearchManager searchmanager = (SearchManager) requireActivity().getSystemService(Context.SEARCH_SERVICE);
      this.mSearchView.setSearchableInfo(searchmanager.getSearchableInfo(requireActivity().getComponentName()));
      this.mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
         @Override
         public boolean onQueryTextSubmit(String query) {
            return false;
         }

         @Override
         public boolean onQueryTextChange(String searchText) {
            Log.i(TAG, "Контакт: " + searchText);
            interactorLoad.Filt(searchText)
                  .subscribeOn(Schedulers.computation())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(list ->
                              setData((ArrayList<Contact>) list, 0),
                        Throwable::printStackTrace);

            return false;
         }
      });
      sortAbMenuItem = menu.findItem(R.id.action_sort);
      sortAbMenuItem.setOnMenuItemClickListener(item -> {
         if (sortAbMenuItem.isChecked()) {
            presenter.reMove(0);
            sortAbMenuItem.setChecked(false);
         } else {
            presenter.reMove(1);
            sortAbMenuItem.setChecked(true);
         }
         return true;
      });
   }

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setHasOptionsMenu(true);
      this.interactorLoad = Applications.INSTANCE.getHelperInteractors().getContactInteractor();
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View inflate = inflater.inflate(R.layout.fragment_name_list, container, false);
      this.recyclerView = inflate.findViewById(R.id.list);
      recyclerView.addItemDecoration(new SpaceItemDecoration());
      mActionBarToolbar = inflate.findViewById(R.id.toolbar);
      ((AppCompatActivity) requireActivity()).setSupportActionBar(mActionBarToolbar);
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
   public void setData(ArrayList<Contact> listContactsList, int typesort) {
      if (listContactsList != null) {
         this.contactsList = listContactsList;
         sortList(typesort);
         ListAdapter adapter = new ListAdapter(contactsList);
         recyclerView.setAdapter(adapter);
         adapter.setClick(id -> {
            this.presenter.openContact(id);
         });
      }
   }

   public void sortList(int typesort) {
      Comparator<Contact> comparator;
      if (typesort == 0) {
         comparator = (o1, o2) ->
               o1.getName().compareTo(o2.getName());
      } else {
         comparator = (o2, o1) ->
               o1.getName().compareTo(o2.getName());
      }
      Collections.sort(contactsList, comparator);
   }

   @Override
   public boolean onBackPressed() {
      this.presenter.back();
      return true;
   }
}