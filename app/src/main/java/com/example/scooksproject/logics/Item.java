package com.example.scooksproject.logics;

public class Item {

    public String name;
    public int value;
    public int weight;

    public Item(String name,int freeTime, int weight) {
        this.name = name;
       if(freeTime>0)
           this.value=2;
       else
           this.value=1;
        this.weight = weight;
    }

    public String str() {
        return name + " [value = " + value + ", weight = " + weight + "]";
    }

}