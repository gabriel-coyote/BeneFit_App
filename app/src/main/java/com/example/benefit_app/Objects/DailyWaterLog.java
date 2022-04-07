package com.example.benefit_app.Objects;

public class DailyWaterLog {

    private String todaysProgress, todaysGoal;

    public DailyWaterLog(){}


    public DailyWaterLog(String goal, String progress){
        this.todaysGoal = goal;
        this.todaysProgress = progress;
    }

    public String getTodaysProgress(){return todaysProgress;}

    public String getTodaysGoal(){return todaysGoal;}

}
