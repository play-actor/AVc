package com.hfad.avc.ui.contact;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.hfad.avc.Applications;
import com.hfad.avc.ui.database.AppDatabase;
import com.hfad.avc.ui.database.Contact;
import com.hfad.avc.ui.database.Template;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class ContactPresenter extends MvpPresenter<IContactViewModel> {

    private AppDatabase db;
    private Contact contact;
    private Template template;
    private String contactId;
    TextView currentDateTime;
    Calendar dateAndTime=Calendar.getInstance();


    public ContactPresenter(Bundle bundle) {
        contactId = String.valueOf(bundle.getInt("congratulations", -1));
        db = Applications.getInstance().getDatabase();
        contact = db.contactDao().getById(contactId);
        template = db.templateDao().getById(String.valueOf(rnd()));

        getViewState().setData(contact);
    }

    public static int rnd() {
        int min = 0; // Минимальное число для диапазона
        int max = 13; // Максимальное число для диапазона
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public void updateDBonContact() {
        if (this.contact.getDate_congratulationsString() != null & this.contact.getFavorite()) {
            this.db.contactDao().update(this.contact);
            getViewState().setWorker(this.contact.getName(), this.contact.getPhone(), this.contact.getDate_congratulationsString(), this.template.getTextTemplate());
        } else {
            Log.i("AVc", "not yet");
        }
    }


    public void setNewDate() {
        getViewState().setDateNew();
    }
    public void setNewTime() {
        getViewState().setTimeNew();
    }


    public static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("dd.MM.yyyy, HH:mm");

    public void setDate(Long selection) {
        this.contact.setDate_congratulations(selection);
        Log.i("AVc", String.valueOf(selection));
        this.contact.setDate_congratulationsString(FORMATTER.print(selection));
        Log.i("AVc", String.valueOf(FORMATTER.print(selection)));
    }
}
