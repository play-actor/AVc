package com.hfad.avc.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.avc.R;
import com.hfad.avc.dagger.ComponentManager;
import com.hfad.avc.dagger.module.ImageModule;
import com.hfad.avc.data.model.Contact;
import com.hfad.avc.data.model.EventCongratulations;
import com.hfad.avc.managers.DBManager;

import java.util.List;

import javax.inject.Inject;

public class CongratulationsListAdapter extends RecyclerView.Adapter<CongratulationsListAdapter.ViewHolder> {
   private View.OnClickListener onItemClickListener;
   private AdapterView.OnItemClickListener itemClickListener;
   private List<EventCongratulations> eventCongratulationsList;
   private List<Contact> contactList;
   private Click click;

   @Inject
   Context context;
   @Inject
   ImageModule imageModule;

   @Inject
   DBManager dbManager;

   public CongratulationsListAdapter() {
      ComponentManager.Companion.getInstance().appComponent.inject(this);
      this.eventCongratulationsList = dbManager.getCongratulationsList(0);
      this.contactList = dbManager.getContactList(0);
   }

   @Override
   public CongratulationsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.congratulationslist_item, parent, false);
      return new ViewHolder(view);
   }

   public void setClick(Click click) {
      this.click = click;
   }

   @Override
   public void onBindViewHolder(@NonNull CongratulationsListAdapter.ViewHolder holder, int position) {
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

   @Override
   public int getItemCount() {
      return eventCongratulationsList.size();
   }

   public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
      this.onItemClickListener = onItemClickListener;
   }

   public static class ViewHolder extends RecyclerView.ViewHolder {
      final ImageView icon;
      final TextView nameView, PhoneView;

      ViewHolder(View view) {
         super(view);
         icon = view.findViewById(R.id.icon_contact);
         nameView = view.findViewById(R.id.name_contact);
         PhoneView = view.findViewById(R.id.phone_number_contact);
      }
   }

   public interface Click {
      void click(int id);
   }
}
