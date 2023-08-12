package com.lastaurus.automatic_congratulations.ui.congratulation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.lastaurus.automatic_congratulations.Util.Util.Companion.getDateTime
import com.lastaurus.automatic_congratulations.bus.BusEvent
import com.lastaurus.automatic_congratulations.bus.EventHandler
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.dagger.module.ImageModule
import com.lastaurus.automatic_congratulations.data.model.Congratulation
import com.lastaurus.automatic_congratulations.data.model.Contact
import com.lastaurus.automatic_congratulations.data.model.Template
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
      if (id == null) {
         if (congratulation == null) {
            congratulation = Congratulation()
            congratulation?.setId(getListSize())
         }
      } else {
         congratulation = congratulationUseCase.getCongratulation(id)
         if (congratulation == null) {
            congratulation = Congratulation()
            congratulation?.setId(getListSize())
         }
      }
   }

   fun getId(): Int {
      return congratulation?.getId() ?: Int.MIN_VALUE
   }

   private fun getListSize(): Int {
      return congratulationUseCase.getCongratulationListSize()
   }

   fun getNameContact(): String {
      return getNameContact(congratulation?.getIdContact())
   }

   fun getNameContact(id: Int?): String {
      return congratulationUseCase.getContact(id)?.getName() ?: ""
   }

   fun getContactPhoneList(): ArrayList<String>? {
      return congratulationUseCase.getContactPhoneList(congratulation?.getIdContact())
   }

   fun getTextTemplate(): String {
      return getTextTemplate(congratulation?.getIdTemplate())
   }

   fun getTextTemplate(id: Int?): String {
      return congratulationUseCase.getTemplate(id)?.getTextTemplate() ?: ""
   }

   fun getTextPhone(): String {
      return congratulation?.getPhone() ?: ""
   }

   fun getTextPhone(id: Int?): String {
      return id?.let {
         congratulationUseCase.getContactPhoneList(congratulation?.getIdContact())?.get(it) ?: ""
      }.toString()
   }

   fun getDate(): String {
      return congratulation?.getDateTime()?.let { FORMATTER_DATE.print(it) } ?: ""
   }

   fun getTime(): String {
      return (congratulation?.getDateTime()?.let { FORMATTER_TIME.print(it) } ?: "")
   }

   fun getPeriodic(): Boolean {
      return congratulation?.getPeriodic() ?: false
   }

   fun setDateTime(dateTime: Long) {
      congratulation?.setDateTime(dateTime)
   }

   fun setContact(id: Int) {
      congratulation?.setIdContact(id)
   }

   fun setPhone(text: String) {
      congratulation?.setPhone(text)
   }

   fun setTemplate(id: Int) {
      congratulation?.setIdTemplate(id)
   }

   fun getContactList(): LiveData<List<Contact>> {
      return congratulationUseCase.getContactList().asLiveData()
   }

   fun getTemplateList(): LiveData<List<Template>> {
      return congratulationUseCase.getTemplateList().asLiveData()
   }

   fun getActive(): Boolean? {
      return congratulation?.getActive()
   }

   fun setPeriodic(periodic: Boolean) {
      congratulation?.setPeriodic(periodic)
   }

   fun setActive(active: Boolean) {
      congratulation?.setActive(active)
   }

   fun getContactByPeaceName(searchText: String): LiveData<List<Contact>> {
      return congratulationUseCase.getContactByPeaceName(searchText).asLiveData()
   }

   fun save() {
      congratulation?.let {
         val dateTime = it.getDateTime()
         getDateTime(congratulation, dateTime)
         eventHandler.postEvent(BusEvent.TextOfSave)
         congratulationUseCase.upsertCongratulationDB(it)
      }
   }


}