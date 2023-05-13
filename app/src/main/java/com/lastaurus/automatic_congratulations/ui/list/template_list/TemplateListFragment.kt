package com.lastaurus.automatic_congratulations.ui.list.template_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButtonToggleGroup
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.Util.SpaceItemDecoration
import com.lastaurus.automatic_congratulations.data.model.Template
import com.lastaurus.automatic_congratulations.ui.list.adapters.TemplateListAdapter
import moxy.MvpAppCompatFragment


class TemplateListFragment : MvpAppCompatFragment() {

   private var recyclerView: RecyclerView? = null
   private var viewModel: TemplateListViewModel? = null
   private var addTemplate: View? = null
   private var filterTemplate: MaterialButtonToggleGroup? = null
   private var adapter: TemplateListAdapter? = null
   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      val view: View = inflater.inflate(
         R.layout.fragment_template_of_congratulations_text_list,
         container,
         false
      )
      with(view) {
         recyclerView = this.findViewById(R.id.templateList)
         addTemplate = this.findViewById(R.id.addTemplate)
         filterTemplate = this.findViewById(R.id.filterTemplate)
      }
      adapter = TemplateListAdapter()
      init(viewModel?.getTemplateList())
      this.addTemplate?.setOnClickListener {
         viewModel?.openNewTemplate()
      }
      filterTemplate?.addOnButtonCheckedListener { group, checkedId, isChecked ->
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
      filterTemplate?.check(R.id.buttonFilterAll)
      recyclerView?.addItemDecoration(SpaceItemDecoration())
      adapter?.setClick(object : TemplateListAdapter.Click {
         override fun click(id: Int) {
            viewModel?.openTemplate(id)
         }
      })
      return view
   }

   fun init(templateList: LiveData<List<Template>>?) {
      templateList?.observe(viewLifecycleOwner) { templates ->
         adapter?.setList(templates)
         recyclerView?.adapter = adapter
      }
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      this.viewModel = ViewModelProvider(this)[TemplateListViewModel::class.java]
   }
}