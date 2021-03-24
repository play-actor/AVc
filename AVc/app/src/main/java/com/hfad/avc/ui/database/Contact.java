package com.hfad.avc.ui.database;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import javax.inject.Inject;

@Entity()
public class Contact extends BaseObservable {
    @PrimaryKey
    @NonNull
    private String id;
    private String name = "";
    private String phone = "";
    private long date_congratulations;
    private String date_congratulationsString;
    private Boolean favorite = false;

    @Inject
    public Contact(){};

    @Bindable
    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getId() {
        return id;
    }

    @Bindable
    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
        notifyPropertyChanged(BR.favorite);
    }

    @Bindable
    public long getDate_congratulations() {
        return date_congratulations;
    }

    public static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("dd.MM.yyyy, HH:mm");

    public void setDate_congratulations(long date_congratulations) {
        this.date_congratulations = date_congratulations;
        this.date_congratulationsString = FORMATTER.print(date_congratulations);
    }

    @Bindable
    public String getDate_congratulationsString() {
        return date_congratulationsString;
    }

    public void setDate_congratulationsString(String date) {
        this.date_congratulationsString = date;
        notifyPropertyChanged(BR.date_congratulationsString);
    }

    @Override
    public String toString() {
        DateTime someDate = new DateTime(this.date_congratulations, DateTimeZone.UTC);
        return "id = " + this.id +
                ", Имя : " + this.name +
                ", Телефон : " + this.phone +
                ", Дата : " + someDate +
                ", Дата2 : " + this.date_congratulations +
                ", Поздравляется : " + this.favorite;
    }
}
