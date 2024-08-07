package com.lastaurus.automatic_congratulations.ui.contact.compose

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel

class ContactComposeViewModel : ViewModel() {

    private val _counter = mutableIntStateOf(0)
    val counter: State<Int> = _counter

    fun onCounterClick() {
        _counter.value++
    }
}
