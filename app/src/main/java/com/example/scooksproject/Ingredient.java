package com.example.scooksproject;

public class Ingredient {

    private String name;
    private double amount;
    private String measureUnit;

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
}
