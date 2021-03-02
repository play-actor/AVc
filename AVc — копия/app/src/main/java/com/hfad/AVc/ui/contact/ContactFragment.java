package com.hfad.AVc.ui.contact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.hfad.AVc.R;
import com.hfad.AVc.databinding.FragmentContactBinding;
import com.hfad.AVc.ui.Calendar;
import com.hfad.AVc.ui.CalendarType;
import com.hfad.AVc.ui.database.Contact;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;


public class ContactFragment extends MvpAppCompatFragment implements IContactViewModel {

    @InjectPresenter
    ContactPresenter presenter;

    private String TAG = "AVc";
    private FragmentContactBinding binding;
    public static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("dd.MM.yyyy, HH:mm");


    public static ContactFragment newInstance(Integer congratulations) {
        ContactFragment contactFragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putInt("congratulations", congratulations);
        contactFragment.setArguments(args);
        return contactFragment;
    }

    @ProvidePresenter
    ContactPresenter ProvidePresenterContactPresenter() {
        return new ContactPresenter(getArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false);
        return binding.getRoot();
    }

    @Override
    public void openCalendar() {
        new Calendar(
                CalendarType.SINGLE,
                requireActivity().getSupportFragmentManager(),
                selection -> {
                    this.presenter.setDate(((Long) selection));
//                    ((TextInputLayout) this.binding.getRoot().findViewById(R.id.date_congratulations))
//                            .getEditText()
//                            .setText(FORMATTER.print(((Long) selection)));
                },
                "времени поздравления"
        );
    }

    @Override
    public void setData(Contact contact) {
        this.binding.setContactDetail(contact);
        this.binding.setPresenter(this.presenter);
    }
}