package com.lastaurus.automatic_congratulations.ui.list.template_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.lastaurus.automatic_congratulations.BaseFragment
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.bus.BusEvent
import com.lastaurus.automatic_congratulations.bus.EventHandler
import com.lastaurus.automatic_congratulations.bus.TypeObject
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.data.model.Template
import com.lastaurus.automatic_congratulations.databinding.FragmentTemplateOfCongratulationsTextListBinding
import com.lastaurus.automatic_congratulations.ui.list.adapters.TemplateListAdapter
import com.lastaurus.automatic_congratulations.util.SpaceItemDecoration
import com.lastaurus.automatic_congratulations.util.isNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class TemplateListFragment @Inject constructor() : BaseFragment() {

   private var viewModel: TemplateListViewModel? = null
   private var adapter: TemplateListAdapter? = null
   private var _binding: FragmentTemplateOfCongratulationsTextListBinding? = null
   private val binding get() = _binding!!

   @Inject
   lateinit var eventHandler: EventHandler

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      this.viewModel = ViewModelProvider(this)[TemplateListViewModel::class.java]
      subscribeOnEventBus()
   }

   private fun subscribeOnEventBus() {
      eventHandler.subscribeEvent { busEvent ->
         (busEvent as? BusEvent.Sort)?.apply {
            if (this.type == TypeObject.TEMPLATE) {
               init(viewModel?.getTemplateList(this.aZ))
            }
         }
         (busEvent as? BusEvent.SearchByText)?.apply {
            if (this.type == TypeObject.TEMPLATE) {
               this.text?.apply { init(viewModel?.getTemplateListByPeaceText(this)) }
               if (this.text.isNull()) {
                  init(viewModel?.getTemplateList())
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
      _binding =
         FragmentTemplateOfCongratulationsTextListBinding.inflate(inflater, container, false)
      val view = binding.root
      binding.apply {
         adapter = TemplateListAdapter()
         this.addTemplate.setOnClickListener {
            viewModel?.openNewTemplate()
         }
         filterTemplate.addOnButtonCheckedListener { _, checkedId, isChecked ->
            when (checkedId) {
               R.id.buttonFilterAll -> {
                  if (!isChecked) return@addOnButtonCheckedListener
                  init(viewModel?.getTemplateList())
               }

               R.id.buttonFilterFavorite -> {
                  if (!isChecked) return@addOnButtonCheckedListener
                  init(viewModel?.getTemplateListFavorite())
               }

               else -> return@addOnButtonCheckedListener
            }
         }
         filterTemplate.check(R.id.buttonFilterAll)
         templateList.addItemDecoration(SpaceItemDecoration())
         adapter?.setClick(object : TemplateListAdapter.Click {
            override fun click(id: Int) {
               viewModel?.openTemplate(id)
            }
         })
      }
      return view
   }

   fun init(templateList: LiveData<List<Template>>?) {
      lifecycleScope.launch {
         withContext(Dispatchers.Main) {
            templateList?.observe(viewLifecycleOwner) { templates ->
               adapter?.setList(templates)
               binding.templateList.adapter = adapter
            }
         }
      }
   }
}