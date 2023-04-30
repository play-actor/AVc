package com.lastaurus.automatic_congratulations.ui.list.template_list

import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lastaurus.automatic_congratulations.BaseFragment
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.data.model.Template
import com.lastaurus.automatic_congratulations.ui.list.TemplateListAdapter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


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
      recyclerView?.addItemDecoration(SpaceItemDecoration())
      return inflate
   }

   private inner class SpaceItemDecoration : RecyclerView.ItemDecoration() {
      override fun getItemOffsets(
         outRect: Rect,
         view: View,
         parent: RecyclerView,
         state: RecyclerView.State,
      ) {
         val margin = 88
         val space = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            margin.toFloat(),
            view.resources.displayMetrics
         ).toInt()
         if (parent.getChildLayoutPosition(view) == (parent.adapter?.itemCount?.minus(1))) {
            outRect.top = 0
            outRect.bottom = space
         }
      }
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      this.viewModel = ViewModelProvider(this)[TemplateListViewModel::class.java]
   }
}