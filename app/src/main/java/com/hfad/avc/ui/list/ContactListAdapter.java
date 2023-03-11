package com.hfad.avc.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hfad.avc.R;
import com.hfad.avc.dagger.ComponentManager;
import com.hfad.avc.dagger.module.ImageModule;
import com.hfad.avc.data.model.Contact;

import java.util.List;

import javax.inject.Inject;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

   private List<Contact> contactList;
   private Click click;
   @Inject
   Context context;
   @Inject
   ImageModule imageModule;

   public ContactListAdapter(List<Contact> contactList) {
      ComponentManager.Companion.getInstance().appComponent.inject(this);
      this.contactList = contactList;
   }

   @Override
   public ContactListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contactlist_item, parent, false);
      return new ViewHolder(view);
   }

   public void setClick(Click click) {
      this.click = click;
   }

   @Override
   public void onBindViewHolder(ContactListAdapter.ViewHolder holder, int position) {
      Contact сontact = contactList.get(position);
      holder.nameView.setText(сontact.getName());
      holder.PhoneView.setText(сontact.getPhoneList().get(0));
      holder.favorite.setVisibility(сontact.getFavorite() ? View.VISIBLE : View.INVISIBLE);
      imageModule.showImageForContact(holder.icon, сontact.getUriThumbnail(), false);
      holder.itemView.setOnClickListener(v -> {
         if (click != null) {
            click.click(Integer.parseInt(сontact.getId()));
         }
      });
   }

   @Override
   public int getItemCount() {
      return contactList != null ? contactList.size() : 0;
   }

   public static class ViewHolder extends RecyclerView.ViewHolder {
      final ImageView icon, favorite, iconNotification;
      final TextView nameView, PhoneView;

      ViewHolder(View view) {
         super(view);
         icon = view.findViewById(R.id.icon);
         favorite = view.findViewById(R.id.favorite);
         iconNotification = view.findViewById(R.id.icon_notification);
         nameView = view.findViewById(R.id.nameListContact);
         PhoneView = view.findViewById(R.id.nameListPhone);
      }
   }

   public interface Click {
      void click(int id);
   }
}
