package com.hfad.avc.ui.main_fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.avc.R;
import com.hfad.avc.ui.database.Contact;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter2 extends RecyclerView.Adapter<ListAdapter2.ViewHolder> {

   private View.OnClickListener onItemClickListener;
   private AdapterView.OnItemClickListener itemClickListener;
   private List<Contact> contactList2;
   private Click click;

   public ListAdapter2(ArrayList<Contact> contactList) {
      this.contactList2 = contactList;
   }

   @Override
   public ListAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
      return new ViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull ListAdapter2.ViewHolder holder, int position) {
      Contact сontact = contactList2.get(position);
      holder.nameView2.setText(сontact.getName());
      holder.PhoneView2.setText(сontact.getPhone());
      holder.itemView.setOnClickListener(v -> {
         if (click != null) {
            click.click(Integer.parseInt(сontact.getId()));
         }
      });

   }

   public void setClick(Click click) {
      this.click = click;
   }


   public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
      this.onItemClickListener = onItemClickListener;
   }

   @Override
   public int getItemCount() {
      return contactList2.size();
   }

   public static class ViewHolder extends RecyclerView.ViewHolder {
      //final ImageView favView;
      final TextView nameView2, PhoneView2;

      ViewHolder(View view) {
         super(view);
         //favView = (ImageView)view.findViewById(R.id.fav);
         nameView2 = (TextView) view.findViewById(R.id.nameListContact);
         PhoneView2 = (TextView) view.findViewById(R.id.nameListPhone);
      }
   }

   public interface Click {
      void click(int id);
   }
}

