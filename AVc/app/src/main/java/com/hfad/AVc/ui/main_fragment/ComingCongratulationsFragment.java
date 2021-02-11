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
import android.widget.Toast;

import com.hfad.avc.Applications;
import com.hfad.avc.R;
import com.hfad.avc.ui.contact.ContactFragment;


public class ComingCongratulationsFragment extends Fragment {

    private ShareActionProvider shareActionProvider;
    private SQLiteDatabase db;
    private Cursor topFavoritesCursor;
    private ListView listFavoritesComing;
    private CursorAdapter favoriteComingAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coming_congratulations, container, false);
        this.listFavoritesComing = view.findViewById(R.id.list_favorites_coming);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupComingFavoritesListView();

    }


    private void setupComingFavoritesListView() {
        //Заполнение списка list_favorites по данным курсора
        try{
            SQLiteOpenHelper AVcDatabaseHelper = Applications.INSTANCE.getAVcDatabaseHelper();
            db = AVcDatabaseHelper.getReadableDatabase();
            topFavoritesCursor = db.query("CONTACT_TABLE",
                    new String[] { "_id", "NAME","DATE_CONGRATULATIONS"},
                    "ACTIVATE = 1 AND DATE_CONGRATULATIONS = 0",
                    null, null, null, null);
            this.favoriteComingAdapter =
                    new SimpleCursorAdapter(requireActivity(),
                            android.R.layout.simple_list_item_1,
                            //Использовать курсор в адаптере курсора.
                            topFavoritesCursor,
                            //Вывести названия напитков в списковом представлении.
                            new String[]{"NAME"},
                            new int[]{android.R.id.text1}, 0);
            listFavoritesComing.setAdapter(favoriteComingAdapter);
        } catch(SQLiteException e) {
            Log.e("БД", "Database unavailable");
            Toast toast = Toast.makeText(Applications.INSTANCE, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        //Переход к DrinkActivity при выборе напитка
        //Списковое представление list_favorites реагирует на щелчки.
        listFavoritesComing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                new String[] {"_id", "NAME"},
                "ACTIVATE = 1 AND DATE_CONGRATULATIONS = 0",
                null, null, null, null);
        //Для получения адаптера ListView используется метод getAdapter().
        //Курсор, используемый list_favorites,заменяется новым курсором.
        this.favoriteComingAdapter.changeCursor(newCursor);
        //Значение topFavoritesCursor заменяется новым курсором, чтобы его можно было закрыть в методе
        //onDestroy() активности.
        topFavoritesCursor = newCursor;
    }
}