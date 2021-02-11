package com.hfad.avc.ui.main_fragment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hfad.avc.R;

public class SectionsPagerAdapter2 extends FragmentStateAdapter {

    private Context context;


    public SectionsPagerAdapter2(Context context, Fragment fm) {
        super(fm);
        this.context = context;
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment comingCongratulationsFragment = new ComingCongratulationsFragment();
        Fragment db_Fragment = new Db_Fragment();
        Fragment congratulationsFragment = new AllCongratulationsFragment();

        switch (position) {
            case 0:
                return comingCongratulationsFragment;
            case 1:
                return congratulationsFragment;

        }
        return null;


    }

    @Override
    public int getItemCount() {
        return 2;
    }


    public CharSequence getPageTitle(int position) {
        switch (position) {
            /**
             * В этих строках кода добавляются строковые ресурсы для вкладок.
             */
            case 0:
                return this.context.getResources().getText(R.string.congratulations_coming);
            case 1:
                return this.context.getResources().getText(R.string.congratulations);

        }
        return null;
    }

}

