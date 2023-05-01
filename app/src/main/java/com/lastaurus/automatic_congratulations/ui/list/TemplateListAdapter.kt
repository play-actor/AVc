package com.lastaurus.automatic_congratulations.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.data.model.Template

class TemplateListAdapter : RecyclerView.Adapter<TemplateListAdapter.ViewHolder?>() {
   private var templateList: List<Template> = emptyList()
   private var click: Click? = null
   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view: View =
         LayoutInflater.from(parent.context).inflate(R.layout.template_item, parent, false)
      return ViewHolder(view)
   }

   fun setClick(click: Click) {
      this.click = click
   }

   fun setList(templateList: List<Template>) {
      this.templateList = templateList
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val template = templateList[position]
      holder.apply {
         templateView.text = template.getTextTemplate()
         favorite.visibility = if (template.getFavorite()) View.VISIBLE else View.INVISIBLE
         itemView.setOnClickListener {
            if (click != null) {
               click!!.click(template.getId())
            }
         }
      }
   }

   override fun getItemCount(): Int {
      return templateList.size
   }

   inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
      val templateView: TextView
      val favorite: ImageView

      init {
         templateView = view.findViewById<View>(R.id.text_template) as TextView
         favorite = view.findViewById(R.id.favorite_template)
      }
   }

   interface Click {
      fun click(id: Int)
   }
}