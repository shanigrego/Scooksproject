package com.example.scooksproject.Exceptions;

import androidx.annotation.Nullable;

public class NoNumberBeforeMinutesException extends Exception {

    @Nullable
    @Override
    public String getMessage() {
        return "נא להכניס מספר לפני המילה דקות";
    }

}
