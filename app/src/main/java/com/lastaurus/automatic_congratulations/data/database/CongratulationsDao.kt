package com.lastaurus.automatic_congratulations.data.database

import androidx.room.*
import com.lastaurus.automatic_congratulations.data.model.Congratulation
import kotlinx.coroutines.flow.Flow

@Dao
interface CongratulationsDao {
   @Query("SELECT * FROM congratulation")
   fun all(): Flow<List<Congratulation>>

   @Query("SELECT * FROM congratulation")
   fun all1(): List<Congratulation>

   @Query("SELECT * FROM congratulation WHERE (active != 0)")
   fun active(): Flow<List<Congratulation>>

   @Query("SELECT * FROM congratulation WHERE id = :id")
   fun getById(id: Int): Congratulation?

   @get:Query("SELECT COUNT(*) FROM congratulation")
   val size: Int

   @Insert
   fun insert(event: Congratulation)

   @Update
   fun update(event: Congratulation)

   @Upsert
   fun upsert(event: Congratulation)

   @Delete
   fun delete(event: Congratulation)
}