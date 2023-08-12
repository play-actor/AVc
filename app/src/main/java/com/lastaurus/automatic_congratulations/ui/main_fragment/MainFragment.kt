package com.lastaurus.automatic_congratulations.ui.main_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationBarView
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.dagger.ComponentManager.Companion.instance

class MainFragment : Fragment() {
   private var barView: NavigationBarView? = null
   private var viewPager: ViewPager2? = null
   private var onPageChangeCallback: ViewPager2.OnPageChangeCallback? = null
   private var mActionBarToolbar: Toolbar? = null
   override fun onCreate(savedInstanceState: Bundle?) {
      instance.appComponent.inject(this)
      super.onCreate(savedInstanceState)
   }

   @SuppressLint("NonConstantResourceId")
   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      val view: View = inflater.inflate(R.layout.fragment_main, container, false)
      with(view) {
         barView = this.findViewById(R.id.bottomAppBar)
         mActionBarToolbar = this.findViewById(R.id.toolbarMain)
         viewPager = this.findViewById(R.id.pager)
      }
      mActionBarToolbar?.inflateMenu(R.menu.menu_contactlist)
      val pagerAdapter = SectionsPagerAdapter(this)
      viewPager?.adapter = pagerAdapter
      barView?.setOnItemSelectedListener { item: MenuItem ->
         when (item.itemId) {
            R.id.contactList -> viewPager?.setCurrentItem(0, true)
            R.id.templated_contacts -> viewPager?.setCurrentItem(1, true)
            R.id.templates -> viewPager?.setCurrentItem(2, true)
            else -> viewPager?.setCurrentItem(0, true)
         }
         true
      }
      onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
         override fun onPageSelected(position: Int) {
            when (position) {
               0 -> barView?.selectedItemId = R.id.contactList
               1 -> barView?.selectedItemId = R.id.templated_contacts
               2 -> barView?.selectedItemId = R.id.templates
               else -> barView?.selectedItemId = R.id.contactList
            }
            super.onPageSelected(position)
         }
      }
      viewPager?.registerOnPageChangeCallback(onPageChangeCallback as ViewPager2.OnPageChangeCallback)
      return view
   }

}