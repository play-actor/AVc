package com.hfad.avc.bus


import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class RxBus {
   private val bus = PublishSubject.create<Int>()
   fun send(auth: Int) {
      bus.onNext(auth)
   }

   fun waitCall(): Observable<Int> {
      return bus
   }
}
