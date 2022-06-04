package com.example.scooksproject;

public class SingleTimer {

    public SingleTimer(int hours, int minutes, String idByStep) {
        this.hours = hours;
        this.minutes = minutes;
        this.idByStep = idByStep;
    }

    private int hours;
    private int minutes;
    private String idByStep;

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public String getIdByStep() {
        return idByStep;
    }
}
