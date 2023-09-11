package com.lastaurus.automatic_congratulations.ui.list.contact_list

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.lastaurus.automatic_congratulations.BaseFragment
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.bus.BusEvent
import com.lastaurus.automatic_congratulations.bus.EventHandler
import com.lastaurus.automatic_congratulations.bus.TypeObject
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.data.model.Contact
import com.lastaurus.automatic_congratulations.databinding.FragmentContactListBinding
import com.lastaurus.automatic_congratulations.ui.list.adapters.ContactListAdapter
import com.lastaurus.automatic_congratulations.util.SpaceItemDecoration
import com.lastaurus.automatic_congratulations.util.isNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ContactListFragment @Inject constructor() : BaseFragment() {
   private var adapter: ContactListAdapter? = null
   private var viewModel: ContactListViewModel? = null
   private var _binding: FragmentContactListBinding? = null
   private val binding get() = _binding!!

   @Inject
   lateinit var eventHandler: EventHandler

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   private val requestPermissionLauncher =
      registerForActivityResult(
         ActivityResultContracts.RequestPermission()
      ) { isGranted: Boolean ->
         if (isGranted) uploadContactList()
      }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      this.viewModel = ViewModelProvider(this)[ContactListViewModel::class.java]
      subscribeOnEventBus()
   }

   @SuppressLint("CheckResult")
   fun uploadContactList() {
      if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS)
         == PackageManager.PERMISSION_GRANTED
      ) {
         binding.loadContact.visibility = View.GONE
         viewModel?.firstLoadSystemContactList()
      } else {
         requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
      }
   }

   private fun subscribeOnEventBus() {
      eventHandler.subscribeEvent { busEvent ->
         (busEvent as? BusEvent.Sort)?.apply {
            if (this.type == TypeObject.CONTACT) {
               init(viewModel?.getContactList(this.aZ))
            }
         }
         (busEvent as? BusEvent.SearchByText)?.apply {
            if (this.type == TypeObject.CONTACT) {
               this.text?.apply { init(viewModel?.getContactByPeaceName(this)) }
               if (this.text.isNull()) {
                  init(viewModel?.getContactList())
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
      _binding = FragmentContactListBinding.inflate(inflater, container, false)
      val view = binding.root
      binding.apply {
         adapter = ContactListAdapter()
         this.addContactToList.setOnClickListener {
            viewModel?.openNewContact()
         }
         this.loadContact.apply {
            visibility = viewModel?.getNeedVisibilityLoadContact() ?: View.GONE
         }.setOnClickListener {
            uploadContactList()
         }
         list.addItemDecoration(SpaceItemDecoration())
         filterContact.addOnButtonCheckedListener { _, checkedId, isChecked ->
            when (checkedId) {
               R.id.buttonFilterAll -> {
                  if (!isChecked) return@addOnButtonCheckedListener
                  init(viewModel?.getContactList())
               }

               R.id.buttonFilterFavorite -> {
                  if (!isChecked) return@addOnButtonCheckedListener
                  init(viewModel?.getContactListFavorite())
               }

               else -> return@addOnButtonCheckedListener
            }
         }
         filterContact.check(R.id.buttonFilterAll)
         adapter?.setClick(object : ContactListAdapter.Click {
            override fun click(id: Int) {
               viewModel?.openContact(id)
            }
         })
      }
      return view
   }

   fun init(contactList: LiveData<List<Contact>>?) {
      lifecycleScope.launch {
         withContext(Dispatchers.Main) {
            contactList?.observe(viewLifecycleOwner) { contacts ->
               adapter?.setList(contacts)
               binding.list.adapter = adapter
            }
         }
      }
   }
}