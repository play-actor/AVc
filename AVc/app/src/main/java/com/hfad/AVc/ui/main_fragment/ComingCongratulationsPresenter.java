package com.hfad.avc.ui.main_fragment;

import com.hfad.avc.Applications;
import com.hfad.avc.ui.database.AppDatabase;
import com.hfad.avc.ui.database.Contact;
import java.util.ArrayList;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class ComingCongratulationsPresenter extends MvpPresenter<IComingCongratulationsViewModel> {

    private AppDatabase db;
    private ArrayList<Contact> contactsList;

    public ComingCongratulationsPresenter() {
        db = Applications.getInstance().getDatabase();
        contactsList = (ArrayList<Contact>) db.contactDao().getComingCongratulations();
        getViewState().setData(contactsList);
    }
}