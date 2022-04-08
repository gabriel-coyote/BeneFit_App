package com.example.benefit_app.Objects;

public class DailyWaterLog {

    private int todaysProgress, todaysGoal;

    public DailyWaterLog(){}


    public DailyWaterLog(int goal, int progress){
        this.todaysGoal = goal;
        this.todaysProgress = progress;
    }

    public int getTodaysProgress(){return todaysProgress;}

    public int getTodaysGoal(){return todaysGoal;}

}
