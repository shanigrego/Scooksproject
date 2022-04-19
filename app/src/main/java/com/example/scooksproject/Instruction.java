package com.example.scooksproject;

import java.io.Serializable;
import java.util.List;

public class Instruction implements Serializable {
    private String content;
    private double workTime;
    private double freeTime;
    private boolean isSelected;

    public Instruction() { }

    public Instruction(String content, double workTime, double freeTime) {
        this.content = content;
        this.workTime = workTime;
        this.freeTime = freeTime;
        this.isSelected=false;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public double getWorkTime() { return workTime; }

    public void setWorkTime(double workTime) { this.workTime = workTime; }

    public double getFreeTime() { return freeTime; }

    public void setFreeTime(double freeTime) { this.freeTime = freeTime; }

}
