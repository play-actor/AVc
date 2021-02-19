package com.hfad.avc.ui.namelist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ViewAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.hfad.avc.R;
import com.hfad.avc.ui.contact.ContactFragment;
import com.hfad.avc.ui.database.AppDatabase;
import com.hfad.avc.ui.database.Contact;
import java.util.ArrayList;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class NameListFragment extends MvpAppCompatFragment implements INameListViewModel {

    @InjectPresenter
    NameListPresenter presenter;

    private String TAG = "AVc";
    public AppDatabase db;
    private RecyclerView recyclerView;
    private ArrayList<Contact> contactsList = new ArrayList<>();
    private ViewAnimator viewAnimator;
    int id = 0;
    Button ShowButton;

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
        this.contactsList = listContactsList;
        // создаем адаптер
        ListAdapter adapter = new ListAdapter(contactsList);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        //Назначение слушателя для спискового представления
        adapter.setClick(id -> {
            ContactFragment contactFragment = ContactFragment.newInstance(id);
            getFragmentManager().beginTransaction()
                    .replace(R.id.root, contactFragment, "ContactFragment")
                    .addToBackStack("ContactFragment")
                    .commit();
        });


    }
}