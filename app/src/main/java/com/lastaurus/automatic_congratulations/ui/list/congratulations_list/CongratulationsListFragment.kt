package com.lastaurus.automatic_congratulations.ui.list.congratulations_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.lastaurus.automatic_congratulations.BaseFragment
import com.lastaurus.automatic_congratulations.bus.BusEvent
import com.lastaurus.automatic_congratulations.bus.EventHandler
import com.lastaurus.automatic_congratulations.bus.TypeObject
import com.lastaurus.automatic_congratulations.dagger.ComponentManager.Companion.instance
import com.lastaurus.automatic_congratulations.data.model.Congratulation
import com.lastaurus.automatic_congratulations.databinding.FragmentCongratulationsListBinding
import com.lastaurus.automatic_congratulations.ui.list.adapters.CongratulationsListAdapter
import com.lastaurus.automatic_congratulations.util.SpaceItemDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CongratulationsListFragment @Inject constructor() : BaseFragment() {

   private var adapter: CongratulationsListAdapter? = null
   private var viewModel: CongratulationsListViewModel? = null
   private var _binding: FragmentCongratulationsListBinding? = null
   private val binding get() = _binding!!

   @Inject
   lateinit var eventHandler: EventHandler

   init {
      instance.appComponent.inject(this)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      this.viewModel = ViewModelProvider(this)[CongratulationsListViewModel::class.java]
      subscribeOnEventBus()
   }

   private fun subscribeOnEventBus() {
      eventHandler.subscribeEvent { busEvent ->
         (busEvent as? BusEvent.Sort)?.apply {
            if (this.type == TypeObject.CONGRATULATIONS) {
               init(viewModel?.getCongratulationsList(this.aZ))
            }
         }
         (busEvent as? BusEvent.SearchByText)?.apply {
            if (this.type == TypeObject.CONGRATULATIONS) {
               this.text?.apply { init(viewModel?.getCongratulationsListByPeaceNameInContact(this)) }
               if (this.text == null) {
                  init(viewModel?.getCongratulationsList())
               }
            }
         }
         false
      }
   }


   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      _binding = FragmentCongratulationsListBinding.inflate(inflater, container, false)
      val view = binding.root
      binding.apply {
         adapter = CongratulationsListAdapter()
         init(viewModel?.getCongratulationsList())
         addCongratulationsToList.setOnClickListener {
            viewModel?.openNewContact()
         }
         listCongratulationsAll.addItemDecoration(SpaceItemDecoration())
         adapter?.setClick(object : CongratulationsListAdapter.Click {
            override fun click(id: Int) {
               viewModel?.openCongratulation(id)
            }
         })
      }
      return view
   }

   fun init(list: LiveData<List<Congratulation>>?) {
      lifecycleScope.launch {
         withContext(Dispatchers.Main) {
            list?.observe(viewLifecycleOwner) { congratulations ->
               adapter?.setList(congratulations)
               binding.listCongratulationsAll.adapter = adapter
            }
         }
      }
   }
}