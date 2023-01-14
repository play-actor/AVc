package com.hfad.avc.data.database

import androidx.room.*
import com.hfad.avc.data.model.Contact

@Dao
interface ContactDao {
   @Query("SELECT * FROM contact")
   fun all(): List<Contact>

   @Query("SELECT * FROM contact WHERE (favorite != 0)  & (date_congratulations <= :secondsDeadLine)")
   fun getComingCongratulations(secondsDeadLine: Long): List<Contact>

   @Query("SELECT * FROM contact WHERE (worked != 0)")
   fun getAllCongratulations(): List<Contact>

   @Query("SELECT * FROM contact WHERE (favorite != 0)")
   fun getFavoriteContact(): List<Contact>

   @Query("SELECT * FROM contact WHERE id = :id")
   fun getById(id: String): Contact

   @Insert
   fun insert(contact: Contact)

   @Update
   fun update(contact: Contact)

   @Delete
   fun delete(contact: Contact)
}