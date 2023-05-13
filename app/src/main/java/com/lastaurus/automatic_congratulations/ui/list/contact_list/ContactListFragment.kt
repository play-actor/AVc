package com.lastaurus.automatic_congratulations.ui.list.contact_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButtonToggleGroup
import com.lastaurus.automatic_congratulations.BaseFragment
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.Util.SpaceItemDecoration
import com.lastaurus.automatic_congratulations.data.model.Contact
import com.lastaurus.automatic_congratulations.ui.list.adapters.ContactListAdapter

class ContactListFragment : BaseFragment() {
   private var recyclerView: RecyclerView? = null
   private var adapter: ContactListAdapter? = null
   private var viewModel: ContactListViewModel? = null
   private var addContact: View? = null
   private var filterContact: MaterialButtonToggleGroup? = null
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      this.viewModel = ViewModelProvider(this)[ContactListViewModel::class.java]
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      val view: View = inflater.inflate(R.layout.fragment_contact_list, container, false)
      with(view) {
         recyclerView = this.findViewById(R.id.list)
         addContact = this.findViewById(R.id.addContactToList)
         filterContact = this.findViewById(R.id.filterContact)

      }
      adapter = ContactListAdapter()
      init(viewModel?.getContactList())
      this.addContact?.setOnClickListener {
         viewModel?.openNewContact()
      }
      filterContact?.addOnButtonCheckedListener { group, checkedId, isChecked ->
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
      filterContact?.check(R.id.buttonFilterAll)
      recyclerView?.addItemDecoration(SpaceItemDecoration())
      adapter?.setClick(object : ContactListAdapter.Click {
         override fun click(id: Int) {
            viewModel?.openContact(id)
         }
      })
      return view
   }

   fun init(contactList: LiveData<List<Contact>>?) {
      contactList?.observe(viewLifecycleOwner) { contacts ->
         adapter?.setList(contacts?.sortedBy { it.getName() })
         recyclerView?.adapter = adapter
      }
   }
}