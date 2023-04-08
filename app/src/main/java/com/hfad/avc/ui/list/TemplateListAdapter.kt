package com.hfad.avc.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hfad.avc.R
import com.hfad.avc.data.model.Template

class TemplateListAdapter(private val templateList: List<Template>) :
   RecyclerView.Adapter<TemplateListAdapter.ViewHolder?>() {
   private var click: Click? = null
   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view: View =
         LayoutInflater.from(parent.context).inflate(R.layout.template_item, parent, false)
      return ViewHolder(view)
   }

   fun setClick(click: Click) {
      this.click = click
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val template = templateList[position]
      holder.templateView.text = template.getTextTemplate()
      holder.itemView.setOnClickListener {
         if (click != null) {
            click!!.click(template.getId().toInt())
         }
      }
   }

   override fun getItemCount(): Int {
      return templateList.size
   }

   inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
      val templateView: TextView

      init {
         templateView = view.findViewById<View>(R.id.text_template) as TextView
      }
   }

   interface Click {
      fun click(id: Int)
   }
}