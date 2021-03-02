package com.hfad.AVc.ui.main_fragment;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hfad.AVc.R;
import com.hfad.AVc.ui.contact.ContactFragment;
import com.hfad.AVc.ui.database.AppDatabase;
import com.hfad.AVc.ui.database.Contact;
import com.hfad.AVc.ui.namelist.ListAdapter;
import java.util.ArrayList;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class AllCongratulationsFragment extends MvpAppCompatFragment implements IAllCongratulationsViewModel {

    @InjectPresenter
    AllCongratulationsPresenter presenter;

    private String TAG = "AVc";
    public AppDatabase db;
    private RecyclerView recyclerView;
    private ArrayList<Contact> contactsList = new ArrayList<>();

    @ProvidePresenter
    AllCongratulationsPresenter ProvidePresenterAllCongratulationsPresenter() {
        return new AllCongratulationsPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_all_congratulations, container, false);
        this.recyclerView = inflate.findViewById(R.id.list_favorites);
        return inflate;
    }

    @Override
    public void setData(ArrayList<Contact> listContactsList) {
        this.contactsList = listContactsList;
        // создаем адаптер
        ListAdapter adapter = new ListAdapter(contactsList);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        adapter.setClick(id -> {
            ContactFragment contactFragment = ContactFragment.newInstance(id);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.root, contactFragment, "ContactFragment")
                    .addToBackStack("ContactFragment")
                    .commitAllowingStateLoss();
        });
    }
}