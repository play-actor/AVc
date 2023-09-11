package com.lastaurus.automatic_congratulations.ui.list.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.dagger.ComponentManager.Companion.instance
import com.lastaurus.automatic_congratulations.dagger.module.ImageModule
import com.lastaurus.automatic_congratulations.data.model.Contact
import javax.inject.Inject

class ContactListAdapter : RecyclerView.Adapter<ContactListAdapter.ViewHolder?>() {
   private var contactList: List<Contact> = emptyList()
   private var click: Click? = null

   @Inject
   lateinit var context: Context

   @Inject
   lateinit var imageModule: ImageModule

   init {
      instance.appComponent.inject(this)
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view: View =
         LayoutInflater.from(parent.context).inflate(R.layout.contactlist_item, parent, false)
      return ViewHolder(view)
   }

   fun setList(contactList: List<Contact>?) {
      contactList?.let { this.contactList = it }
   }

   fun setClick(click: Click) {
      this.click = click
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val contact = contactList[position]
      contact.uriThumbnail.let { imageModule.showImageForContact(holder.icon, it, false) }
      holder.apply {
         nameText.text = contact.name
         phoneText.text = contact.phoneList[0]
         favorite.visibility = if (contact.favorite) View.VISIBLE else View.INVISIBLE
         itemView.setOnClickListener {
            if (click != null) {
               contact.id.let { click?.click(it) }
            }
         }
      }
   }

   override fun getItemCount(): Int {
      return contactList.size
   }

   class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
      val icon: ImageView
      val favorite: ImageView
      val nameText: TextView
      val phoneText: TextView

      init {
         icon = view.findViewById(R.id.icon)
         favorite = view.findViewById(R.id.favorite)
         nameText = view.findViewById(R.id.nameListContact)
         phoneText = view.findViewById(R.id.nameListPhone)
      }
   }

   interface Click {
      fun click(id: Int)
   }
}