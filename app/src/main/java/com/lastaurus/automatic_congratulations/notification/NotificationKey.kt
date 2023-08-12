package com.lastaurus.automatic_congratulations.notification

class NotificationKey {

   enum class Categorys(
      val id: String,
      val number: Int,
      val title: String,
      val stateDefault: Boolean?,
      val vibrateDefault: Boolean,
      val priorityDefault: Int,
      private val createPreference: Boolean,
      val description: String,
   ) {
      C_1(
         "arm",
         0,
         "fff",
         true,
         true,
         4,
         true,
         "По данному каналу возможны уведомления по следующим событиям:\n- Взятие зон/шлефов под охрану\n- Включение контроля датчиков температуры/влажности"
      );

      val key: String
         get() = "notification_category_$id"

      fun getName(): String {
         return title
      }

      fun createPreference(): Boolean {
         return createPreference
      }

      companion object {
         operator fun get(id: String): Categorys {
            for (value in values()) {
               if (value.id == id) {
                  return value
               }
            }
            return C_1
         }


      }
   }
}