package com.lastaurus.automatic_congratulations.data.database

import androidx.room.*
import com.lastaurus.automatic_congratulations.data.model.Template
import kotlinx.coroutines.flow.Flow

@Dao
interface TemplateDao {
   @get:Query("SELECT * FROM template ORDER BY textTemplate")
   val all: Flow<List<Template>>

   @get:Query("SELECT * FROM template WHERE (favorite != 0) ORDER BY textTemplate")
   val favorite: Flow<List<Template>>

   @get:Query("SELECT * FROM template ORDER BY textTemplate DESC")
   val allDESC: Flow<List<Template>>

   @get:Query("SELECT * FROM template WHERE (favorite != 0) ORDER BY textTemplate DESC")
   val favoriteDESC: Flow<List<Template>>

   @Query("SELECT * FROM template WHERE id = :id")
   fun getById(id: Int): Template?

   @get:Query("SELECT COUNT(*) FROM template")
   val size: Int

   @Query("SELECT * FROM template WHERE textTemplate LIKE '%' || :searchText || '%' ORDER BY textTemplate")
   fun searchTemplate(searchText: String): Flow<List<Template>>

   @Insert
   fun insert(template: Template)

   @Update
   fun update(template: Template)

   @Upsert
   fun upsert(template: Template)

   @Delete
   fun delete(template: Template)
}