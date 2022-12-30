package com.hfad.avc.ui.namelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hfad.avc.R;
import com.hfad.avc.ui.database.Contact;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

   private View.OnClickListener onItemClickListener;
   private AdapterView.OnItemClickListener itemClickListener;
   private List<Contact> contactList;
   private Click click;

   public ListAdapter(ArrayList<Contact> contactList) {
      this.contactList = contactList;
   }

   @Override
   public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
      return new ViewHolder(view);
   }

   public void setClick(Click click) {
      this.click = click;
   }

   @Override
   public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
      Contact сontact = contactList.get(position);
      holder.nameView.setText(сontact.getName());
      holder.PhoneView.setText(сontact.getPhone());
      holder.itemView.setOnClickListener(v -> {
         if (click != null) {
            click.click(Integer.parseInt(сontact.getId()));
         }
      });
   }

   public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
      this.onItemClickListener = onItemClickListener;
   }

   @Override
   public int getItemCount() {
      return contactList.size();
   }

   public static class ViewHolder extends RecyclerView.ViewHolder {
      //final ImageView favView;
      final TextView nameView, PhoneView;

      ViewHolder(View view) {
         super(view);
         //favView = (ImageView)view.findViewById(R.id.fav);
         nameView = (TextView) view.findViewById(R.id.nameListContact);
         PhoneView = (TextView) view.findViewById(R.id.nameListPhone);
      }
   }

   public interface Click {
      void click(int id);
   }
}
