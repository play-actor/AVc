package com.lastaurus.automatic_congratulations.ui.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.databinding.FragmentContactBinding
import com.lastaurus.automatic_congratulations.ui.contact.dialog.FullScreenDialogExample
import com.lastaurus.automatic_congratulations.ui.list.adapters.PhoneListAdapter

class ContactFragment : Fragment() {

   private var viewModel: ContactViewModel? = null

   private var _binding: FragmentContactBinding? = null
   private val binding get() = _binding!!
   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      _binding = FragmentContactBinding.inflate(inflater, container, false)
      val view = binding.root
      binding.apply {
         toolbar.let {
            it.inflateMenu(R.menu.menu_contact)
            it.setNavigationOnClickListener { requireActivity().onBackPressed() }
         }
         name.text = viewModel?.getName()
         iconContact.let { viewModel?.setIconContact(it) }
         viewModel?.getPhoneListFromContact().let {
            phonelist.adapter = it?.let { it1 -> PhoneListAdapter(it1) }
         }
         toolbar.menu.findItem(R.id.favoriteObject)?.apply {
            viewModel?.setFavoriteContact(this, viewModel?.getFavorite())
         }?.setOnMenuItemClickListener { favorite ->
            viewModel?.let {
               it.setFavoriteContact(favorite, it.changeFavorite())
               it.update()
            }
            false
         }
         val dialogFragment = FullScreenDialogExample()
         dialogFragment.arguments = Bundle()//TODO
         this.changeContact.setOnClickListener {
            dialogFragment.show(requireActivity().supportFragmentManager, "signature")
         }
      }
      return view
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      this.viewModel = ViewModelProvider(this)[ContactViewModel::class.java]
      viewModel?.initContact(arguments?.getInt("contactId", -1))
   }
}