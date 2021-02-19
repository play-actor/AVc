package com.hfad.avc.ui.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hfad.avc.Applications;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact")
    List<Contact> getAll();

    @Query("SELECT * FROM contact WHERE (favorite != 0)")
    List<Contact> getComingCongratulations();

    @Query("SELECT * FROM contact WHERE favorite != 0")
    List<Contact> getAllCongratulations();

    @Query("SELECT * FROM contact WHERE id = :id")
    Contact getById(String id);

    @Insert
    void insert(Contact contact);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);



}
