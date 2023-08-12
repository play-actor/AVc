package com.lastaurus.automatic_congratulations.data.database

import androidx.room.*
import com.lastaurus.automatic_congratulations.data.model.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
//   @Query("SELECT * FROM contact WHERE (favorite != 0)  & (date_congratulations <= :secondsDeadLine)")
//   fun getComingCongratulations(secondsDeadLine: Long): List<Contact>

//   @Query("SELECT * FROM contact WHERE (worked != 0)")
//   fun getAllCongratulations(): List<Contact>

   @get:Query("SELECT * FROM contact")
   val all: Flow<List<Contact>>


   @get:Query("SELECT * FROM contact WHERE (favorite != 0)")
   val favorite: Flow<List<Contact>>

   @Query("SELECT * FROM contact WHERE id = :id")
   fun getById(id: Int): Contact?

   @Query("SELECT * FROM contact WHERE name = :name")
   fun getByName(name: String): Contact?

   @get:Query("SELECT COUNT(*) FROM contact")
   val size: Int

   @Query("SELECT * FROM contact WHERE name LIKE '%' || :searchText || '%'")
   fun searchContact(searchText: String): Flow<List<Contact>>

   @Insert
   fun insert(contact: Contact)

   @Update
   fun update(contact: Contact)

   @Upsert
   fun upsert(contact: Contact)

   @Delete
   fun delete(contact: Contact)
}