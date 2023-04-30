package com.lastaurus.automatic_congratulations.bus


import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class RxBus {
   private val bus = PublishSubject.create<String>()
   fun send(text: String) {
      bus.onNext(text)
   }

   fun waitCall(): Observable<String> {
      return bus
   }
}
