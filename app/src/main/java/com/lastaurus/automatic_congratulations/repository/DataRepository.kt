package com.lastaurus.automatic_congratulations.repository

import com.lastaurus.automatic_congratulations.data.database.AppDatabase
import com.lastaurus.automatic_congratulations.data.model.Congratulation
import com.lastaurus.automatic_congratulations.data.model.Contact
import com.lastaurus.automatic_congratulations.data.model.Template
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataRepository @Inject constructor(var db: AppDatabase) {

   fun getTemplateList(): Flow<List<Template>> {
      return db.templateDao().all
   }

   fun getFavoriteTemplateList(): Flow<List<Template>> {
      return db.templateDao().favorite
   }

   fun updateTemplateDB(template: Template) {
      db.templateDao().update(template)
   }

   fun upsertTemplateDB(template: Template) {
      db.templateDao().upsert(template)
   }

   fun getTemplateById(id: Int?): Template? {
      return id?.let { db.templateDao().getById(it) }
   }

   fun getTemplateListSize(): Int {
      return db.templateDao().size
   }

   fun getContactList(): Flow<List<Contact>> {
      return db.contactDao().all
   }

   fun getCongratulationsList(): Flow<List<Congratulation>> {
      return db.congratulationsDao().all()
   }

   fun getActiveCongratulationsList(): Flow<List<Congratulation>> {
      return db.congratulationsDao().active()
   }

   fun getFavoriteContactList(): Flow<List<Contact>> {
      return db.contactDao().favorite
   }

   fun updateContactDB(contact: Contact) {
      db.contactDao().update(contact)
   }

   fun upsertContactDB(contact: Contact) {
      db.contactDao().upsert(contact)
   }

   fun getContactById(id: Int?): Contact? {
      return id?.let { db.contactDao().getById(it) }
   }

   fun getContactPhoneListById(id: Int?): ArrayList<String>? {
      return id?.let { db.contactDao().getById(it)?.getPhoneList() }
   }

   fun getContactByName(id: String?): Contact? {
      return id?.let { db.contactDao().getByName(it) }
   }

   fun getContactListSize(): Int {
      return db.contactDao().size
   }

   fun getCongratulationListSize(): Int {
      return db.congratulationsDao().size
   }

   fun updateCongratulationDB(congratulation: Congratulation) {
      db.congratulationsDao().update(congratulation)
   }

   fun upsertCongratulationDB(congratulation: Congratulation) {
      db.congratulationsDao().upsert(congratulation)
   }

   fun getCongratulationById(id: Int?): Congratulation? {
      return id?.let { db.congratulationsDao().getById(it) }
   }

   fun getPhoneListFromContact(id: Int): ArrayList<String>? {
      return db.contactDao().getById(id)?.getPhoneList()
   }
}