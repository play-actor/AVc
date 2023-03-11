package com.hfad.avc.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hfad.avc.R;
import com.hfad.avc.dagger.ComponentManager;

import java.util.ArrayList;

import javax.inject.Inject;

public class PhoneListAdapter extends RecyclerView.Adapter<PhoneListAdapter.ViewHolder> {

   private ArrayList<String> phonelist;
   private Click click;
   @Inject
   Context context;

   public PhoneListAdapter(ArrayList<String> phonelist) {
      ComponentManager.Companion.getInstance().appComponent.inject(this);
      this.phonelist = phonelist;
   }

   @Override
   public PhoneListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phonenomber_item, parent, false);
      return new ViewHolder(view);
   }

   public void setClick(Click click) {
      this.click = click;
   }

   @Override
   public void onBindViewHolder(PhoneListAdapter.ViewHolder holder, int position) {
      holder.phone.setText(phonelist.get(position));
      holder.itemView.setOnClickListener(v -> {

      });
   }

   @Override
   public int getItemCount() {
      return phonelist != null ? phonelist.size() : 0;
   }

   public static class ViewHolder extends RecyclerView.ViewHolder {
      final TextView phone;

      ViewHolder(View view) {
         super(view);
         phone = view.findViewById(R.id.phone);
      }
   }
   public interface Click {
      void click();
   }
}
