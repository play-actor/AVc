package com.lastaurus.avc.cicerone

import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.lastaurus.avc.ui.contact.СhangeContactFragment
import com.lastaurus.avc.ui.main_fragment.MainFragment
import com.lastaurus.avc.ui.list.contact_list.ContactListFragment
import com.lastaurus.avc.ui.list.template_list.TemplateListFragment
import com.lastaurus.avc.ui.template.СhangeTemplateFragment


object Screens {
   @JvmStatic
   fun templateListScreen() = object : FragmentScreen {
      override fun createFragment(factory: FragmentFactory) =
         TemplateListFragment()
   }
   @JvmStatic
   fun conatctListScreen() = object : FragmentScreen {
      override fun createFragment(factory: FragmentFactory) =
         ContactListFragment()
   }

   @JvmStatic
   fun main() = object : FragmentScreen {
      override fun createFragment(factory: FragmentFactory) =
         MainFragment()
   }

   @JvmStatic
   fun changeContactScreen(congratulations:Int) = object : FragmentScreen {
      override fun createFragment(factory: FragmentFactory) =
         СhangeContactFragment().apply {
            val bundle = Bundle()
            bundle.putInt("congratulations", congratulations)
            arguments = bundle
         }
   }

   @JvmStatic
   fun changeTemplateScreen(templateId:Int) = object : FragmentScreen {
      override fun createFragment(factory: FragmentFactory) =
         СhangeTemplateFragment().apply {
            val bundle = Bundle()
            bundle.putInt("template_Id", templateId)
            arguments = bundle
         }
   }
}