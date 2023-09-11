package com.lastaurus.automatic_congratulations.data.database

import androidx.room.*
import com.lastaurus.automatic_congratulations.data.model.Congratulation
import kotlinx.coroutines.flow.Flow

@Dao
interface CongratulationsDao {
   @get:Query("SELECT * FROM congratulation")
   val all: Flow<List<Congratulation>>

   @get:Query("SELECT * FROM congratulation")
   val allDESC: Flow<List<Congratulation>>

   @get:Query("SELECT * FROM congratulation WHERE (active != 0)")
   val active: Flow<List<Congratulation>>

   @get:Query("SELECT * FROM congratulation WHERE (active != 0)")
   val activeDESC: Flow<List<Congratulation>>

   @Query("SELECT * FROM congratulation WHERE id = :id")
   fun getById(id: Int): Congratulation?

   @get:Query("SELECT COUNT(*) FROM congratulation")
   val size: Int

   @Query("SELECT * FROM congratulation WHERE ABS(dateTimeFuture>=:millis) limit 1")
   fun targetCongratulation(millis: Long): Congratulation?

   @Query("SELECT * FROM congratulation LEFT JOIN contact ON congratulation.idContact = contact.id WHERE contact.name LIKE '%' || :searchText || '%' ORDER BY contact.name")
   fun searchCongratulation(searchText: String): Flow<List<Congratulation>>

   @Insert
   fun insert(event: Congratulation)

   @Update
   fun update(event: Congratulation)

   @Upsert
   fun upsert(event: Congratulation)

   @Delete
   fun delete(event: Congratulation)
}