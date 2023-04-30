package com.lastaurus.automatic_congratulations.ui.list.contact_list

import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.lastaurus.automatic_congratulations.BaseFragment
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.dagger.ComponentManager.Companion.instance
import com.lastaurus.automatic_congratulations.data.model.Contact
import com.lastaurus.automatic_congratulations.managers.DBManager
import com.lastaurus.automatic_congratulations.ui.list.ContactListAdapter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.util.Collections
import javax.inject.Inject

class ContactListFragment : BaseFragment(), IContactListViewModel {
   @InjectPresenter
   lateinit var presenter: ContactListPresenter
   private var recyclerView: RecyclerView? = null
   private var contactsList: List<Contact> = ArrayList()
   private val mActionBarToolbar: Toolbar? = null
   private var sortAbMenuItem: MenuItem? = null
   private var searchMenuItem: MenuItem? = null
   private var mSearchView: SearchView? = null

   @Inject
   lateinit var dbManager: DBManager

   @ProvidePresenter
   fun ProvidePresenterNameListPresenter(): ContactListPresenter {
      return ContactListPresenter()
   }

   init {
      instance.appComponent.inject(this)
   }

   override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
      mActionBarToolbar?.inflateMenu(R.menu.menu_contactlist)
      val menuFinal = mActionBarToolbar?.menu
      menuFinal?.let {
         super.onCreateOptionsMenu(menuFinal, inflater)
         searchMenuItem = menuFinal.findItem(R.id.action_search)
//         mSearchView = searchMenuItem?.actionView as SearchView
//         val searchManager =
//            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
//         mSearchView?.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
//         mSearchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//               return false
//            }
//
//            @SuppressLint("CheckResult")
//            override fun onQueryTextChange(searchText: String): Boolean {
//               Log.i(TAG, "Контакт: $searchText")
//               dbManager.Filt(searchText)
//                  .subscribeOn(Schedulers.computation())
//                  .observeOn(AndroidSchedulers.mainThread())
//                  .subscribe(
//                     { list: List<Contact> ->
//                        setData(
//                           list,
//                           0
//                        )
//                     }
//                  ) { throwable: Throwable -> throwable.printStackTrace() }
//               return false
//            }
//         })
         sortAbMenuItem = menuFinal.findItem(R.id.action_sort).let {
            it.setOnMenuItemClickListener {
               if (sortAbMenuItem?.isChecked == true) {
                  presenter.reMove(0)
                  sortAbMenuItem?.isChecked = false
               } else {
                  presenter.reMove(1)
                  sortAbMenuItem?.isChecked = true
               }
               true
            }
         }
      }
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setHasOptionsMenu(true)
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      val inflate: View = inflater.inflate(R.layout.fragment_contact_list, container, false)
      recyclerView = inflate.findViewById(R.id.list)
      recyclerView?.addItemDecoration(SpaceItemDecoration())
      return inflate
   }

   private inner class SpaceItemDecoration : RecyclerView.ItemDecoration() {
      override fun getItemOffsets(
         outRect: Rect,
         view: View,
         parent: RecyclerView,
         state: RecyclerView.State,
      ) {
         val margin = 88
         val space = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            margin.toFloat(),
            view.resources.displayMetrics
         ).toInt()
         if (parent.getChildLayoutPosition(view) == (parent.adapter?.itemCount?.minus(1))) {
            outRect.top = 0
            outRect.bottom = space
         }
      }
   }

   override fun setData(contactsList: List<Contact>, typeSort: Int) {
      this.contactsList = contactsList
      sortList(typeSort)
      val adapter = ContactListAdapter(this.contactsList)
      recyclerView?.adapter = adapter
      adapter.setClick(object : ContactListAdapter.Click {
         override fun click(id: Int) {
            presenter.openContact(id)
         }
      })
   }

   fun sortList(typeSort: Int) {
      val comparator: Comparator<Contact> =
         Comparator { o1: Contact, o2: Contact ->
            if (typeSort == 0)
               o1.getName().compareTo(o2.getName())
            else
               o2.getName().compareTo(o1.getName())
         }
      Collections.sort(contactsList, comparator)
   }
}