package com.hfad.avc.ui;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({CalendarType.RANGE, CalendarType.SINGLE})
@Retention(RetentionPolicy.SOURCE)
public @interface CalendarType {
    int RANGE = 1;
    int SINGLE = 2;
}