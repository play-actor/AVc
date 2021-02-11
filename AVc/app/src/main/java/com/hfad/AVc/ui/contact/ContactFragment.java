package com.hfad.avc.ui.contact;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hfad.avc.Applications;
import com.hfad.avc.R;
import com.hfad.avc.databinding.FragmentContactBinding;
import com.hfad.avc.ui.MainActivity;
import com.hfad.avc.ui.database.AVcDatabaseHelper;
import com.hfad.avc.ui.database.Contact;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class ContactFragment extends Fragment {

    //public static final Integer CONGRATULATIONS = 0;
    private CheckBox favorite;
    private TextView name;
    private TextView description;
    private TextView ID_in_base;
    private EditText date_congratulations;
    private String TAG = "AVc";
    private FragmentContactBinding binding;

    private final CompositeDisposable disposable = new CompositeDisposable();

    public static ContactFragment newInstance(Integer congratulations) {
        ContactFragment contactFragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putInt("congratulations", congratulations);
        contactFragment.setArguments(args);
        return contactFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_contact, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact,container,false);
        /*this.favorite = view.findViewById(R.id.favorite);
        this.name = view.findViewById(R.id.name);
        this.description = view.findViewById(R.id.description);
        this.date_congratulations = view.findViewById(R.id.date_congratulations);
        this.ID_in_base = view.findViewById(R.id.ID_in_base);
        view.findViewById(R.id.buttonAdd).setOnClickListener(v -> onClickSave());*/
        //возврат на уровень назад
        /*Toolbar toolbar = view.findViewById(R.id.toolbar2);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());*/

        return binding.getRoot();
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Contact contact =new Contact();

        //FragmentContactBinding binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_contact);



/*        this.favorite.setOnClickListener(v -> onFavoriteClicked());
        this.date_congratulations.setOnClickListener(v -> onDateClicked());*/

        //Получение контакта из интента
        if (getArguments() != null) {
            int contactId = getArguments().getInt("congratulations", -1);

            //Создание курсора
            SQLiteOpenHelper AVcDatabaseHelper = new AVcDatabaseHelper(Applications.INSTANCE);
            try {
                //Получение ссылки на базу данных
                SQLiteDatabase db = AVcDatabaseHelper.getReadableDatabase();
                //Создаем курсор для получения названия, описания и идентификатора ресурса выбранного
                //пользователем напитка.
                Cursor cursor = db.query("CONTACT_TABLE",
                        new String[]{"NAME", "PHONE", "ACTIVATE", "DATE_CONGRATULATIONS", "_id"},
                        "_id = ?",
                        new String[]{Integer.toString(contactId)},
                        null, null, null);
                //Переход к первой записи в курсоре
                //moveToFirst - возвращает true, если запись успешно найдена
                //Log.d(TAG, String.valueOf(db.getVersion()));

                if (cursor.moveToFirst()) {
                    /**
                     * Получение данных напитка из курсора и расфасовка их по переменным
                     *
                     * Название напитка хранится в первом столбце курсора, описание — во втором, а
                     * идентификатор ресурса изображения — в третьем. Вспомните, что столбцы NAME,
                     * DESCRIPTION и IMAGE_RESOURCE_ID базы данных были включены в курсор именно
                     * в таком порядке
                     */
                    String nameText = cursor.getString(0);
                    String descriptionText = cursor.getString(1);
                    String dateCongratulations = cursor.getString(3);
                    String new_text = cursor.getString(4);

                    //Если столбец FAVORITE содержит 1, это соответствуетзначению true
                    boolean isFavorite = (cursor.getInt(2) == 1);

                    //Заполнение названия напитка
/*                    this.name.setText("Имя: " + nameText);

                    //Заполнение описания напитка
                    this.description.setText("Номер: " + descriptionText);

                    //Заполнение флажка поздравления
                    this.favorite.setChecked(isFavorite);

                    //Заполнение даты поздравления
                    this.date_congratulations.setText(dateCongratulations);

                    //Заполнение даты поздравления
                    this.ID_in_base.setText("id: " + new_text);*/

                    contact.setId(new_text);
                    contact.setName(nameText);
                    contact.setPhone(descriptionText);
                    contact.setDateCongratulations(dateCongratulations);
                    contact.setFavorite(isFavorite);
                }
                binding.setContactDetail(contact);
                //Эти строки закрывают курсор и базу данных.
                cursor.close();
                db.close();


            } catch (SQLiteException e) {//При выдаче исключенияSQLiteException выводится уведомление.
                Snackbar.make(requireView(), "C базой данных возникли проблемы", BaseTransientBottomBar.LENGTH_LONG).show();
                //Если выдается исключение SQLiteException,значит, с базой данных возникли проблемы.
                //В этом случае уведомление используется для вывода сообщения для пользователя.
                //toast.show();
            }
        }
    }

    //Обновление базы данных по щелчку на флажке


    private void onClickSave() {
        Observable.just(requireArguments().getInt("congratulations", -1))
                .subscribeOn(Schedulers.io())
                .flatMapCompletable(id -> {
                    Applications.INSTANCE.getAVcDatabaseHelper().updateContact(
                            this.favorite.isChecked(),
                            this.date_congratulations.getText().toString(),
                            id
                    );
                    return Completable.complete();
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    new AlertDialog.Builder(requireActivity())
                            .setTitle("Запись")
                            .setMessage("Успешно")
                            .setPositiveButton("Ок", null)
                            .show();
                }, throwable -> {
                    new AlertDialog.Builder(requireActivity())
                            .setTitle("Запись")
                            .setMessage("Ошибка " + throwable.getCause())
                            .setPositiveButton("Ок", null)
                            .show();
                });

    }




/*    //Реализация AsyncTask добавляется в активность в виде внутреннего класса.
    private class UpdateTask extends AsyncTask<Integer, Void, Boolean> {

        private ContentValues activateValues;
        private ContentValues dateValues;

        */

    /**
     * Мы определили drinkValues как приватную  переменную, так как она используется только
     * в методах onExecute() и doInBackground().
     *//*
        protected void onPreExecute() {
            //Перед выполнением кода базы данных значение флажка помещается в объект
            //drinkValues типа ContentValues.

            activateValues = new ContentValues();
            //Прежде чем выполнять код базы данных, значение флажка favorite помещается в объект
            // drinkValues типа ContentValues.
            activateValues.put("ACTIVATE", favorite.isChecked());
            dateValues = new ContentValues();
            dateValues.put("DATE_CONGRATULATIONS", date_congratulations.getText().toString());
        }
        //Код базы данных содержится в методе doInBackground() и выполняется в фоновом потоке.

        protected Boolean doInBackground(Integer... contacts) {
            int contactId = contacts[0];
            SQLiteOpenHelper AVcDatabaseHelper =
                    Applications.INSTANCE.getAVcDatabaseHelper();
            try {
                SQLiteDatabase db = AVcDatabaseHelper.getWritableDatabase();
                db.update("CONTACT_TABLE", activateValues,//Обновление значениястолбца ACTIVATE.
                        "_id= ?", new String[]{Integer.toString(contactId)});
                db.update("CONTACT_TABLE", dateValues,//Обновление значениястолбца FAVORITE.
                        "_id= ?", new String[]{Integer.toString(contactId)});
                db.close();
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        //После выполнения кода базы данных в фоновом режиме следует проверить, успешно ли он
        //был выполнен. Если при выполнении произошла ошибка, выводится сообщение об ошибке.
        protected void onPostExecute(Boolean success) {
            if (!success) {
                *//*Toast toast = Toast.makeText(ContactActivity.this,
                        "Database unavailable", Toast.LENGTH_SHORT);
                //Код вывода сообщения включается в метод onPostExecute(), так как он должен
                //выполняться в основном потоке событий для обновления экрана.
                toast.show();*//*
                Snackbar.make(getActivity().findViewById(R.id.fragContact), "C базой данных возникли проблемы", BaseTransientBottomBar.LENGTH_LONG).show();
            }
        }
    }*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposable.clear();
    }
}