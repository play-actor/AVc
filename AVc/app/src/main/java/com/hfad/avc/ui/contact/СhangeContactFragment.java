package com.lastaurus.automatic_congratulations.ui.contact;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TimePicker;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.work.Data;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.lastaurus.automatic_congratulations.R;
import com.lastaurus.automatic_congratulations.dagger.ComponentManager;
import com.lastaurus.automatic_congratulations.data.model.Contact;
import com.lastaurus.automatic_congratulations.databinding.FragmentContactBinding;
import com.lastaurus.automatic_congratulations.managers.DBManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;


public class СhangeContactFragment extends MvpAppCompatFragment implements IСhangeContactViewModel {
   @Inject
   DBManager dbManager;

   @InjectPresenter
   СhangeContactPresenter presenter;

   private String TAG = "AVc";
   Data data = null;
   Date thisDateCon;
   public CoordinatorLayout coordLayout;
   Date myTime = java.util.Calendar.getInstance().getTime();
   private FragmentContactBinding binding;
   public static final String PERMISSION_STRING = Manifest.permission.SEND_SMS;
   private final int PERMISSION_REQUEST_CODE = 2;
   SimpleDateFormat format = new SimpleDateFormat();
   private String id, name, phoneNumber, dateCon, getTextTemplate;
   java.util.Calendar dateAndTime = java.util.Calendar.getInstance();
   ImageView iconContact, favoriteContact;

   public static СhangeContactFragment newInstance(Integer congratulations) {
      СhangeContactFragment contactFragment = new СhangeContactFragment();
      Bundle args = new Bundle();
      args.putInt("congratulations", congratulations);
      contactFragment.setArguments(args);
      return contactFragment;
   }

   @Override
   public void onCreate(Bundle savedInstanceState) {
      ComponentManager.Companion.getInstance().appComponent.inject(this);
      super.onCreate(savedInstanceState);
   }

   @ProvidePresenter
   СhangeContactPresenter ProvidePresenterContactPresenter() {
      return new СhangeContactPresenter(getArguments());
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false);
      View view = binding.getRoot();
      this.iconContact = view.findViewById(R.id.iconContact);
      this.favoriteContact = view.findViewById(R.id.favoriteContact);
      this.presenter.setIconContact(iconContact);
      this.presenter.setFavoriteContact(favoriteContact);
      favoriteContact.setOnClickListener(v -> {
         this.presenter.setOnClickFavoriteContact();
         this.presenter.setFavoriteContact(favoriteContact);
      });
      return view;
   }


   @Override
   public void setData(Contact contact) {
      this.binding.setContactDetail(contact);
      this.binding.setPresenter(this.presenter);
   }

   @Override
   public void setWorker(String id, String name, String phoneNumber, String dateCon, String getTextTemplate) {
      if (ContextCompat.checkSelfPermission(requireActivity(), PERMISSION_STRING)
            == PackageManager.PERMISSION_GRANTED) {
         data = new Data.Builder()
               .putString("Name", name)
               .putString("Phone", phoneNumber)
               .putString("TextTemplate", getTextTemplate)
               .build();
         try {
            format.applyPattern("dd.MM.yyyy, HH:mm");
            thisDateCon = format.parse(dateCon);
         } catch (ParseException e) {
            e.printStackTrace();
         }
         if (thisDateCon != null) {
            long seconds = ((thisDateCon.getTime() - myTime.getTime()) / (1000));
            if (seconds < 0) {
               seconds += 31536000;
            }
            if (this.binding.worked.isChecked()) {
               dbManager.createWorkRequest(data, seconds, id)
                     .subscribeOn(Schedulers.io())
                     .observeOn(AndroidSchedulers.mainThread())
                     .subscribe(
                           () -> Log.d(TAG, "Set Worker"),
                           throwable -> Log.e(TAG, "Worker error", throwable)
                     );
               coordLayout = requireActivity().findViewById(R.id.mainFragApp);
               Snackbar.make(coordLayout, "Сохранено", BaseTransientBottomBar.LENGTH_LONG).show();
            } else {
               dbManager.cancelWorkRequest(id)
                     .subscribeOn(Schedulers.io())
                     .observeOn(AndroidSchedulers.mainThread())
                     .subscribe(
                           () -> Log.d(TAG, "Set Worker"),
                           throwable -> Log.e(TAG, "Worker error", throwable)
                     );
            }
         }
      } else {
         this.name = name;
         this.id = id;
         this.phoneNumber = phoneNumber;
         this.dateCon = dateCon;
         this.getTextTemplate = getTextTemplate;
         ActivityCompat.requestPermissions(requireActivity(),
               new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
      }
   }

   @Override
   public void setWorker() {
      setWorker(this.id, this.name, this.phoneNumber, this.dateCon, this.getTextTemplate);
   }

   // отображаем диалоговое окно для выбора даты
   @Override
   public void setDateNew() {
      new DatePickerDialog(getActivity(), d,
            dateAndTime.get(java.util.Calendar.YEAR),
            dateAndTime.get(java.util.Calendar.MONTH),
            dateAndTime.get(java.util.Calendar.DAY_OF_MONTH))
            .show();
   }

   // отображаем диалоговое окно для выбора времени
   @Override
   public void setTimeNew() {
      new TimePickerDialog(getActivity(), t,
            dateAndTime.get(java.util.Calendar.HOUR_OF_DAY),
            dateAndTime.get(java.util.Calendar.MINUTE), true)
            .show();
   }

   private void setInitialDateTime() {
      DateUtils.formatDateTime(getActivity(),
            dateAndTime.getTimeInMillis(),
            DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                  | DateUtils.FORMAT_SHOW_TIME
      );
      this.presenter.setDate(dateAndTime.getTimeInMillis());

   }

   // установка обработчика выбора времени
   TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
      public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
         dateAndTime.set(java.util.Calendar.HOUR_OF_DAY, hourOfDay);
         dateAndTime.set(java.util.Calendar.MINUTE, minute);
         setInitialDateTime();
      }
   };

   // установка обработчика выбора даты
   DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
      public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
         dateAndTime.set(java.util.Calendar.YEAR, year);
         dateAndTime.set(java.util.Calendar.MONTH, monthOfYear);
         dateAndTime.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth);
         setInitialDateTime();
      }
   };
}