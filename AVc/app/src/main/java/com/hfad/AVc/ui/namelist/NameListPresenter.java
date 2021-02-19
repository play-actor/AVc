package com.hfad.avc.ui.namelist;

import com.hfad.avc.Applications;
import com.hfad.avc.ui.database.AppDatabase;
import com.hfad.avc.ui.database.Contact;
import java.util.ArrayList;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class NameListPresenter extends MvpPresenter<INameListViewModel> {

    private AppDatabase db;
    private ArrayList<Contact> contactsList;

    public NameListPresenter() {
        db = Applications.getInstance().getDatabase();
        contactsList = (ArrayList<Contact>) db.contactDao().getAll();
        getViewState().setData(contactsList);
    }
}
