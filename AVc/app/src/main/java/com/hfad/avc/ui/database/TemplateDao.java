package com.hfad.avc.ui.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface TemplateDao {
    @Query("SELECT * FROM template")
    List<Template> getAll();

    @Query("SELECT * FROM template WHERE id = :id")
    Template getById(String id);

    @Query("SELECT COUNT(*) FROM template")
    String getSize();

    @Insert
    void insert(Template template);

    @Update
    void update(Template template);

    @Delete
    void delete(Template template);
}
