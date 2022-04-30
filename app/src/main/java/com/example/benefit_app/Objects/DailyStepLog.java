package com.example.benefit_app.Objects;

public class DailyStepLog {

    private int todaysProgress, todaysGoal;

    public DailyStepLog(){}


    public DailyStepLog(int goal, int progress){
        this.todaysGoal = goal;
        this.todaysProgress = progress;
    }

    public int getTodaysProgress(){return todaysProgress;}

    public int getTodaysGoal(){return todaysGoal;}

}
