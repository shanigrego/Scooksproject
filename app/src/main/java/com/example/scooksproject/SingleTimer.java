package com.example.scooksproject;

import java.util.Calendar;

public class SingleTimer {

    public SingleTimer(int hours, int minutes, String idByStep, int minutesForCountdown) {
        this.hours = hours;
        this.minutes = minutes;
        this.idByStep = idByStep;
        this.minutesForCountdown = minutesForCountdown;
    }

    private int hours;
    private int minutes;
    private String idByStep;
    private int minutesForCountdown;

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public String getIdByStep() {
        return idByStep;
    }

    public int getMinutesForCountdown() {
        return minutesForCountdown;
    }

    public int getMinutesForView(){
        return Math.abs(minutes - Calendar.getInstance().get(Calendar.MINUTE));
    }

    public int getHoursForView(){
        return Math.abs(hours - Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
    }
}
