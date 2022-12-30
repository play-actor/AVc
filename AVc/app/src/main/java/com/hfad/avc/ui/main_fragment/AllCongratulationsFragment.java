package com.hfad.avc.ui.main_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.hfad.avc.R;
import com.hfad.avc.ui.contact.ContactFragment;
import com.hfad.avc.ui.database.AppDatabase;
import com.hfad.avc.ui.database.Contact;
import com.hfad.avc.ui.namelist.ListAdapter;

import java.util.ArrayList;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class AllCongratulationsFragment extends MvpAppCompatFragment implements IAllCongratulationsViewModel {

   @InjectPresenter
   AllCongratulationsPresenter presenter1;

   private String TAG = "AVc";
   public AppDatabase db;
   private RecyclerView recyclerView1;
   private ArrayList<Contact> contactsList1 = new ArrayList<>();

   @ProvidePresenter
   AllCongratulationsPresenter ProvidePresenterAllCongratulationsPresenter() {
      return new AllCongratulationsPresenter();
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View inflate1 = inflater.inflate(R.layout.fragment_all_congratulations, container, false);
      this.recyclerView1 = inflate1.findViewById(R.id.list_favorites_all);
      return inflate1;
   }

   @Override
   public void setData(ArrayList<Contact> listContactsList1) {
      this.contactsList1 = listContactsList1;
      // создаем адаптер
      ListAdapter adapter1 = new ListAdapter(contactsList1);
      // устанавливаем для списка адаптер
      recyclerView1.setAdapter(adapter1);
      adapter1.setClick(id -> {
         ContactFragment contactFragment = ContactFragment.newInstance(id);
         getActivity().getSupportFragmentManager().beginTransaction()
               .replace(R.id.root, contactFragment, "ContactFragment")
               .addToBackStack("ContactFragment")
               .commitAllowingStateLoss();
      });
   }
}