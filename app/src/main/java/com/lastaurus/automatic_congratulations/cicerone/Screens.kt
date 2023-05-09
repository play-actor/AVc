package com.lastaurus.automatic_congratulations.cicerone

import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.lastaurus.automatic_congratulations.ui.congratulation.CongratulationFragment
import com.lastaurus.automatic_congratulations.ui.contact.ContactFragment
import com.lastaurus.automatic_congratulations.ui.list.contact_list.ContactListFragment
import com.lastaurus.automatic_congratulations.ui.list.template_list.TemplateListFragment
import com.lastaurus.automatic_congratulations.ui.main_fragment.MainFragment
import com.lastaurus.automatic_congratulations.ui.template.TemplateFragment


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
   fun mainScreen() = object : FragmentScreen {
      override fun createFragment(factory: FragmentFactory) =
         MainFragment()
   }

   @JvmStatic
   fun contactScreen(contactId: Int) = object : FragmentScreen {
      override fun createFragment(factory: FragmentFactory) =
         ContactFragment().apply {
            val bundle = Bundle()
            bundle.putInt("contactId", contactId)
            arguments = bundle
         }
   }

   @JvmStatic
   fun templateScreen(templateId: Int) = object : FragmentScreen {
      override fun createFragment(factory: FragmentFactory) =
         TemplateFragment().apply {
            val bundle = Bundle()
            bundle.putInt("template_Id", templateId)
            arguments = bundle
         }
   }

   @JvmStatic
   fun congratulationScreen(congratulationId: Int) = object : FragmentScreen {
      override fun createFragment(factory: FragmentFactory) =
         CongratulationFragment().apply {
            val bundle = Bundle()
            bundle.putInt("congratulation_Id", congratulationId)
            arguments = bundle
         }
   }
}