package com.lastaurus.automatic_congratulations.ui.list.all_congratulations_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.ui.list.CongratulationsListAdapter
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
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      val view: View =
         inflater.inflate(R.layout.fragment_all_congratulations, container, false)
      recyclerView = view.findViewById(R.id.list_congratulations_all)
      return view
   }

   override fun setData() {
      val recyclerListAdapter = CongratulationsListAdapter()
      recyclerView?.adapter = recyclerListAdapter
//      recyclerListAdapter.setClick(object : Click {
//         override fun click(id: Int) {
//            val contactFragment = СhangeContactFragment.newInstance(id)
//            activity?.supportFragmentManager?.beginTransaction()
//               ?.replace(R.id.root, contactFragment, "ContactFragment")
//               ?.addToBackStack("ContactFragment")
//               ?.commitAllowingStateLoss()
//         }
//      })
   }
}