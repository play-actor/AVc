package com.lastaurus.automatic_congratulations.data.database

import androidx.room.*
import com.lastaurus.automatic_congratulations.data.model.EventCongratulations

@Dao
interface EventCongratulationsDao {
   @Query("SELECT * FROM eventCongratulations")
   fun getAll(): List<EventCongratulations>

   @Query("SELECT * FROM eventCongratulations WHERE (worked != 0)")
   fun getActiveEvent(): List<EventCongratulations>

   @Query("SELECT * FROM eventCongratulations WHERE id = :id")
   fun getById(id: String): EventCongratulations

   @Insert
   fun insert(event: EventCongratulations)

   @Update
   fun update(event: EventCongratulations)

   @Upsert
   fun upsert(event: EventCongratulations)

   @Delete
   fun delete(event: EventCongratulations)
}