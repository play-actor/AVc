package com.hfad.avc.ui.contact;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.hfad.avc.Applications;
import com.hfad.avc.R;
import com.hfad.avc.databinding.FragmentContactBinding;
import com.hfad.avc.interactor.SendIteractor;
import com.hfad.avc.ui.SendWorker;
import com.hfad.avc.ui.calendar.Calendar;
import com.hfad.avc.ui.calendar.CalendarType;
import com.hfad.avc.ui.database.Contact;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;


public class ContactFragment extends MvpAppCompatFragment implements IContactViewModel {
    PeriodicWorkRequest myWorkRequest;
    @InjectPresenter
    ContactPresenter presenter;
    SendIteractor interactor;
    private String TAG = "AVc";
    Data data = null;
    Date thisDateCon;
    Date myTime = java.util.Calendar.getInstance().getTime();
    private FragmentContactBinding binding;
    public static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("dd.MM.yyyy, HH:mm");


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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false);
        return binding.getRoot();
    }

    @Override
    public void openCalendar() {
        new Calendar(
                CalendarType.SINGLE,
                requireActivity().getSupportFragmentManager(),
                selection -> {
                    this.presenter.setDate(((Long) selection));
//                    ((TextInputLayout) this.binding.getRoot().findViewById(R.id.date_congratulations))
//                            .getEditText()
//                            .setText(FORMATTER.print(((Long) selection)));
                },
                "времени поздравления"
        );
    }

    @Override
    public void setData(Contact contact) {
        this.binding.setContactDetail(contact);
        this.binding.setPresenter(this.presenter);
    }

    @Override
    public void setWorker(String name, String phoneNumber, String dateCon, String getTextTemplate) {

        data = new Data.Builder()
                .putString("Name", name)
                .putString("Phone", phoneNumber)
                .putString("TextTemplate", getTextTemplate)
                .build();
        Log.i(TAG, String.valueOf(data));
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        try {
            thisDateCon = format.parse(dateCon);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long seconds = (thisDateCon.getTime() - myTime.getTime()) / (100);
        Log.i(TAG, "----------------------------------------------------------");
        Log.i(TAG, "Дата поздравления: " + String.valueOf(thisDateCon));
        Log.i(TAG, "Текущая дата: " + String.valueOf(myTime));
        Log.i(TAG, "----------------------------------------------------------");
        Log.i(TAG, "Разница между датами в секундах: " + String.valueOf(seconds));
        Log.i(TAG, "----------------------------------------------------------");

      //  this.interactor = Applications.INSTANCE.getHelperInteractors().getSendIteractor();
        /*this.interactor.smsSend(Applications.INSTANCE,inputData.getString("Phone"),inputData.getString("TextTemplate"));*/
       // this.interactor.smsSend(getActivity(), phoneNumber, getTextTemplate);
       // this.interactor.smsSend(Applications.INSTANCE, "89041100764", "getTextTemplate");

        if (this.binding.favorite.isChecked()) {
            myWorkRequest = new PeriodicWorkRequest.Builder(SendWorker.class, 525600, TimeUnit.MINUTES)
                    .setInputData(data)
                    .setInitialDelay(10, TimeUnit.SECONDS)
                    .build();
            WorkManager.getInstance(Applications.INSTANCE).enqueue(myWorkRequest);
            Log.i(TAG, "Задача создана");
        }
    }
}