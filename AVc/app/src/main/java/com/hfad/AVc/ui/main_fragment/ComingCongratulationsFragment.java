package com.hfad.avc.ui.main_fragment;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hfad.avc.R;
import com.hfad.avc.ui.contact.ContactFragment;
import com.hfad.avc.ui.database.AppDatabase;
import com.hfad.avc.ui.database.Contact;
import com.hfad.avc.ui.namelist.ListAdapter;
import java.util.ArrayList;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class ComingCongratulationsFragment extends MvpAppCompatFragment implements IComingCongratulationsViewModel {

    @InjectPresenter
    ComingCongratulationsPresenter presenter;

    private String TAG = "AVc";
    public AppDatabase db;
    private RecyclerView recyclerView;
    private ArrayList<Contact> contactsList = new ArrayList<>();

    @ProvidePresenter
    ComingCongratulationsPresenter ProvidePresenterComingCongratulationsPresenter() {
        return new ComingCongratulationsPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_coming_congratulations, container, false);
        this.recyclerView = inflate.findViewById(R.id.list_favorites_coming);
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