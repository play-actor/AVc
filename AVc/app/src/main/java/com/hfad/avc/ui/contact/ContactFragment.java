package com.hfad.avc.ui.contact;

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
import android.widget.TimePicker;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.work.Data;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hfad.avc.Applications;
import com.hfad.avc.R;
import com.hfad.avc.databinding.FragmentContactBinding;
import com.hfad.avc.interactor.LoadDBInteractor;
import com.hfad.avc.ui.database.Contact;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;


public class ContactFragment extends MvpAppCompatFragment implements IContactViewModel {
   LoadDBInteractor interactorLoad;
   @InjectPresenter
   ContactPresenter presenter;
   private String TAG = "AVc";
   Data data = null;
   Date thisDateCon;
   public CoordinatorLayout coordLayout;
   Date myTime = java.util.Calendar.getInstance().getTime();
   private FragmentContactBinding binding;
   public static final String PERMISSION_STRING = Manifest.permission.SEND_SMS;
   private final int PERMISSION_REQUEST_CODE = 1118;
   SimpleDateFormat format = new SimpleDateFormat();
   java.util.Calendar dateAndTime = java.util.Calendar.getInstance();

   public static ContactFragment newInstance(Integer congratulations) {
      ContactFragment contactFragment = new ContactFragment();
      Bundle args = new Bundle();
      args.putInt("congratulations", congratulations);
      contactFragment.setArguments(args);
      return contactFragment;
   }

   @ProvidePresenter
   ContactPresenter ProvidePresenterContactPresenter() {
      return new ContactPresenter(getArguments());
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false);
      return binding.getRoot();
   }


   @Override
   public void setData(Contact contact) {
      this.binding.setContactDetail(contact);
      this.binding.setPresenter(this.presenter);
   }

   @Override
   public void setWorker(String name, String phoneNumber, String dateCon, String getTextTemplate) {
      this.interactorLoad = Applications.INSTANCE.getHelperInteractors().getContactInteractor();
      if (ContextCompat.checkSelfPermission(getActivity(), PERMISSION_STRING)
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
         long seconds = ((thisDateCon.getTime() - myTime.getTime()) / (1000));
         if (seconds < 0) {
            seconds += 31536000;
         }
         if (this.binding.favorite.isChecked()) {
            this.interactorLoad.myWork(data, seconds)
                  .subscribeOn(Schedulers.computation())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(
                        () -> Log.i(TAG, "Worker создан"),
                        throwable -> Log.e(TAG, "Worker error", (Throwable) throwable)
                  );
/*                    myWorkRequest = new PeriodicWorkRequest.Builder(SendWorker.class, 31536000, TimeUnit.MINUTES)
                            .setInputData(data)
                            .setInitialDelay(10, TimeUnit.SECONDS)
                            .build();
                WorkManager.getInstance(Applications.INSTANCE).enqueue(myWorkRequest);*/
            coordLayout = getActivity().findViewById(R.id.mainFragApp);
            Snackbar.make(coordLayout, "Сохранено", BaseTransientBottomBar.LENGTH_LONG).show();
         }
      } else {
         ActivityCompat.requestPermissions(getActivity(),
               new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
      }
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
      String d = DateUtils.formatDateTime(getActivity(),
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