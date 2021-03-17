package com.hfad.avc.ui.database;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.inject.Inject;

@Entity()
public class Template extends BaseObservable {
    @PrimaryKey
    @NonNull
    private String id;
    private String textTemplate = "";
    private Boolean favorite = false;

    @Inject
    public Template() {
    }

    ;

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getTextTemplate() {
        return textTemplate;
    }

    public void setTextTemplate(String textTemplate) {
        this.textTemplate = textTemplate;
        notifyPropertyChanged(BR.textTemplate);
    }

    @Bindable
    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
        notifyPropertyChanged(BR.favorite);
    }

    @Override
    public String toString() {
        return "id = " + this.id +
                ", Текст: " + this.textTemplate +
                ", В избранном: " + this.favorite;
    }

}