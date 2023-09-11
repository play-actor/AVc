package com.lastaurus.automatic_congratulations.bus

import com.lastaurus.automatic_congratulations.data.model.Contact

sealed class BusEvent {
   class Text(val text: String) : BusEvent()
   object TextOfSave : BusEvent() {
      fun getTextSave(): String {
         return "Сохранено"
      }
   }

   class SaveContact(val contact: Contact) : BusEvent()
   class Sort(val aZ: Boolean, val type: TypeObject) : BusEvent()
   class SearchByText(val text: String?, val type: TypeObject) : BusEvent()
}

enum class TypeObject() {
   CONTACT,
   CONGRATULATIONS,
   TEMPLATE
}
