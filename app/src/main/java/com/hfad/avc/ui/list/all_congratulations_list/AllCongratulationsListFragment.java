package com.hfad.avc.ui.list.all_congratulations_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.hfad.avc.R;
import com.hfad.avc.data.model.Contact;
import com.hfad.avc.ui.contact.СhangeContactFragment;
import com.hfad.avc.ui.list.ContactListAdapter;

import java.util.ArrayList;
import java.util.List;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class AllCongratulationsListFragment extends MvpAppCompatFragment implements IAllCongratulationsListViewModel {

   @InjectPresenter
   AllCongratulationsListPresenter presenter1;

   private RecyclerView recyclerView;
   private List<Contact> contactsList = new ArrayList<>();

   @ProvidePresenter
   AllCongratulationsListPresenter ProvidePresenterAllCongratulationsPresenter() {
      return new AllCongratulationsListPresenter();
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View inflate1 = inflater.inflate(R.layout.fragment_all_congratulations, container, false);
      this.recyclerView = inflate1.findViewById(R.id.list_congratulations_all);
      return inflate1;
   }

   @Override
   public void setData(List<Contact> listContactsList) {
      this.contactsList = listContactsList;
      // создаем адаптер
      ContactListAdapter adapter1 = new ContactListAdapter(contactsList);
      // устанавливаем для списка адаптер
      recyclerView.setAdapter(adapter1);
      adapter1.setClick(id -> {
         СhangeContactFragment contactFragment = СhangeContactFragment.newInstance(id);
         getActivity().getSupportFragmentManager().beginTransaction()
               .replace(R.id.root, contactFragment, "ContactFragment")
               .addToBackStack("ContactFragment")
               .commitAllowingStateLoss();
      });
   }
}