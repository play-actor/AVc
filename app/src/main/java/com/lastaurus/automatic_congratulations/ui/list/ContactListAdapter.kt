package com.lastaurus.automatic_congratulations.ui.list

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

class ContactListAdapter(private val contactList: List<Contact>) :
   RecyclerView.Adapter<ContactListAdapter.ViewHolder?>() {
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

   fun setClick(click: Click) {
      this.click = click
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val contact = contactList[position]
      holder.nameText.text = contact.getName()
      holder.phoneText.text = contact.getPhoneList()[0]
      holder.favorite.visibility = if (contact.getFavorite()) View.VISIBLE else View.INVISIBLE
      contact.getUriThumbnail().let { imageModule.showImageForContact(holder.icon, it, false) }
      holder.itemView.setOnClickListener {
         if (click != null) {
            contact.getId().toInt().let { click?.click(it) }
         }
      }
   }

   override fun getItemCount(): Int {
      return contactList.size
   }

   class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
      val icon: ImageView
      val favorite: ImageView
      private val iconNotification: ImageView
      val nameText: TextView
      val phoneText: TextView

      init {
         icon = view.findViewById(R.id.icon)
         favorite = view.findViewById(R.id.favorite)
         iconNotification = view.findViewById(R.id.icon_notification)
         nameText = view.findViewById(R.id.nameListContact)
         phoneText = view.findViewById(R.id.nameListPhone)
      }
   }

   interface Click {
      fun click(id: Int)
   }
}