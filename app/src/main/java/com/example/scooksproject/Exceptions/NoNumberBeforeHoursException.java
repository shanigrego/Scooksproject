package com.example.scooksproject.Exceptions;

import androidx.annotation.Nullable;

public class NoNumberBeforeHoursException extends Exception{

    @Nullable
    @Override
    public String getMessage() { return "נא להכניס מספר לפני המילה שעות"; }
}
