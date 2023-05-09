package com.lastaurus.automatic_congratulations.ui.list.congratulations_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.lastaurus.automatic_congratulations.BaseFragment
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.Util.SpaceItemDecoration
import com.lastaurus.automatic_congratulations.dagger.ComponentManager.Companion.instance
import com.lastaurus.automatic_congratulations.data.model.Congratulation
import com.lastaurus.automatic_congratulations.ui.list.adapters.CongratulationsListAdapter

class CongratulationsListFragment : BaseFragment() {
   private var recyclerView: RecyclerView? = null
   private var adapter: CongratulationsListAdapter? = null
   private var viewModel: CongratulationsListViewModel? = null
   private var addContact: View? = null
   private var filterContact: View? = null

   init {
      instance.appComponent.inject(this)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      this.viewModel = ViewModelProvider(this)[CongratulationsListViewModel::class.java]
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      val view: View = inflater.inflate(R.layout.fragment_congratulations_list, container, false)
      with(view) {
         recyclerView = this.findViewById(R.id.list_congratulations_all)
         addContact = this.findViewById(R.id.addCongratulationsToList)
      }
      adapter = CongratulationsListAdapter()
      init(viewModel?.getContactList())
      this.addContact?.setOnClickListener {
         viewModel?.openNewContact()
      }
      this.filterContact?.setOnClickListener {
         init(viewModel?.getActiveCongratulationsList())
      }
      recyclerView?.addItemDecoration(SpaceItemDecoration())
      adapter?.setClick(object : CongratulationsListAdapter.Click {
         override fun click(id: Int) {
            viewModel?.openCongratulation(id)
         }
      })
      return view
   }

   fun init(list: LiveData<List<Congratulation>>?) {
      list?.observe(viewLifecycleOwner) { congratulations ->
         adapter?.setList(congratulations?.sortedBy { it.getId() })
         recyclerView?.adapter = adapter
      }
   }
}