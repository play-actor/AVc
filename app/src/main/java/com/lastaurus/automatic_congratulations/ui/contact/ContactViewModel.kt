package com.lastaurus.automatic_congratulations.ui.contact

import androidx.lifecycle.ViewModel
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.dagger.module.ImageModule
import com.lastaurus.automatic_congratulations.data.model.Contact
import com.lastaurus.automatic_congratulations.util.isNull
import javax.inject.Inject

class ContactViewModel : ViewModel() {

    @Inject
    lateinit var changeContactUseCase: ContactUseCase

    @Inject
    lateinit var imageModule: ImageModule

    private var contactName: String = ""
    private var contact: Contact? = null

    init {
        ComponentManager.instance.appComponent.inject(this)
    }

    fun initContact(id: Int? = null) {
        id?.apply { contact = changeContactUseCase.getContact(this) }
        if (contact.isNull()) {
            contact = Contact()
            contact?.id = getListSize()
        }
    }

    private fun getListSize(): Int {
        return changeContactUseCase.getContactListSize()
    }

    fun update() {
        contact?.let { changeContactUseCase.updateContactDB(it) }
    }

    fun upsert() {
        contact?.let { changeContactUseCase.upsertContactDB(it) }
    }

    fun getName(): String {
        contactName = contact?.name ?: ""
        return contactName
    }

    fun getPhoneListFromContact(): ArrayList<String>? {
        return contact?.id?.let { changeContactUseCase.getPhoneListFromContact(it) }
    }

    fun getFavorite(): Boolean {
        return contact?.favorite ?: false
    }

    fun setFavoriteContactCompose(favorite: Boolean) {
        contact?.favorite = favorite
    }

    fun save() {
        contact?.apply {
            changeContactUseCase.upsertContactDB(this)
        }
    }
}