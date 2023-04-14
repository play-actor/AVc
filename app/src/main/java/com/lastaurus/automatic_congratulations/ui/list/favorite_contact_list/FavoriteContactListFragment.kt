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
import com.lastaurus.automatic_congratulations.ui.list.contact.СhangeContactFragment
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class FavoriteContactListFragment : MvpAppCompatFragment(),IFavoriteContactListViewModel {
   @InjectPresenter
   lateinit var presenter: FavoriteContactListPresenter

   private val TAG = "FavoriteContactListFragment"
   private var recyclerView1: RecyclerView? = null
   private var contactsList: List<Contact> = ArrayList()

   @ProvidePresenter
   fun ProvidePresenterFavoriteContactListPresenter(): FavoriteContactListPresenter? {
      return FavoriteContactListPresenter()
   }

   @SuppressLint("MissingInflatedId")
   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      val inflate1: View =
         inflater.inflate(R.layout.fragment_favorite_contact_list, container, false)
      recyclerView1 = inflate1.findViewById(R.id.list_favorites_contact)
      return inflate1
   }

   override fun setData(contactList: List<Contact>) {
      this.contactsList = contactList
      // создаем адаптер
      val adapter1 = ContactListAdapter(contactsList)
      // устанавливаем для списка адаптер
      this.recyclerView1?.adapter = adapter1
      adapter1.setClick(object : ContactListAdapter.Click {
         override fun click(id: Int) {
            val contactFragment = СhangeContactFragment.newInstance(id)
            requireActivity().supportFragmentManager.beginTransaction()
               .replace(R.id.root, contactFragment, "ContactFragment")
               .addToBackStack("ContactFragment")
               .commitAllowingStateLoss()
         }
      }
      )
   }
}
