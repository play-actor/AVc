package com.hfad.avc.dagger

import android.content.Context
import com.hfad.avc.AvcApplication
import com.hfad.avc.dagger.module.BusModule
import com.hfad.avc.dagger.module.HelperModule
import com.hfad.avc.dagger.module.ImageModule
import com.hfad.avc.dagger.module.NavigationModule
import com.hfad.avc.managers.DBManager
import com.hfad.avc.ui.contact.СhangeContactFragment
import com.hfad.avc.ui.contact.СhangeContactPresenter
import com.hfad.avc.ui.list.ContactListAdapter
import com.hfad.avc.ui.list.PhoneListAdapter
import com.hfad.avc.ui.list.contact_list.ContactListPresenter
import com.hfad.avc.ui.main_avtivity.MainActivity
import com.hfad.avc.ui.main_avtivity.MainPresenter
import com.hfad.avc.ui.list.all_congratulations_list.AllCongratulationsListPresenter
import com.hfad.avc.ui.list.contact_list.ContactListFragment
import com.hfad.avc.ui.list.favorite_contact_list.FavoriteContactListPresenter
import com.hfad.avc.ui.main_fragment.MainFragment
import com.hfad.avc.ui.template.СhangeTemplatePresenter
import com.hfad.avc.ui.list.template_list.TemplateListPresenter
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