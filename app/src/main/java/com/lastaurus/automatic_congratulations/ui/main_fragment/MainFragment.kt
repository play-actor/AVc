package com.lastaurus.automatic_congratulations.ui.main_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationBarView
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.dagger.ComponentManager.Companion.instance

class MainFragment : Fragment() {
   private var barView: NavigationBarView? = null
   private var viewPager: ViewPager2? = null
   private var onPageChangeCallback: ViewPager2.OnPageChangeCallback? = null
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
      barView = view.findViewById(R.id.bottomAppBar)
      val pagerAdapter = SectionsPagerAdapter(
         this
      )
      viewPager = view.findViewById(R.id.pager)
      viewPager?.adapter = pagerAdapter
      barView?.setOnItemSelectedListener { item: MenuItem ->
         when (item.itemId) {
            R.id.contactList -> viewPager?.setCurrentItem(0, true)
            R.id.contactfavorites -> viewPager?.setCurrentItem(1, true)
            R.id.templated_contacts -> viewPager?.setCurrentItem(2, true)
            R.id.templates -> viewPager?.setCurrentItem(3, true)
            else -> viewPager?.setCurrentItem(0, true)
         }
         true
      }
      onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
         override fun onPageSelected(position: Int) {
            when (position) {
               0 -> barView?.setSelectedItemId(R.id.contactList)
               1 -> barView?.setSelectedItemId(R.id.contactfavorites)
               2 -> barView?.setSelectedItemId(R.id.templated_contacts)
               3 -> barView?.setSelectedItemId(R.id.templates)
               else -> barView?.setSelectedItemId(R.id.contactList)
            }
            super.onPageSelected(position)
         }
      }
      viewPager?.registerOnPageChangeCallback(onPageChangeCallback as ViewPager2.OnPageChangeCallback)
      return view
   }

}