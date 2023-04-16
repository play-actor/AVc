package com.lastaurus.automatic_congratulations.ui.main_fragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lastaurus.automatic_congratulations.ui.list.all_congratulations_list.AllCongratulationsListFragment
import com.lastaurus.automatic_congratulations.ui.list.contact_list.ContactListFragment
import com.lastaurus.automatic_congratulations.ui.list.favorite_contact_list.FavoriteContactListFragment
import com.lastaurus.automatic_congratulations.ui.list.template_list.TemplateListFragment

class SectionsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

   override fun createFragment(position: Int): Fragment {
      val templateListFragment: Fragment = TemplateListFragment()
      val favoriteContactListFragment: Fragment = FavoriteContactListFragment()
      val allCongratulationsListFragment: Fragment = AllCongratulationsListFragment()
      val contactListFragment: Fragment = ContactListFragment()
      when (position) {
         0 -> return contactListFragment
         1 -> return favoriteContactListFragment
         2 -> return allCongratulationsListFragment
         3 -> return templateListFragment
      }
      return contactListFragment
   }

   override fun getItemCount(): Int {
      return 4
   }
}