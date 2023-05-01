package com.lastaurus.automatic_congratulations.data

import com.lastaurus.automatic_congratulations.data.database.AppDatabase
import com.lastaurus.automatic_congratulations.data.model.Template
import javax.inject.Inject

class DataRepository @Inject constructor(var db: AppDatabase) {

   fun getTemplateList(): List<Template> {
      return db.templateDao().all
   }

   fun getFavoriteTemplateList(): List<Template> {
      return db.templateDao().favorite
   }

   fun updateTemplateDB(template: Template) {
      db.templateDao().update(template)
   }

   fun upsertTemplateDB(template: Template) {
      db.templateDao().upsert(template)
   }

   fun getTemplateById(id: Int): Template? {
      return db.templateDao().getById(id)
   }

   fun getTemplateListSize(): Int {
      return db.templateDao().size
   }
}