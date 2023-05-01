package com.lastaurus.automatic_congratulations.ui.list.template_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.lastaurus.automatic_congratulations.Util.SpaceItemDecoration
import com.lastaurus.automatic_congratulations.data.model.Template
import com.lastaurus.automatic_congratulations.ui.list.TemplateListAdapter
import moxy.MvpAppCompatFragment


class TemplateListFragment : MvpAppCompatFragment() {

   private var recyclerView: RecyclerView? = null
   private lateinit var viewModel: TemplateListViewModel
   lateinit var addTemplate: View
   lateinit var filter: View
   lateinit var adapter: TemplateListAdapter
   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      val view: View = inflater.inflate(
         com.lastaurus.automatic_congratulations.R.layout.fragment_template_of_congratulations_text_list,
         container,
         false
      )
      recyclerView = view.findViewById(com.lastaurus.automatic_congratulations.R.id.templateList)

      this.addTemplate = view.findViewById(com.lastaurus.automatic_congratulations.R.id.addTemplate)
      this.filter = view.findViewById(com.lastaurus.automatic_congratulations.R.id.filter)
      adapter = TemplateListAdapter()
      init(viewModel.getTemplateList())
      this.addTemplate.setOnClickListener {
         viewModel.openNewTemplate()
      }
      this.filter.setOnClickListener {
         init(viewModel.getTemplateListFavorite())
      }
      return view
   }

   fun init(templateList: List<Template>) {
      adapter.setList(templateList)
      recyclerView?.adapter = adapter
      recyclerView?.addItemDecoration(SpaceItemDecoration())
      adapter.setClick(object : TemplateListAdapter.Click {
         override fun click(id: Int) {
            viewModel.openTemplate(id)
         }
      })
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      this.viewModel = ViewModelProvider(this)[TemplateListViewModel::class.java]
   }
}