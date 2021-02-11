package com.hfad.avc.ui.namelist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hfad.avc.Applications;
import com.hfad.avc.R;
import com.hfad.avc.ui.contact.ContactFragment;
import com.hfad.avc.ui.database.Contact;

import java.util.ArrayList;

public class NameListFragment extends Fragment {
    private String TAG = "AVc";
    private SQLiteDatabase db;
    private Cursor cursor;
    //private ListView listDrinks;
    private RecyclerView recyclerView;
    //private AdapterView.OnItemClickListener itemClickListener;
    //private ListAdapter adapter;
    private ArrayList<Contact> contactsList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id = 0;
        //Получить ссылку на базу данных и создать курсор
        SQLiteOpenHelper AVcDatabaseHelper = Applications.INSTANCE.getAVcDatabaseHelper();
        try {
            this.db = AVcDatabaseHelper.getReadableDatabase();
            this.cursor = this.db.query("CONTACT_TABLE",
                    new String[]{"_id", "NAME", "PHONE"},
                    null, null, null, null, null);


            this.cursor.moveToFirst();
            id = this.cursor.getColumnIndex("_id");
            while (this.cursor.moveToNext()) {
                Contact contact = new Contact();
                contact.setId(this.cursor.getString(id));
                contact.setName(this.cursor.getString(this.cursor.getColumnIndex("NAME")));
                contact.setPhone(this.cursor.getString(this.cursor.getColumnIndex("PHONE")));
                this.contactsList.add(contact);
                Log.i(TAG, contact.getName());
            }

        } catch (SQLiteException e) {
            e.printStackTrace();
            Snackbar.make(requireView(), "База данных не найдена", BaseTransientBottomBar.LENGTH_LONG).show();
        }


        //Создание слушателя
        //this.itemClickListener = (listDrinks1, itemView, position, id)
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_name_list, container, false);
        this.recyclerView = inflate.findViewById(R.id.list);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.list);
        // создаем адаптер
        ListAdapter adapter = new ListAdapter(contactsList);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        //Назначение слушателя для спискового представления
        adapter.setClick(id -> {
            ContactFragment contactFragment = ContactFragment.newInstance(id);
            getFragmentManager().beginTransaction()
                    .replace(R.id.root, contactFragment, "ContactFragment")
                    .addToBackStack("ContactFragment")
                    .commit();
            /* Log.i("Важно", String.valueOf(id));*/
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.cursor.close();
        this.db.close();
    }
}