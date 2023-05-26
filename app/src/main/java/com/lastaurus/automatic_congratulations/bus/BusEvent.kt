package com.lastaurus.automatic_congratulations.bus

sealed class BusEvent {
   class Text(val text: String) : BusEvent()
   object TextOfSave : BusEvent() {
      fun getTextSave(): String {
         return "Сохранено"
      }
   }
}