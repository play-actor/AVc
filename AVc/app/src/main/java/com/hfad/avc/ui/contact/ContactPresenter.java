package com.hfad.avc.ui.contact;

import android.os.Bundle;
import android.util.Log;

import com.hfad.avc.Applications;
import com.hfad.avc.ui.database.AppDatabase;
import com.hfad.avc.ui.database.Contact;
import com.hfad.avc.ui.database.Template;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class ContactPresenter extends MvpPresenter<IContactViewModel> {

    private AppDatabase db;
    private Contact contact;
    private Template template;
    private String contactId;


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

    /**
     * @SuppressLint("SetTextI18n") TimePickerDialog timePicker = new TimePickerDialog(context, (view1, hourOfDay, minute) -> {
     * DateTime time = new DateTime()
     * .withYearOfCentury(today.getYearOfCentury())
     * .withMonthOfYear(today.getMonthOfYear())
     * .withDayOfMonth(today.getDayOfMonth())
     * .withHourOfDay(hourOfDay)
     * .withMinuteOfHour(minute);
     * switch (type) {
     * case 0:
     * this.orderSelected.setTimeMaster(String.valueOf(FORMATTER_TIME_DATE2.print(time)));
     * break;
     * case 1:
     * this.orderSelected.setTime(String.valueOf(FORMATTER_TIME_DATE2.print(time)));
     * break;
     * }
     * }, 10, 0, true);
     * timePicker.show();
     */

    public void updateDBonContact() {
        if (this.contact.getDate_congratulationsString() != null & this.contact.getFavorite()) {
            this.db.contactDao().update(this.contact);
            getViewState().setWorker(this.contact.getName(), this.contact.getPhone(), this.contact.getDate_congratulationsString(), this.template.getTextTemplate());
        } else {
            Log.i("AVc", "not yet");
        }
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
