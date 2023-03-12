package com.hfad.avc.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.avc.R;
import com.hfad.avc.data.model.Template;

import java.util.List;

public class TemplateListAdapter extends RecyclerView.Adapter<TemplateListAdapter.ViewHolder> {
   private View.OnClickListener onItemClickListener;
   private AdapterView.OnItemClickListener itemClickListener;
   private List<Template> templateList;
   private Click click;

   public TemplateListAdapter(List<Template> templateList) {
      this.templateList = templateList;
   }

   @Override
   public TemplateListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_item, parent, false);
      return new ViewHolder(view);
   }

   public void setClick(TemplateListAdapter.Click click) {
      this.click = click;
   }

   @Override
   public void onBindViewHolder(@NonNull TemplateListAdapter.ViewHolder holder, int position) {
      Template template = templateList.get(position);
      holder.templateView.setText(template.getTextTemplate());
      holder.itemView.setOnClickListener(v -> {
         if (click != null) {
            click.click(Integer.parseInt(template.getId()));
         }
      });
   }

   @Override
   public int getItemCount() {
      return templateList.size();
   }

   public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
      this.onItemClickListener = onItemClickListener;
   }

   public class ViewHolder extends RecyclerView.ViewHolder {
      final TextView templateView;

      ViewHolder(View view) {
         super(view);
         templateView = (TextView) view.findViewById(R.id.text_template);
      }
   }

   public interface Click {
      void click(int id);
   }
}
