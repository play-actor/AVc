package com.hfad.avc.ui.list.template_list

import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.avc.BaseFragment
import com.hfad.avc.R
import com.hfad.avc.data.model.Template
import com.hfad.avc.ui.list.TemplateListAdapter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class TemplateListFragment : BaseFragment(), ITemplateListViewModel {
   @InjectPresenter
   lateinit var presenter: TemplateListPresenter
   private val TAG = "AVc"
   private var recyclerView: RecyclerView? = null
   private var templatesList: List<Template> = ArrayList()

   @ProvidePresenter
   fun ProvidePresenterTemplatePresenter(): TemplateListPresenter {
      return TemplateListPresenter()
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      val inflate: View = inflater.inflate(R.layout.fragment_templatelist, container, false)
      recyclerView = inflate.findViewById(R.id.TemplateOnList)
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

   override fun setData(templatesList: List<Template>) {
      this.templatesList = templatesList
      val adapter = TemplateListAdapter(this.templatesList)
      recyclerView?.adapter = adapter
      adapter.setClick(object : TemplateListAdapter.Click {
         override fun click(id: Int) {
            presenter.openTemplate(id)
         }
      })
   }
}