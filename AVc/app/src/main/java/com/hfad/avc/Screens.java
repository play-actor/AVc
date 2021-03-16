package com.hfad.avc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.hfad.avc.ui.contact.ContactFragment;
import com.hfad.avc.ui.database.Db_Fragment;
import com.hfad.avc.ui.main_fragment.MainFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {

    public static final class MainScreen extends SupportAppScreen {

        public MainScreen() {
            this.screenKey = getClass().getSimpleName();
        }

        @Override
        public Fragment getFragment() {
            MainFragment fragment = new MainFragment();
            return fragment;
        }
    }

    public static final class DbScreen extends SupportAppScreen {

        public DbScreen() {
            this.screenKey = getClass().getSimpleName();
        }

        @Override
        public Fragment getFragment() {
            Db_Fragment fragment = new Db_Fragment();
            return fragment;
        }
    }

    public static final class ConatctScreen extends SupportAppScreen {

        private final int congratulations;

        public ConatctScreen(int congratulations) {
            this.congratulations = congratulations;
            this.screenKey = getClass().getSimpleName();
        }

        @Override
        public Fragment getFragment() {
            ContactFragment fragment = new ContactFragment();
            Bundle args = new Bundle();
            args.putInt("congratulations", this.congratulations);
            fragment.setArguments(args);
            return fragment;
        }
    }

}