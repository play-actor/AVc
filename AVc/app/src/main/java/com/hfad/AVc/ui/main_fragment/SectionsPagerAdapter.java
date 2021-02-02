package com.hfad.AVc.ui.main_fragment;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hfad.AVc.R;
import com.hfad.AVc.ui.main_fragment.CongratulationsFragment;
import com.hfad.AVc.ui.main_fragment.Db_Fragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    /**
     * Необходимо наличие конструктора, получающего параметр FragmentManager.
     */
    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    /**
     * Необходимо переопределить метод getCount() для определения количества страниц в ViewPager.
     */
    public int getCount() {
        return 2;
    }

    @Override
    /**
     * Необходимо указать, какой фрагмент должен
     * выводиться на каждой странице. Позиция определяет номер страницы (начиная с 0).
     */
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Db_Fragment();
            case 1:
                return new CongratulationsFragment();
        }
        return null;
    }

    @Override
    /**
     * Новый метод в созданной ранее реализации FragmentPagerAdapter.
     */
    public CharSequence getPageTitle(int position) {
        switch (position) {
            /**
             * В этих строках кода добавляются строковые ресурсы для вкладок.
             */
            case 0:
                return this.context.getResources().getText(R.string.base_work);
            case 1:
                return this.context.getResources().getText(R.string.congratulations);
        }
        return null;
    }
}