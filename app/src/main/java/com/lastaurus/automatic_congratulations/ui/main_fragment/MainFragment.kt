package com.lastaurus.automatic_congratulations.ui.main_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.bus.BusEvent
import com.lastaurus.automatic_congratulations.bus.EventHandler
import com.lastaurus.automatic_congratulations.bus.TypeObject
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.databinding.FragmentMainBinding
import com.lastaurus.automatic_congratulations.ui.list.congratulations_list.CongratulationsListViewModel
import com.lastaurus.automatic_congratulations.ui.list.contact_list.ContactListViewModel
import com.lastaurus.automatic_congratulations.ui.list.template_list.TemplateListViewModel
import javax.inject.Inject

class MainFragment @Inject constructor() : Fragment() {
   private var contactListViewModel: ContactListViewModel? = null
   private var congratulationsListViewModel: CongratulationsListViewModel? = null
   private var templateListViewModel: TemplateListViewModel? = null
   private var _binding: FragmentMainBinding? = null
   private val binding get() = _binding!!
   private var sort: Boolean = true

   @Inject
   lateinit var eventHandler: EventHandler

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      this.contactListViewModel = ViewModelProvider(this)[ContactListViewModel::class.java]
      this.congratulationsListViewModel =
         ViewModelProvider(this)[CongratulationsListViewModel::class.java]
      this.templateListViewModel = ViewModelProvider(this)[TemplateListViewModel::class.java]
   }

   @SuppressLint("NonConstantResourceId")
   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      _binding = FragmentMainBinding.inflate(inflater, container, false)
      val view = binding.root
      binding.apply {
         toolbarMain.inflateMenu(R.menu.menu_base)
         val pagerAdapter = SectionsPagerAdapter(this@MainFragment)
         pager.adapter = pagerAdapter
         bottomAppBar.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
               R.id.contactList -> {
                  pager.setCurrentItem(0, true)
                  toolbarMain.menu.apply {
                     findItem(R.id.action_sort).setOnMenuItemClickListener {
                        sort = sort.not()
                        eventHandler.postEvent(BusEvent.Sort(sort, TypeObject.CONTACT))
                        false
                     }
                     search(findItem(R.id.action_search)) {
                        eventHandler.postEvent(BusEvent.SearchByText(it, TypeObject.CONTACT))
                     }
                     findItem(R.id.action_update_contact_list).apply {
                        setOnMenuItemClickListener {
                           contactListViewModel?.updateSystemContactList()
                           false
                        }
                        isVisible = true
                     }
                     findItem(R.id.action_insert_contact_list).apply {
                        isVisible = true
                        setOnMenuItemClickListener {
                           contactListViewModel?.insertNewSystemContactInList()
                           false
                        }
                     }
                  }
               }

               R.id.congratulations -> {
                  pager.setCurrentItem(1, true)
                  toolbarMain.menu.apply {

                     findItem(R.id.action_sort).setOnMenuItemClickListener {
                        sort = sort.not()
                        eventHandler.postEvent(BusEvent.Sort(sort, TypeObject.CONGRATULATIONS))
                        false
                     }
                     search(findItem(R.id.action_search)) {
                        eventHandler.postEvent(
                           BusEvent.SearchByText(
                              it,
                              TypeObject.CONGRATULATIONS
                           )
                        )
                     }

                     findItem(R.id.action_update_contact_list).isVisible = false
                     findItem(R.id.action_insert_contact_list).isVisible = false
                  }
               }

               R.id.templates -> {
                  pager.setCurrentItem(2, true)
                  toolbarMain.menu.apply {

                     findItem(R.id.action_sort).setOnMenuItemClickListener {
                        sort = sort.not()
                        contactListViewModel?.apply {
                           eventHandler.postEvent(BusEvent.Sort(sort, TypeObject.TEMPLATE))
                        }
                        false
                     }
                     search(findItem(R.id.action_search)) {
                        eventHandler.postEvent(BusEvent.SearchByText(it, TypeObject.TEMPLATE))
                     }

                     findItem(R.id.action_update_contact_list).isVisible = false
                     findItem(R.id.action_insert_contact_list).isVisible = false
                  }
               }

               else -> pager.setCurrentItem(0, true)
            }
            true
         }
         val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
               when (position) {
                  0 -> bottomAppBar.selectedItemId = R.id.contactList
                  1 -> bottomAppBar.selectedItemId = R.id.congratulations
                  2 -> bottomAppBar.selectedItemId = R.id.templates
                  else -> bottomAppBar.selectedItemId = R.id.contactList
               }
               super.onPageSelected(position)
            }
         }
         pager.registerOnPageChangeCallback(onPageChangeCallback as ViewPager2.OnPageChangeCallback)
      }
      return view
   }

   private fun search(searchItem: MenuItem, event: (String?) -> Unit) {
      val searchView = searchItem.actionView as SearchView
      val searchHint = getString(R.string.content_description_search)
      searchView.queryHint = searchHint
      searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
         override fun onQueryTextSubmit(query: String?): Boolean {
            return false
         }

         override fun onQueryTextChange(newText: String?): Boolean {
            event.invoke(newText)
            return false
         }
      })
   }
}