package com.lastaurus.automatic_congratulations.dagger

import android.content.Context
import com.lastaurus.automatic_congratulations.AvcApplication
import com.lastaurus.automatic_congratulations.dagger.module.BusModule
import com.lastaurus.automatic_congratulations.dagger.module.HelperModule
import com.lastaurus.automatic_congratulations.dagger.module.ImageModule
import com.lastaurus.automatic_congratulations.dagger.module.NavigationModule
import com.lastaurus.automatic_congratulations.managers.DBManager
import com.lastaurus.automatic_congratulations.ui.contact.ChangeContactFragment
import com.lastaurus.automatic_congratulations.ui.contact.ContactViewModel
import com.lastaurus.automatic_congratulations.ui.list.CongratulationsListAdapter
import com.lastaurus.automatic_congratulations.ui.list.ContactListAdapter
import com.lastaurus.automatic_congratulations.ui.list.PhoneListAdapter
import com.lastaurus.automatic_congratulations.ui.list.contact_list.ContactListFragment
import com.lastaurus.automatic_congratulations.ui.list.contact_list.ContactListViewModel
import com.lastaurus.automatic_congratulations.ui.list.template_list.TemplateListViewModel
import com.lastaurus.automatic_congratulations.ui.main_avtivity.MainActivity
import com.lastaurus.automatic_congratulations.ui.main_avtivity.MainPresenter
import com.lastaurus.automatic_congratulations.ui.main_fragment.MainFragment
import com.lastaurus.automatic_congratulations.ui.template.ChangeTemplateFragment
import com.lastaurus.automatic_congratulations.ui.template.TemplateViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [HelperModule::class, NavigationModule::class, BusModule::class, ImageModule::class])
interface AppComponent {

   @Component.Factory
   interface Factory {
      fun create(@BindsInstance context: Context): AppComponent
   }

   fun inject(classes: AvcApplication)

   fun inject(classes: MainFragment)

   fun inject(classes: ChangeContactFragment)

   fun inject(classes: ChangeTemplateFragment)
   fun inject(classes: ContactViewModel)
   fun inject(classes: ContactListViewModel)
   fun inject(classes: TemplateViewModel)
   fun inject(classes: TemplateListViewModel)

   fun inject(classes: ContactListFragment)

   fun inject(classes: DBManager)

   fun inject(classes: ContactListAdapter)

   fun inject(classes: CongratulationsListAdapter)

   fun inject(classes: PhoneListAdapter)

   fun inject(classes: MainActivity)

   fun inject(classes: MainPresenter)

}