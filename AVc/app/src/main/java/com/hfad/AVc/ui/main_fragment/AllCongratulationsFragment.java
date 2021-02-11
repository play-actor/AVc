package com.hfad.avc.ui.main_fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.hfad.avc.Applications;
import com.hfad.avc.R;
import com.hfad.avc.ui.contact.ContactFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllCongratulationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllCongratulationsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ShareActionProvider shareActionProvider;
    private SQLiteDatabase db;
    private Cursor favoritesCursor;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listFavorites;
    private CursorAdapter favoriteAdapter;

    public AllCongratulationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllCongratulationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllCongratulationsFragment newInstance(String param1, String param2) {
        AllCongratulationsFragment fragment = new AllCongratulationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_congratulations, container, false);

        this.listFavorites = view.findViewById(R.id.list_favorites);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupFavoritesListView();

    }

    private void setupFavoritesListView() {
        //Заполнение списка list_favorites по данным курсора
        try{
            SQLiteOpenHelper AVcDatabaseHelper = Applications.INSTANCE.getAVcDatabaseHelper();
            db = AVcDatabaseHelper.getReadableDatabase();
            favoritesCursor = db.query("CONTACT_TABLE",
                    new String[] { "_id", "NAME"},
                    "ACTIVATE = 1",
                    null, null, null, null);
            this.favoriteAdapter =
                    new SimpleCursorAdapter(requireActivity(),
                            android.R.layout.simple_list_item_1,
                            //Использовать курсор в адаптере курсора.
                            favoritesCursor,
                            //Вывести названия напитков в списковом представлении.
                            new String[]{"NAME"},
                            new int[]{android.R.id.text1}, 0);
            listFavorites.setAdapter(favoriteAdapter);
        } catch(SQLiteException e) {
            Log.e("БД", "Database unavailable");
            /*Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();*/
        }
        //Переход к DrinkActivity при выборе напитка
        //Списковое представление list_favorites реагирует на щелчки.
        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View v, int position, long id) {
                /*Intent intent = new Intent(requireActivity(), ContactFragment.class);
                //Запустить DrinkActivity и передать идентификатор выбранного напитка.
                intent.put(ContactFragment., (int)id);
                startActivity(intent);*/

                ContactFragment contactFragment = ContactFragment.newInstance((int) id);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.root, contactFragment, "contactFragment")
                        .show(contactFragment)
                        .addToBackStack("contactFragment")
                        .commitAllowingStateLoss();
                /*getFragmentManager().beginTransaction()
                        .replace(R.id.root, contactFragment, "ContactFragment")
                        .addToBackStack("ContactFragment")
                        .commit();*/
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Cursor newCursor = db.query("CONTACT_TABLE",
                new String[] { "_id", "NAME"},
                "ACTIVATE = 1",
                null, null, null, null);
        //Для получения адаптера ListView используется метод getAdapter().
        //Курсор, используемый list_favorites,заменяется новым курсором.
        this.favoriteAdapter.changeCursor(newCursor);
        //Значение favoritesCursor заменяется новым курсором, чтобы его можно было закрыть в методе
        //onDestroy() активности.
        favoritesCursor = newCursor;
    }


}