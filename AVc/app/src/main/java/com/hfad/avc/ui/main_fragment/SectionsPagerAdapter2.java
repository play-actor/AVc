package com.hfad.avc.ui.main_fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hfad.avc.ui.list.all_congratulations_list.AllCongratulationsListFragment;
import com.hfad.avc.ui.list.contact_list.ContactListFragment;
import com.hfad.avc.ui.list.favorite_contact_list.FavoriteContactListFragment;
import com.hfad.avc.ui.list.template_list.TemplateListFragment;

public class SectionsPagerAdapter2 extends FragmentStateAdapter {


   public SectionsPagerAdapter2( Fragment fragment) {
      super(fragment);
   }

   @NonNull
   @Override
   public Fragment createFragment(int position) {
      final Fragment templateListFragment = new TemplateListFragment();
      final Fragment favoriteContactListFragment = new FavoriteContactListFragment();
      final Fragment allCongratulationsListFragment = new AllCongratulationsListFragment();
      final Fragment contactListFragment = new ContactListFragment();
      switch (position) {
         case 0:
            return contactListFragment;
         case 1:
            return favoriteContactListFragment;
         case 2:
            return allCongratulationsListFragment;
         case 3:
            return templateListFragment;
      }
      return contactListFragment;
   }

   @Override
   public int getItemCount() {
      return 4;
   }
}

