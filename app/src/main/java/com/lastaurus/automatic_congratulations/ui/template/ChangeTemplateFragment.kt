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
import com.lastaurus.automatic_congratulations.bus.RxBus
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import moxy.MvpAppCompatFragment
import javax.inject.Inject


class ChangeTemplateFragment : MvpAppCompatFragment() {
   lateinit var toolbar: Toolbar
   lateinit var favoriteTemplate: MenuItem
   lateinit var changeTemplate: View
   lateinit var textTemplate: EditText
   private lateinit var viewModel: TemplateViewModel

   @Inject
   lateinit var rxBus: RxBus

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      val view: View =
         inflater.inflate(R.layout.fragment_template_of_congratulations_text, container, false)
      this.toolbar = view.findViewById(R.id.toolbar)
      this.toolbar.inflateMenu(R.menu.menu_contact)
      this.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
      this.favoriteTemplate = toolbar.menu.findItem(R.id.favoriteObject)
      this.changeTemplate = view.findViewById(R.id.changeTemplate)
      this.textTemplate = view.findViewById(R.id.text_template)

      this.textTemplate.setText(this.viewModel.getText())
      this.favoriteTemplate.let {
         setFavoriteTemplate(it, viewModel.getFavorite())
      }
      this.favoriteTemplate.setOnMenuItemClickListener {
         with(viewModel) {
            setFavoriteTemplate(favoriteTemplate, changeFavorite())
            viewModel.update()
         }
         false
      }
      this.changeTemplate.setOnClickListener {
         viewModel.saveText(textTemplate.text.toString())
         rxBus.send("Сохранено")
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