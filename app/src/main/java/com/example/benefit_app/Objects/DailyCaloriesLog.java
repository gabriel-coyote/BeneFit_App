package com.example.benefit_app.Objects;

public class DailyCaloriesLog {

    private int todaysProgress, todaysGoal;

    public DailyCaloriesLog(){}


    public DailyCaloriesLog(int goal, int progress){
        this.todaysGoal = goal;
        this.todaysProgress = progress;
    }

    public int getTodaysProgress(){return todaysProgress;}

    public int getTodaysGoal(){return todaysGoal;}

}
