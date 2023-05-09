package com.lastaurus.automatic_congratulations.data.database

import androidx.room.*
import com.lastaurus.automatic_congratulations.data.model.Template
import kotlinx.coroutines.flow.Flow

@Dao
interface TemplateDao {
   @get:Query("SELECT * FROM template")
   val all: Flow<List<Template>>

   @get:Query("SELECT * FROM template WHERE (favorite != 0)")
   val favorite: Flow<List<Template>>

   @Query("SELECT * FROM template WHERE id = :id")
   fun getById(id: Int): Template?

   @get:Query("SELECT COUNT(*) FROM template")
   val size: Int

   @Insert
   fun insert(template: Template)

   @Update
   fun update(template: Template)

   @Upsert
   fun upsert(template: Template)

   @Delete
   fun delete(template: Template)
}