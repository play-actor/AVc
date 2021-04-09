package com.hfad.avc.ui.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact")
    List<Contact> getAll();

    @Query("SELECT * FROM contact WHERE (favorite != 0)  & (date_congratulations <= :secondsDeadLine)")
    List<Contact> getComingCongratulations(long secondsDeadLine);

    @Query("SELECT * FROM contact WHERE (favorite != 0)")
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
