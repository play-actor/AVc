package com.lastaurus.automatic_congratulations.data

import com.lastaurus.automatic_congratulations.data.database.AppDatabase
import com.lastaurus.automatic_congratulations.data.model.Contact
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

   fun getContactList(): List<Contact> {
      return db.contactDao().all
   }

   fun getFavoriteContactList(): List<Contact> {
      return db.contactDao().favorite
   }

   fun updateContactDB(template: Contact) {
      db.contactDao().update(template)
   }

   fun upsertContactDB(template: Contact) {
      db.contactDao().upsert(template)
   }

   fun getContactById(id: Int): Contact? {
      return db.contactDao().getById(id)
   }

   fun getContactListSize(): Int {
      return db.contactDao().size
   }

   fun getPhoneListFromContact(id: Int): ArrayList<String>? {
      return db.contactDao().getById(id)?.getPhoneList()
   }
}