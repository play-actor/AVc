package com.hfad.avc.ui.contact;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.hfad.avc.R;
import com.hfad.avc.bus.RxBus;
import com.hfad.avc.dagger.ComponentManager;
import com.hfad.avc.dagger.module.ImageModule;
import com.hfad.avc.data.database.AppDatabase;
import com.hfad.avc.data.model.Contact;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class СhangeContactPresenter extends MvpPresenter<IСhangeContactViewModel> {
   @Inject
   AppDatabase db;

   @Inject
   RxBus rxBus;

   @Inject
   Context context;

   @Inject
   ImageModule imageModule;

   protected final CompositeDisposable disposable = new CompositeDisposable();
   private Contact contact;
   private String contactId;

   public СhangeContactPresenter(Bundle bundle) {
      ComponentManager.Companion.getInstance().appComponent.inject(this);
      contactId = String.valueOf(bundle.getInt("congratulations", -1));
      contact = db.contactDao().getById(contactId);
//      template = db.templateDao().getById(String.valueOf(rnd()));
      getViewState().setData(contact);
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

   public void updateContactDB() {
      this.db.contactDao().update(this.contact);
   }
   public void setIconContact(ImageView iconContact) {
      imageModule.showImageForContact(iconContact, contact.getUriFull(), true);
   }

   public void setFavoriteContact(MenuItem favoriteContact) {
      int iconId = contact.getFavorite() ? R.drawable.ic_baseline_star_favorite : R.drawable.ic_baseline_star_no_favorite;
      favoriteContact.setIcon(iconId);
   }

   public void setOnClickFavoriteContact() {
      contact.setFavorite(!contact.getFavorite());
   }

//   @Override
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
