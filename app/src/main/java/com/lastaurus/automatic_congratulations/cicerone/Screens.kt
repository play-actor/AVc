package com.lastaurus.automatic_congratulations.cicerone

import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.lastaurus.automatic_congratulations.ui.list.contact.СhangeContactFragment
import com.lastaurus.automatic_congratulations.ui.list.contact_list.ContactListFragment
import com.lastaurus.automatic_congratulations.ui.list.template_list.TemplateListFragment
import com.lastaurus.automatic_congratulations.ui.main_fragment.MainFragment
import com.lastaurus.automatic_congratulations.ui.template.ChangeTemplateFragment


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
         ChangeTemplateFragment().apply {
            val bundle = Bundle()
            bundle.putInt("template_Id", templateId)
            arguments = bundle
         }
   }
}