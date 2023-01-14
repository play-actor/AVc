package com.hfad.avc.ui.list.all_congratulations_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.hfad.avc.R;
import com.hfad.avc.data.model.Contact;
import com.hfad.avc.ui.contact.СhangeContactFragment;
import com.hfad.avc.ui.list.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class AllCongratulationsListFragment extends MvpAppCompatFragment implements IAllCongratulationsListViewModel {

   @InjectPresenter
   AllCongratulationsListPresenter presenter1;

   private String TAG = "AllCongratulationsListFragment";
   private RecyclerView recyclerView1;
   private List<Contact> contactsList = new ArrayList<>();

   @ProvidePresenter
   AllCongratulationsListPresenter ProvidePresenterAllCongratulationsPresenter() {
      return new AllCongratulationsListPresenter();
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View inflate1 = inflater.inflate(R.layout.fragment_all_congratulations, container, false);
      this.recyclerView1 = inflate1.findViewById(R.id.list_favorites_all);
      return inflate1;
   }

   @Override
   public void setData(List<Contact> listContactsList) {
      this.contactsList = listContactsList;
      // создаем адаптер
      ListAdapter adapter1 = new ListAdapter(contactsList);
      // устанавливаем для списка адаптер
      recyclerView1.setAdapter(adapter1);
      adapter1.setClick(id -> {
         СhangeContactFragment contactFragment = СhangeContactFragment.newInstance(id);
         getActivity().getSupportFragmentManager().beginTransaction()
               .replace(R.id.root, contactFragment, "ContactFragment")
               .addToBackStack("ContactFragment")
               .commitAllowingStateLoss();
      });
   }
}