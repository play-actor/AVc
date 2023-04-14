package com.lastaurus.avc.dagger

import android.content.Context
import com.lastaurus.avc.AvcApplication
import com.lastaurus.avc.dagger.module.BusModule
import com.lastaurus.avc.dagger.module.HelperModule
import com.lastaurus.avc.dagger.module.ImageModule
import com.lastaurus.avc.dagger.module.NavigationModule
import com.lastaurus.avc.managers.DBManager
import com.lastaurus.avc.ui.contact.СhangeContactFragment
import com.lastaurus.avc.ui.contact.СhangeContactPresenter
import com.lastaurus.avc.ui.list.ListAdapter
import com.lastaurus.avc.ui.list.contact_list.ContactListPresenter
import com.lastaurus.avc.ui.main_avtivity.MainActivity
import com.lastaurus.avc.ui.main_avtivity.MainPresenter
import com.lastaurus.avc.ui.list.all_congratulations_list.AllCongratulationsListPresenter
import com.lastaurus.avc.ui.list.contact_list.ContactListFragment
import com.lastaurus.avc.ui.list.favorite_contact_list.FavoriteContactListPresenter
import com.lastaurus.avc.ui.main_fragment.MainFragment
import com.lastaurus.avc.ui.template.СhangeTemplatePresenter
import com.lastaurus.avc.ui.list.template_list.TemplateListPresenter
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

   fun inject(classes: ListAdapter)

   fun inject(classes: ContactListPresenter)

   fun inject(classes: AllCongratulationsListPresenter)

   fun inject(classes: FavoriteContactListPresenter)

   fun inject(classes: MainActivity)

   fun inject(classes: TemplateListPresenter)

   fun inject(classes: MainPresenter)

   fun inject(classes: СhangeContactPresenter)

   fun inject(classes: СhangeTemplatePresenter)
}