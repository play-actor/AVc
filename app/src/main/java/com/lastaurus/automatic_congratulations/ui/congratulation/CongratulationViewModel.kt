package com.lastaurus.automatic_congratulations.ui.congratulation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.lastaurus.automatic_congratulations.bus.BusEvent
import com.lastaurus.automatic_congratulations.bus.EventHandler
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.dagger.module.ImageModule
import com.lastaurus.automatic_congratulations.data.model.Congratulation
import com.lastaurus.automatic_congratulations.data.model.Contact
import com.lastaurus.automatic_congratulations.data.model.Template
import com.lastaurus.automatic_congratulations.util.Util.Companion.getDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import javax.inject.Inject

class CongratulationViewModel : ViewModel() {
   @Inject
   lateinit var congratulationUseCase: CongratulationUseCase

   @Inject
   lateinit var imageModule: ImageModule

   private var congratulation: Congratulation? = null

   @Inject
   lateinit var eventHandler: EventHandler

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   companion object {
      private val FORMATTER_DATE: DateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy")
      private val FORMATTER_TIME: DateTimeFormatter = DateTimeFormat.forPattern("HH:mm")
   }

   fun init(id: Int? = null) {
      id?.apply {
         congratulation = congratulationUseCase.getCongratulation(this)
      }
      if (congratulation == null) {
         congratulation = Congratulation()
         congratulation?.id = getListSize()
      }
   }

   fun getId(): Int {
      return congratulation?.id ?: Int.MIN_VALUE
   }

   private fun getListSize(): Int {
      return congratulationUseCase.getCongratulationListSize()
   }

   fun getNameContact(): String {
      return getNameContact(congratulation?.idContact)
   }

   fun getNameContact(id: Int?): String {
      return congratulationUseCase.getContact(id)?.name ?: ""
   }

   fun getContactPhoneList(): ArrayList<String>? {
      return congratulationUseCase.getContactPhoneList(congratulation?.idContact)
   }

   fun getTextTemplate(): String {
      return getTextTemplate(congratulation?.idTemplate)
   }

   fun getTextTemplate(id: Int?): String {
      return congratulationUseCase.getTemplate(id)?.textTemplate ?: ""
   }

   fun getTextPhone(): String {
      return congratulation?.phone ?: ""
   }

   fun getTextPhone(id: Int?): String {
      return id?.let {
         congratulationUseCase.getContactPhoneList(congratulation?.idContact)?.get(it) ?: ""
      }.toString()
   }

   fun getDate(): String {
      return congratulation?.dateTime?.let { FORMATTER_DATE.print(it) } ?: ""
   }

   fun getTime(): String {
      return (congratulation?.dateTime?.let { FORMATTER_TIME.print(it) } ?: "")
   }

   fun getPeriodic(): Boolean {
      return congratulation?.periodic ?: false
   }

   fun setDateTime(dateTime: Long) {
      congratulation?.dateTime = dateTime
   }

   fun setContact(id: Int) {
      congratulation?.idContact = id
   }

   fun setPhone(text: String) {
      congratulation?.phone = text
   }

   fun setTemplate(id: Int) {
      congratulation?.idTemplate = id
   }

   fun getContactList(): LiveData<List<Contact>> {
      return congratulationUseCase.getContactList().asLiveData()
   }

   fun getTemplateList(): LiveData<List<Template>> {
      return congratulationUseCase.getTemplateList().asLiveData()
   }

   fun getActive(): Boolean? {
      return congratulation?.active
   }

   fun setPeriodic(periodic: Boolean) {
      congratulation?.periodic = periodic
   }

   fun setActive(active: Boolean) {
      congratulation?.active = active
   }

   fun getContactByPeaceName(searchText: String): LiveData<List<Contact>> {
      return congratulationUseCase.getContactByPeaceName(searchText).asLiveData()
   }

   fun save() {
      congratulation?.let {
         val dateTime = it.dateTime
         getDateTime(congratulation, dateTime)
         eventHandler.postEvent(BusEvent.TextOfSave)
         congratulationUseCase.upsertCongratulationDB(it)
      }
   }


}