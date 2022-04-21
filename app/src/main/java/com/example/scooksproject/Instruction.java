package com.example.scooksproject;

import java.io.Serializable;
import java.util.List;

public class Instruction implements Serializable {
    private String content;
    private int workTime;
    private int freeTime;
    private boolean isSelected;

    public Instruction() { }

    public Instruction(String content, int workTime, int freeTime) {
        this.content = content;
        this.workTime = workTime;
        this.freeTime = freeTime;
        this.isSelected=false;
    }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public int getWorkTime() { return workTime; }

    public void setWorkTime(int workTime) { this.workTime = workTime; }

    public int getFreeTime() { return freeTime; }

    public void setFreeTime(int freeTime) { this.freeTime = freeTime; }

}
