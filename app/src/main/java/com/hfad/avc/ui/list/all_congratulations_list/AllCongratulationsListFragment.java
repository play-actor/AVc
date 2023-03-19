package com.hfad.avc.ui.list.all_congratulations_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.hfad.avc.R;
import com.hfad.avc.ui.contact.СhangeContactFragment;
import com.hfad.avc.ui.list.CongratulationsListAdapter;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class AllCongratulationsListFragment extends MvpAppCompatFragment implements IAllCongratulationsListViewModel {

   @InjectPresenter
   AllCongratulationsListPresenter presenter1;

   private RecyclerView recyclerView;

   @ProvidePresenter
   AllCongratulationsListPresenter ProvidePresenterAllCongratulationsPresenter() {
      return new AllCongratulationsListPresenter();
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_all_congratulations, container, false);
      this.recyclerView = view.findViewById(R.id.list_congratulations_all);
      return view;
   }

   @Override
   public void setData() {
      // создаем адаптер
      CongratulationsListAdapter recyclerListAdapter = new CongratulationsListAdapter();
      // устанавливаем для списка адаптер
      recyclerView.setAdapter(recyclerListAdapter);
      recyclerListAdapter.setClick(id -> {
         СhangeContactFragment contactFragment = СhangeContactFragment.newInstance(id);
         getActivity().getSupportFragmentManager().beginTransaction()
               .replace(R.id.root, contactFragment, "ContactFragment")
               .addToBackStack("ContactFragment")
               .commitAllowingStateLoss();
      });
   }
}