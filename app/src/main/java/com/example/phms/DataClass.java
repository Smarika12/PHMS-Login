package com.example.phms;

public class DataClass
{
    private String foodName;
    private String calorieCount;

    public DataClass(String foodName, String calorieCount)
    {
        this.foodName = foodName;
        this.calorieCount = calorieCount;
    }
    public String getFoodName()
    {
        return foodName;
    }
    public String getCalorieCount()
    {
        return calorieCount;
    }

    public DataClass()
    {

    }

}
