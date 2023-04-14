package com.lastaurus.automatic_congratulations.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.dagger.ComponentManager.Companion.instance
import javax.inject.Inject

class PhoneListAdapter(private val phonelist: ArrayList<String>) :
   RecyclerView.Adapter<PhoneListAdapter.ViewHolder>() {


   @Inject
   lateinit var context: Context

   init {
      instance.appComponent.inject(this)
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view: View =
         LayoutInflater.from(parent.context).inflate(R.layout.phonenomber_item, parent, false)
      return ViewHolder(view)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.phone.text = phonelist[position]
   }

   override fun getItemCount(): Int {
      return phonelist.size
   }

   class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
      val phone: TextView

      init {
         phone = view.findViewById(R.id.phone)
      }
   }
}