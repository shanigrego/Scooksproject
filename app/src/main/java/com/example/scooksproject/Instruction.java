package com.example.scooksproject;

import java.io.Serializable;
import java.util.List;

public class Instruction implements Serializable {
    private String content;
    private int workTime;
    private int freeTime;
    private int index;
    private String recipeName;
    public Instruction() { }

    public Instruction(int index,String content, int workTime, int freeTime) {
        this.content = content;
        this.workTime = workTime;
        this.freeTime = freeTime;
        this.index=index;
        this.recipeName=null;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public int getIndex() {
        return index;
    }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public int getWorkTime() { return workTime; }

    public void setWorkTime(int workTime) { this.workTime = workTime; }

    public int getFreeTime() { return freeTime; }

    public void setFreeTime(int freeTime) { this.freeTime = freeTime; }

}
