package com.hfad.avc.data.database

import androidx.room.*
import com.hfad.avc.data.model.Event

@Dao
interface EventDao {
   @Query("SELECT * FROM event")
   fun all(): List<Event>

   @Query("SELECT * FROM event WHERE (worked != 0)")
   fun getActiveEvent(): List<Event>

   @Query("SELECT * FROM event WHERE id = :id")
   fun getById(id: String): Event

   @Insert
   fun insert(event: Event)

   @Update
   fun update(event: Event)

   @Upsert
   fun upsert(event: Event)

   @Delete
   fun delete(event: Event)
}