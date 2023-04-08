package com.hfad.avc.ui.template

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.hfad.avc.R
import com.hfad.avc.data.model.Template
import com.hfad.avc.databinding.FragmentTemplateBinding
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class СhangeTemplateFragment : MvpAppCompatFragment(), IСhangeTemplateViewModel {
   @InjectPresenter
   lateinit var presenter: СhangeTemplatePresenter
   lateinit var toolbar: Toolbar
   lateinit var favoriteTemplate: MenuItem
   lateinit var changeTemplate: MenuItem
   lateinit var textTemplate: EditText
   lateinit var binding: FragmentTemplateBinding

   @ProvidePresenter
   fun ProvidePresenterContactPresenter(): СhangeTemplatePresenter {
      return СhangeTemplatePresenter(arguments)
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_template, container, false)
      val view: View = binding.root
      this.toolbar = view.findViewById(R.id.toolbar)
      this.toolbar.inflateMenu(R.menu.menu_contact)
      this.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
      this.favoriteTemplate = toolbar.menu.findItem(R.id.favoriteObject)
      this.changeTemplate = toolbar.menu.findItem(R.id.changeObject)
      this.textTemplate = binding.textTemplate
      this.favoriteTemplate.let { presenter.setFavoriteTemplate(it) }
      this.favoriteTemplate.setOnMenuItemClickListener {
         presenter.setOnClickFavoriteTemplate()
         presenter.setFavoriteTemplate(favoriteTemplate)
         presenter.updateTemplateDB()
         false
      }
      this.changeTemplate.setOnMenuItemClickListener {
         val isCursorVisible = textTemplate.isCursorVisible
         if (isCursorVisible) {
            presenter.updateTemplateDB()
         }
         textTemplate.isCursorVisible = !isCursorVisible
         textTemplate.isFocusable = !isCursorVisible
         textTemplate.isClickable = !isCursorVisible
         false
      }
      return view
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setHasOptionsMenu(true)
   }

   override fun setData(template: Template?) {
      this.binding.templateDetail = template
      this.binding.presenterTemplate = presenter
   }

   override fun onInsertDB(newId: String?) {
      this.presenter.setNewTemplateText()
   }

   override fun sendOkUsers() {
      Snackbar.make(requireView(), "Сохранено", BaseTransientBottomBar.LENGTH_LONG).show()
   }
}