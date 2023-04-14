package com.lastaurus.automatic_congratulations.ui.list.contact


import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.dagger.ComponentManager.Companion.instance
import com.lastaurus.automatic_congratulations.dagger.module.ImageModule
import com.lastaurus.automatic_congratulations.data.database.AppDatabase
import com.lastaurus.automatic_congratulations.data.model.Contact
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject


@InjectViewState
class СhangeContactPresenter(bundle: Bundle?) :
   MvpPresenter<IСhangeContactViewModel?>() {
   @Inject
   lateinit var db: AppDatabase

//   @Inject
//   lateinit var rxBus: RxBus

   @Inject
   lateinit var context: Context

   @Inject
   lateinit var imageModule: ImageModule

   //   private val disposable: CompositeDisposable = CompositeDisposable()
   private val contact: Contact
   private val contactId: String

   init {
      instance.appComponent.inject(this)
      contactId = bundle?.getInt("congratulations", -1).toString()
      contact = db.contactDao().getById(contactId)
      //      template = db.templateDao().getById(String.valueOf(rnd()));
      viewState!!.setData(contact)
   }

   //   public int rnd() {
   //      int min = 0; // Минимальное число для диапазона
   //      int max = db.templateDao().getAll().size(); // Максимальное число для диапазона
   //      max -= min;
   //      return (int) (Math.random() * ++max) + min;
   //   }
   //   public void updateDBonContact() {
   //      if (this.contact.getDate_congratulationsString() != null & this.contact.getWorked()) {
   //         this.db.contactDao().update(this.contact);
   //         getViewState().setWorker(
   //               this.contact.getId(), this.contact.getName(), this.contact.getPhoneList(), this.contact.getDate_congratulationsString(), this.template.getTextTemplate());
   //      } else if (this.contact.getDate_congratulationsString() != null & !this.contact.getWorked()) {
   //         this.db.contactDao().update(this.contact);
   //      }
   //   }
   fun updateContactDB() {
      db.contactDao().update(contact)
   }

   fun setIconContact(iconContact: ImageView) {
      imageModule.showImageForContact(iconContact, contact.getUriFull(), true)
   }

   fun setFavoriteContact(favoriteContact: MenuItem) {
      val iconId: Int =
         if (contact.getFavorite()) R.drawable.ic_baseline_star_favorite else R.drawable.ic_baseline_star_no_favorite
      favoriteContact.setIcon(iconId)
   }

   fun setOnClickFavoriteContact() {
      contact.setFavorite(!contact.getFavorite())
   } //   @Override
   //   protected void onFirstViewAttach() {
   //      this.disposable.addAll(
   //            rxBus.waitCall()
   //                  .subscribeOn(Schedulers.io())
   //                  .subscribe(requestCode -> {
   //                           if (requestCode == 2) {
   //                              getViewState().setWorker();
   //                           }
   //                        }
   //                  ));
   //      super.onFirstViewAttach();
   //   }
   //   public void setNewDate() {
   //      getViewState().setDateNew();
   //   }
   //
   //   public void setNewTime() {
   //      getViewState().setTimeNew();
   //   }
   //   public static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("dd.MM.yyyy, HH:mm");
   //   public void setDate(Long selection) {
   //      this.contact.setDate_congratulations(selection);
   //      Log.i("AVc", String.valueOf(selection));
   //      this.contact.setDate_congratulationsString(FORMATTER.print(selection));
   //      Log.i("AVc", String.valueOf(FORMATTER.print(selection)));
   //   }
}