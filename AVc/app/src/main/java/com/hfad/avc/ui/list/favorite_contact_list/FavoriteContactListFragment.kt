package com.hfad.avc.ui.list.favorite_contact_list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.avc.R
import com.hfad.avc.data.model.Contact
import com.hfad.avc.ui.contact.СhangeContactFragment
import com.hfad.avc.ui.list.ListAdapter
import com.hfad.avc.ui.list.all_congratulations_list.IAllCongratulationsListViewModel
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
      savedInstanceState: Bundle?
   ): View? {
      val inflate1: View = inflater.inflate(R.layout.fragment_favorite_contact_list, container, false)
      recyclerView1 = inflate1.findViewById(R.id.list_favorites_contact)
      return inflate1
   }

   override fun setData(listContactsList: List<Contact>) {
      contactsList = listContactsList
      // создаем адаптер
      val adapter1 = ListAdapter(contactsList)
      // устанавливаем для списка адаптер
      recyclerView1!!.adapter = adapter1
      adapter1.setClick { id: Int ->
         val contactFragment = СhangeContactFragment.newInstance(id)
         requireActivity().getSupportFragmentManager().beginTransaction()
            .replace(R.id.root, contactFragment, "ContactFragment")
            .addToBackStack("ContactFragment")
            .commitAllowingStateLoss()
      }
   }
}
