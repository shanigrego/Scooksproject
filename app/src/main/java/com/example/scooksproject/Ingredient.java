package com.example.scooksproject;

public class Ingredient {

    private String name;
    private double amount;
    private String measureUnit;

    //Required for firebase
    //Do not erase!!!
    public Ingredient(){}

    public Ingredient(String name, double amount, String measureUnit) {
        this.name = name;
        this.amount = amount;
        this.measureUnit = measureUnit;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }
}
