package com.lastaurus.avc.data.database

import androidx.room.*
import com.lastaurus.avc.data.model.Template

@Dao
interface TemplateDao {
   @get:Query("SELECT * FROM template")
   val all: List<Template>

   @get:Query("SELECT * FROM template WHERE (favorite != 0)")
   val favorite: List<Template>

   @Query("SELECT * FROM template WHERE id = :id")
   fun getById(id: String): Template?

   @get:Query("SELECT COUNT(*) FROM template")
   val size: String

   @Insert
   fun insert(template: Template)

   @Update
   fun update(template: Template)

   @Delete
   fun delete(template: Template)
}