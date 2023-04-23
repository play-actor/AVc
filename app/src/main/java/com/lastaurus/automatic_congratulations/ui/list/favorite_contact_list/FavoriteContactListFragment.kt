package com.lastaurus.automatic_congratulations.ui.list.favorite_contact_list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.data.model.Contact
import com.lastaurus.automatic_congratulations.ui.list.ContactListAdapter
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class FavoriteContactListFragment : MvpAppCompatFragment(),IFavoriteContactListViewModel {
   @InjectPresenter
   lateinit var presenter: FavoriteContactListPresenter

   private var recyclerView: RecyclerView? = null
   private var contactsList: List<Contact> = ArrayList()

   @ProvidePresenter
   fun ProvidePresenterFavoriteContactListPresenter(): FavoriteContactListPresenter {
      return FavoriteContactListPresenter()
   }

   @SuppressLint("MissingInflatedId")
   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      val view: View =
         inflater.inflate(R.layout.fragment_favorite_contact_list, container, false)
      recyclerView = view.findViewById(R.id.list_favorites_contact)
      return view
   }

   override fun setData(contactList: List<Contact>) {
      this.contactsList = contactList
      val adapter = ContactListAdapter(contactsList)
      this.recyclerView?.adapter = adapter
      adapter.setClick(object : ContactListAdapter.Click {
         override fun click(id: Int) {
            presenter.openContact(id)
         }
      }
      )
   }
}
