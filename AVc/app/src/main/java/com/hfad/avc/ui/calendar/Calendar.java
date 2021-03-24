package com.hfad.avc.ui.calendar;

import androidx.core.util.Pair;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.TimeZone;

public class Calendar {

    private long today;
    private long oneYearForward;
    private Pair<Long, Long> todayPair;

    public Calendar(@CalendarType int calendarType, FragmentManager fragmentManager, OnSelectionListener listener, String selectDateWho) {
        initSettings(calendarType);

        MaterialDatePicker.Builder<?> builder = getBuilder(calendarType);
        builder.setTitleText("Выберите дату " + selectDateWho);

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        if (calendarType == CalendarType.SINGLE) {
            constraintsBuilder.setStart(today);
        }
        constraintsBuilder.setEnd(oneYearForward);
        constraintsBuilder.setOpenAt(today);

        builder.setCalendarConstraints(constraintsBuilder.build());
        MaterialDatePicker<?> picker = builder.build();
        picker.show(fragmentManager, picker.toString());

        picker.addOnPositiveButtonClickListener(
                selection -> {
                    if (listener != null) {
                        listener.selection(selection);
                    }
                });
    }

    private void initSettings(@CalendarType int type) {
        today = MaterialDatePicker.thisMonthInUtcMilliseconds();
        java.util.Calendar calendar = getClearedUtc();
        calendar.setTimeInMillis(today);
        calendar.roll(type, 10);

        calendar.setTimeInMillis(today);
        calendar.set(type, java.util.Calendar.JANUARY);
        calendar.setTimeInMillis(today);
        calendar.set(type, java.util.Calendar.DECEMBER);

        calendar.setTimeInMillis(today);
        calendar.roll(java.util.Calendar.YEAR, 1);
        oneYearForward = calendar.getTimeInMillis();

        todayPair = new Pair<>(today, today);
    }

    private MaterialDatePicker.Builder<?> getBuilder(@CalendarType int type) {
        switch (type) {
            case CalendarType.SINGLE:
                MaterialDatePicker.Builder<Long> longBuilder = MaterialDatePicker.Builder.datePicker();
                longBuilder.setSelection(today);
                return longBuilder;
            case CalendarType.RANGE:
                MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
                builder.setSelection(todayPair);
                return builder;
        }

        throw new RuntimeException("Указан неверный тип календаря!");
    }

    private java.util.Calendar getClearedUtc() {
        java.util.Calendar utc = java.util.Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        utc.clear();
        return utc;
    }

    public interface OnSelectionListener {

        void selection(Object selection);
    }
}