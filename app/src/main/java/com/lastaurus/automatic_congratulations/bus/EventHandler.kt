package com.lastaurus.automatic_congratulations.bus

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EventHandler {
   private val scope = CoroutineScope(Job())

   fun postEvent(event: BusEvent) {
      scope.launch {
         postLoginEvent(event)
      }
   }

   suspend fun postLoginEvent(loginEvent: BusEvent) {
      EventBus.publish(loginEvent)
   }

   fun subscribeEvent(o: (BusEvent) -> Boolean) {
      scope.launch {
         EventBus.subscribe<BusEvent> { loginEvent ->
            o.invoke(loginEvent)
         }
      }
   }
}
