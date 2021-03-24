package com.hfad.avc.ui.namelist;

import android.os.Bundle;
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

import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class NameListFragment extends BaseFragment implements INameListViewModel, BackButtonListener {

    @InjectPresenter
    NameListPresenter presenter;
    private String TAG = "AVc";
    public AppDatabase db;
    private RecyclerView recyclerView;
    private ArrayList<Contact> contactsList = new ArrayList<>();
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
        return inflate;
    }

    @Override
    public void setData(ArrayList<Contact> listContactsList) {
        if (listContactsList != null) {
            this.contactsList = listContactsList;
            ListAdapter adapter = new ListAdapter(contactsList);
            recyclerView.setAdapter(adapter);
            adapter.setClick(id -> {
                this.presenter.openContact(id);
//                ContactFragment contactFragment = ContactFragment.newInstance(id);
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.root, contactFragment, "ContactFragment")
//                        .addToBackStack("ContactFragment")
//                        .commit();
            });
        }
    }

    @Override
    public boolean onBackPressed() {
        this.presenter.back();
        return false;
    }
}