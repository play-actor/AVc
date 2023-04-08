package com.hfad.avc.ui.list.all_congratulations_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.avc.ui.list.CongratulationsListAdapter
import com.hfad.avc.ui.list.CongratulationsListAdapter.Click
import com.hfad.avc.ui.list.contact.СhangeContactFragment
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class AllCongratulationsListFragment : MvpAppCompatFragment(),
   IAllCongratulationsListViewModel {
   @InjectPresenter
   lateinit var presenter1: AllCongratulationsListPresenter
   private var recyclerView: RecyclerView? = null

   @ProvidePresenter
   fun ProvidePresenterAllCongratulationsPresenter(): AllCongratulationsListPresenter {
      return AllCongratulationsListPresenter()
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      val view: View =
         inflater.inflate(com.hfad.avc.R.layout.fragment_all_congratulations, container, false)
      recyclerView = view.findViewById(com.hfad.avc.R.id.list_congratulations_all)
      return view
   }

   override fun setData() {
      // создаем адаптер
      val recyclerListAdapter = CongratulationsListAdapter()
      // устанавливаем для списка адаптер
      recyclerView!!.adapter = recyclerListAdapter
      recyclerListAdapter.setClick(object : Click {
         override fun click(id: Int) {
            val contactFragment = СhangeContactFragment.newInstance(id)
            activity?.supportFragmentManager?.beginTransaction()
               ?.replace(com.hfad.avc.R.id.root, contactFragment, "ContactFragment")
               ?.addToBackStack("ContactFragment")
               ?.commitAllowingStateLoss()
         }
      })
   }
}