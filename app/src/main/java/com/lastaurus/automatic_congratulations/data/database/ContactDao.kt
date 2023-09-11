package com.lastaurus.automatic_congratulations.data.database

import androidx.room.*
import com.lastaurus.automatic_congratulations.data.model.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

   @get:Query("SELECT * FROM contact ORDER BY name")
   val all: Flow<List<Contact>>

   @get:Query("SELECT * FROM contact ORDER BY name DESC")
   val allDESC: Flow<List<Contact>>


   @get:Query("SELECT * FROM contact WHERE (favorite != 0) ORDER BY name")
   val favorite: Flow<List<Contact>>

   @get:Query("SELECT * FROM contact WHERE (favorite != 0) ORDER BY name DESC")
   val favoriteDESC: Flow<List<Contact>>

   @Query("SELECT * FROM contact WHERE id = :id")
   fun getById(id: Int): Contact?

   @Query("SELECT * FROM contact WHERE idInBase = :idInBase")
   fun getByIdInBase(idInBase: String): Contact?

   @Query("SELECT COUNT(*)>0 FROM contact WHERE idInBase = :idInBase")
   fun contactInBase(idInBase: String): Boolean

   @Query("SELECT * FROM contact WHERE name = :name")
   fun getByName(name: String): Contact?

   @get:Query("SELECT COUNT(*) FROM contact")
   val size: Int

   @Query("SELECT * FROM contact WHERE name LIKE '%' || :searchText || '%' ORDER BY name")
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