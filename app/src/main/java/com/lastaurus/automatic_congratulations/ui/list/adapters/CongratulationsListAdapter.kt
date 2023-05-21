package com.lastaurus.automatic_congratulations.ui.list.adapters

import android.annotation.SuppressLint
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
import com.lastaurus.automatic_congratulations.data.model.Congratulation
import com.lastaurus.automatic_congratulations.repository.DataRepository
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import javax.inject.Inject

class CongratulationsListAdapter : RecyclerView.Adapter<CongratulationsListAdapter.ViewHolder?>() {
   private var eventCongratulationsList: List<Congratulation> = emptyList()
   private var click: Click? = null

   @Inject
   lateinit var context: Context

   @Inject
   lateinit var imageModule: ImageModule

   @Inject
   lateinit var repository: DataRepository

   init {
      instance.appComponent.inject(this)
   }

   companion object {
      private val FORMATTER_DATE: DateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy")
      private val FORMATTER_TIME: DateTimeFormatter = DateTimeFormat.forPattern("HH:mm")
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view: View = LayoutInflater.from(parent.context)
         .inflate(R.layout.congratulationslist_item, parent, false)
      return ViewHolder(view)
   }

   fun setList(eventCongratulationsList: List<Congratulation>?) {
      eventCongratulationsList?.let { this.eventCongratulationsList = it }
   }

   fun setClick(click: Click) {
      this.click = click
   }

   @SuppressLint("SetTextI18n")
   override fun onBindViewHolder(holder: CongratulationsListAdapter.ViewHolder, position: Int) {
      val congratulations = eventCongratulationsList[position]
      val contact = repository.getContactById(congratulations.getIdContact())
      contact?.getUriThumbnail()?.let { imageModule.showImageForContact(holder.icon, it, false) }

      holder.apply {
         nameView.text = contact?.getName()
         phoneView.text = congratulations.getPhone()
         dateAndTime.text = FORMATTER_DATE.print(congratulations.getDateTime()) + " " +
               FORMATTER_TIME.print(congratulations.getDateTime())
         itemView.setOnClickListener {
            if (click != null) {
               congratulations.getId().let { click?.click(it) }
            }
         }
      }
   }

   override fun getItemCount(): Int {
      return eventCongratulationsList.size
   }

   class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
      val icon: ImageView
      val nameView: TextView
      val phoneView: TextView
      val dateAndTime: TextView

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