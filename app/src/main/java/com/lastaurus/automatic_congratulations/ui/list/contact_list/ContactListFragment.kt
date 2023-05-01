package com.lastaurus.automatic_congratulations.ui.list.contact_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.lastaurus.automatic_congratulations.BaseFragment
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.Util.SpaceItemDecoration
import com.lastaurus.automatic_congratulations.dagger.ComponentManager.Companion.instance
import com.lastaurus.automatic_congratulations.data.model.Contact
import com.lastaurus.automatic_congratulations.ui.list.ContactListAdapter

class ContactListFragment : BaseFragment() {
   private var recyclerView: RecyclerView? = null
   private var adapter: ContactListAdapter? = null
   private var viewModel: ContactListViewModel? = null
   lateinit var addContact: View
   lateinit var filterContact: View

   init {
      instance.appComponent.inject(this)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      this.viewModel = ViewModelProvider(this)[ContactListViewModel::class.java]
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      val view: View = inflater.inflate(R.layout.fragment_contact_list, container, false)
      recyclerView = view.findViewById(R.id.list)

      this.addContact = view.findViewById(R.id.addContactToList)
      this.filterContact = view.findViewById(R.id.filterContact)
      adapter = ContactListAdapter()
      init(viewModel?.getContactList())
      this.addContact.setOnClickListener {
         viewModel?.openNewContact()
      }
      this.filterContact.setOnClickListener {
         init(viewModel?.getContactListFavorite())
      }
      return view
   }

   fun init(contactList: List<Contact>?) {
      adapter?.setList(contactList?.sortedBy { it.getName() })
      recyclerView?.adapter = adapter
      recyclerView?.addItemDecoration(SpaceItemDecoration())
      adapter?.setClick(object : ContactListAdapter.Click {
         override fun click(id: Int) {
            viewModel?.openContact(id)
         }
      })
   }
}