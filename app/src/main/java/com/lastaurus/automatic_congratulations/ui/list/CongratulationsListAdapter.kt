package com.lastaurus.automatic_congratulations.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.dagger.ComponentManager.Companion.instance
import com.lastaurus.automatic_congratulations.dagger.module.ImageModule
import com.lastaurus.automatic_congratulations.data.model.Contact
import com.lastaurus.automatic_congratulations.data.model.EventCongratulations
import com.lastaurus.automatic_congratulations.managers.DBManager
import javax.inject.Inject

class CongratulationsListAdapter :
   RecyclerView.Adapter<CongratulationsListAdapter.ViewHolder?>() {
   private var onItemClickListener: View.OnClickListener? = null
   private val itemClickListener: OnItemClickListener? = null
   private val eventCongratulationsList: List<EventCongratulations>
   private val contactList: List<Contact>
   private var click: Click? = null

   @Inject
   lateinit var context: Context

   @Inject
   lateinit var imageModule: ImageModule

   @Inject
   lateinit var dbManager: DBManager

   init {
      instance.appComponent.inject(this)
      eventCongratulationsList = dbManager.getCongratulationsList(0)
      contactList = dbManager.getContactList(0)
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view: View = LayoutInflater.from(parent.context)
         .inflate(R.layout.congratulationslist_item, parent, false)
      return ViewHolder(view)
   }

   fun setClick(click: Click) {
      this.click = click
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//      EventCongratulations congratulations = eventCongratulationsList.get(position);
//      Contact contact = contactList.get(position);
//      holder.nameView.setText(contact.getName());

//      holder.view.setText(congratulations.getTextTemplate());
//      holder.itemView.setOnClickListener(v -> {
//         if (click != null) {
//            click.click(Integer.parseInt(congratulations.getId()));
//         }
//      });
   }

   override fun getItemCount(): Int {
      return eventCongratulationsList.size
   }

   fun setOnItemClickListener(onItemClickListener: View.OnClickListener) {
      this.onItemClickListener = onItemClickListener
   }

   class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
      private val icon: ImageView
      private val nameView: TextView
      private val phoneView: TextView
      private val dateAndTime: TextView

      init {
         icon = view.findViewById(R.id.icon_contact)
         nameView = view.findViewById(R.id.name_contact)
         phoneView = view.findViewById(R.id.phone_number_contact)
         dateAndTime = view.findViewById(R.id.date_and_time)
      }
   }

   interface Click {

      fun click(id: Int)
   }
}