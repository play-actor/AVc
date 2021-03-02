package com.hfad.AVc.ui.contact;

import android.os.Bundle;

import com.hfad.AVc.Applications;
import com.hfad.AVc.ui.database.AppDatabase;
import com.hfad.AVc.ui.database.Contact;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class ContactPresenter extends MvpPresenter<IContactViewModel> {

    private AppDatabase db;
    private Contact contact;
    private String contactId;

    public ContactPresenter(Bundle bundle) {
        contactId = String.valueOf(bundle.getInt("congratulations", -1));
        db = Applications.getInstance().getDatabase();
        contact = db.contactDao().getById(contactId);
        getViewState().setData(contact);
    }

    /**
     * @SuppressLint("SetTextI18n")
     *       TimePickerDialog timePicker = new TimePickerDialog(context, (view1, hourOfDay, minute) -> {
     *          DateTime time = new DateTime()
     *                .withYearOfCentury(today.getYearOfCentury())
     *                .withMonthOfYear(today.getMonthOfYear())
     *                .withDayOfMonth(today.getDayOfMonth())
     *                .withHourOfDay(hourOfDay)
     *                .withMinuteOfHour(minute);
     *          switch (type) {
     *             case 0:
     *                this.orderSelected.setTimeMaster(String.valueOf(FORMATTER_TIME_DATE2.print(time)));
     *                break;
     *             case 1:
     *                this.orderSelected.setTime(String.valueOf(FORMATTER_TIME_DATE2.print(time)));
     *                break;
     *          }
     *       }, 10, 0, true);
     *       timePicker.show();
     */

    public void updateDBonContact() {this.db.contactDao().update(this.contact);
        //Applications.INSTANCE.getRouter().backTo();
    }

    public void setDate2() {
        getViewState().openCalendar();
    }

    public static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("dd.MM.yyyy, HH:mm");

    public void setDate(Long selection) {
        this.contact.setDate_congratulations(selection);
        this.contact.setDate_congratulationsString(FORMATTER.print(selection));
    }
}
