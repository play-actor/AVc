package com.hfad.avc.ui.namelist;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.hfad.avc.BackButtonListener;
import com.hfad.avc.BaseFragment;
import com.hfad.avc.R;
import com.hfad.avc.ui.database.AppDatabase;
import com.hfad.avc.ui.database.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class NameListFragment extends BaseFragment implements INameListViewModel, BackButtonListener {

    @InjectPresenter
    NameListPresenter presenter;
    private String TAG = "AVc";
    public AppDatabase db;
    private RecyclerView recyclerView;
    public ArrayList<Contact> contactsList = new ArrayList<>();
    int id = 0;

    @ProvidePresenter
    NameListPresenter ProvidePresenterNameListPresenter() {
        return new NameListPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_name_list, container, false);
        this.recyclerView = inflate.findViewById(R.id.list);
        recyclerView.addItemDecoration(new SpaceItemDecoration());
        return inflate;
    }

    private class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            // вычисление пикселей по DP. Здесь отступ будет *8dp*
            int margin = 88;
            int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin, view.getResources().getDisplayMetrics());
            if (parent.getChildLayoutPosition(view) == (parent.getAdapter().getItemCount() - 1)) {
                outRect.top = 0;
                outRect.bottom = space;
            }
        }
    }

    @Override
    public void setData(ArrayList<Contact> listContactsList) {
        Comparator<Contact> comparator = (o1, o2) ->
             o1.getName().compareTo(o2.getName());

        if (listContactsList != null) {
            this.contactsList = listContactsList;

            Collections.sort(contactsList, comparator);
            for (int i = 0; i < listContactsList.size(); i++) {
                Log.i("AVc ArrayList", contactsList.get(i).getId() + " " + contactsList.get(i).getName() + " " + contactsList.get(i).getPhone());

            }
            ListAdapter adapter = new ListAdapter(contactsList);
            recyclerView.setAdapter(adapter);
            adapter.setClick(id -> {
                this.presenter.openContact(id);
            });
        }

    }

    @Override
    public boolean onBackPressed() {
        this.presenter.back();
        return true;
    }
}