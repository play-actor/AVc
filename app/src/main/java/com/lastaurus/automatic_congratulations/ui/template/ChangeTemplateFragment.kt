package com.lastaurus.automatic_congratulations.ui.template

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.databinding.FragmentTemplateBinding
import moxy.MvpAppCompatFragment


class ChangeTemplateFragment : MvpAppCompatFragment() {
   lateinit var toolbar: Toolbar
   lateinit var favoriteTemplate: MenuItem
   lateinit var changeTemplate: MenuItem
   lateinit var textTemplate: EditText
   lateinit var binding: FragmentTemplateBinding
   private lateinit var viewModel: TemplateViewModel

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      this.binding = FragmentTemplateBinding.inflate(inflater, container, false)
      val view: View = binding.root
      this.toolbar = view.findViewById(R.id.toolbar)
      this.toolbar.inflateMenu(R.menu.menu_contact)
      this.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
      this.favoriteTemplate = toolbar.menu.findItem(R.id.favoriteObject)
      this.changeTemplate = toolbar.menu.findItem(R.id.changeObject)
      this.textTemplate = binding.textTemplate

      this.textTemplate.setText(this.viewModel.getText())
      this.favoriteTemplate.let { setFavoriteTemplate(it, viewModel.getFavorite()) }
      this.favoriteTemplate.setOnMenuItemClickListener {
         with(viewModel) {
            setFavoriteTemplate(favoriteTemplate, changeFavorite())
            viewModel.update()
         }
         false
      }
      this.changeTemplate.setOnMenuItemClickListener {
         if (textTemplate.isCursorVisible) {
            viewModel.saveText(textTemplate.text.toString())
         }
         with(textTemplate) {
            isCursorVisible = !isVisible
            isFocusable = !isVisible
            isClickable = !isVisible
         }
         false
      }
      return view
   }

   fun setFavoriteTemplate(favoriteTemplate: MenuItem, favorite: Boolean) {
      val iconId: Int =
         if (favorite) R.drawable.ic_baseline_star_favorite else R.drawable.ic_baseline_star_no_favorite
      favoriteTemplate.setIcon(iconId)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      this.viewModel = ViewModelProvider(this)[TemplateViewModel::class.java]
      viewModel.initTemplate(arguments?.getInt("template_Id", -1))
      setHasOptionsMenu(true)
   }

}