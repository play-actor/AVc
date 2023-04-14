package com.lastaurus.automatic_congratulations.dagger

import android.content.Context
import com.lastaurus.automatic_congratulations.AvcApplication
import com.lastaurus.automatic_congratulations.dagger.module.BusModule
import com.lastaurus.automatic_congratulations.dagger.module.HelperModule
import com.lastaurus.automatic_congratulations.dagger.module.ImageModule
import com.lastaurus.automatic_congratulations.dagger.module.NavigationModule
import com.lastaurus.automatic_congratulations.managers.DBManager
import com.lastaurus.automatic_congratulations.ui.list.CongratulationsListAdapter
import com.lastaurus.automatic_congratulations.ui.list.ContactListAdapter
import com.lastaurus.automatic_congratulations.ui.list.PhoneListAdapter
import com.lastaurus.automatic_congratulations.ui.list.all_congratulations_list.AllCongratulationsListPresenter
import com.lastaurus.automatic_congratulations.ui.list.contact.СhangeContactFragment
import com.lastaurus.automatic_congratulations.ui.list.contact.СhangeContactPresenter
import com.lastaurus.automatic_congratulations.ui.list.contact_list.ContactListFragment
import com.lastaurus.automatic_congratulations.ui.list.contact_list.ContactListPresenter
import com.lastaurus.automatic_congratulations.ui.list.favorite_contact_list.FavoriteContactListPresenter
import com.lastaurus.automatic_congratulations.ui.list.template_list.TemplateListPresenter
import com.lastaurus.automatic_congratulations.ui.main_avtivity.MainActivity
import com.lastaurus.automatic_congratulations.ui.main_avtivity.MainPresenter
import com.lastaurus.automatic_congratulations.ui.main_fragment.MainFragment
import com.lastaurus.automatic_congratulations.ui.template.СhangeTemplatePresenter
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

   fun inject(classes: СhangeContactFragment)

   fun inject(classes: ContactListFragment)

   fun inject(classes: DBManager)

   fun inject(classes: ContactListAdapter)

   fun inject(classes: CongratulationsListAdapter)

   fun inject(classes: PhoneListAdapter)

   fun inject(classes: ContactListPresenter)

   fun inject(classes: AllCongratulationsListPresenter)

   fun inject(classes: FavoriteContactListPresenter)

   fun inject(classes: MainActivity)

   fun inject(classes: TemplateListPresenter)

   fun inject(classes: MainPresenter)

   fun inject(classes: СhangeContactPresenter)

   fun inject(classes: СhangeTemplatePresenter)
}