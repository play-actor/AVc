package com.lastaurus.automatic_congratulations.ui.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Data
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.ui.list.PhoneListAdapter

class ChangeContactFragment : Fragment() {

   var data: Data? = null
   private var iconContact: ImageView? = null
   private var mActionBarToolbar: Toolbar? = null
   private var favoriteContact: MenuItem? = null
   private var phonelist: RecyclerView? = null
   private var name: TextView? = null
   private lateinit var viewModel: ContactViewModel

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      val view: View = inflater.inflate(R.layout.fragment_contact, container, false)
      view.let {
         phonelist = it.findViewById(R.id.phonelist)
         iconContact = it.findViewById(R.id.iconContact)
         mActionBarToolbar = it.findViewById(R.id.toolbar)
         name = it.findViewById(R.id.name)
      }
      mActionBarToolbar?.let {
         it.inflateMenu(R.menu.menu_contact)
         it.setNavigationOnClickListener { requireActivity().onBackPressed() }
      }
      name?.text = viewModel.getName()
      favoriteContact = mActionBarToolbar?.menu?.findItem(R.id.favoriteObject)
      iconContact?.let { viewModel.setIconContact(it) }
      viewModel.getPhoneListFromContact().let {
         phonelist?.adapter = it?.let { it1 -> PhoneListAdapter(it1) }
      }
      this.favoriteContact?.let {
         viewModel.setFavoriteContact(it, viewModel.getFavorite())
      }
      this.favoriteContact?.setOnMenuItemClickListener {
         with(viewModel) {
            setFavoriteContact(favoriteContact!!, changeFavorite())
            update()
         }
         false
      }
      return view
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      this.viewModel = ViewModelProvider(this)[ContactViewModel::class.java]
      viewModel.initContact(arguments?.getInt("contactId", -1))
      setHasOptionsMenu(true)
   }
}