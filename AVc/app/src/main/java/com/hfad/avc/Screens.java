package com.hfad.avc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.hfad.avc.ui.contact.ContactFragment;
import com.hfad.avc.ui.main_fragment.MainFragment;
import com.hfad.avc.ui.namelist.NameListFragment;
import com.hfad.avc.ui.template.TemplateFragment;
import com.hfad.avc.ui.templateWrite.TemplateWriteFragment;

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
            NameListFragment fragment = new NameListFragment();
            return fragment;
        }
    }

    public static final class TemplateScreen extends SupportAppScreen {

        public TemplateScreen() {this.screenKey = getClass().getSimpleName();}

        @Override
        public Fragment getFragment() {
            TemplateFragment fragment = new TemplateFragment();
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
    public static final class TemplateWriteScreen extends SupportAppScreen {

        private final int templateId;

        public TemplateWriteScreen(int templateId) {
            this.templateId = templateId;
            this.screenKey = getClass().getSimpleName();
        }

        @Override
        public Fragment getFragment() {
            TemplateWriteFragment fragment = new TemplateWriteFragment();
            Bundle args = new Bundle();
            args.putInt("template_Id", this.templateId);
            fragment.setArguments(args);
            return fragment;
        }
    }

}