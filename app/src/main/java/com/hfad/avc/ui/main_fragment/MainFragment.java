package com.hfad.avc.ui.main_fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationBarView;
import com.hfad.avc.R;
import com.hfad.avc.dagger.ComponentManager;

public class MainFragment extends Fragment {

   private NavigationBarView barView;
   private ViewPager2 viewPager;
   private ViewPager2.OnPageChangeCallback onPageChangeCallback;

   @Override
   public void onCreate(@Nullable Bundle savedInstanceState) {
      ComponentManager.Companion.getInstance().appComponent.inject(this);
      super.onCreate(savedInstanceState);
   }

   @SuppressLint("NonConstantResourceId")
   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_main, container, false);
      this.barView = view.findViewById(R.id.bottomAppBar);
      final SectionsPagerAdapter2 pagerAdapter =
            new SectionsPagerAdapter2(
                  this
            );
      this.viewPager = view.findViewById(R.id.pager);
      this.viewPager.setAdapter(pagerAdapter);
      this.barView.setOnItemSelectedListener(item -> {
         switch (item.getItemId()) {
            default:
            case R.id.contactList:
               viewPager.setCurrentItem(0, true);
               break;
            case R.id.contactfavorites:
               viewPager.setCurrentItem(1, true);
               break;
            case R.id.templated_contacts:
               viewPager.setCurrentItem(2, true);
               break;
            case R.id.templates:
               viewPager.setCurrentItem(3, true);
               break;
         }
         return true;
      });

      this.onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
         @Override
         public void onPageSelected(int position) {
            switch (position) {
               default:
               case 0:
                  barView.setSelectedItemId(R.id.contactList);
                  break;
               case 1:
                  barView.setSelectedItemId(R.id.contactfavorites);
                  break;
               case 2:
                  barView.setSelectedItemId(R.id.templated_contacts);
                  break;
               case 3:
                  barView.setSelectedItemId(R.id.templates);
                  break;
            }
            super.onPageSelected(position);
         }
      };
      viewPager.registerOnPageChangeCallback(onPageChangeCallback);
      return view;
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
   }

   @Override
   public void onDestroy() {
      requireActivity().finish();
      super.onDestroy();
      this.viewPager.unregisterOnPageChangeCallback(onPageChangeCallback);
   }
}