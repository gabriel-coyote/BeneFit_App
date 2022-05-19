package com.example.benefit_app.Objects;

public class CreatedWorkout {

    private String workoutTitle, workoutBody;
    private int workoutCalories;

    public CreatedWorkout(String title, String workout, int calories){
        this.workoutBody = workout;
        this.workoutTitle = title;
        this.workoutCalories = calories;
    }


    public String getWorkoutBody() {
        return workoutBody;
    }

    public String getWorkoutTitle() {
        return workoutTitle;
    }


    public int getWorkoutCalories() {
        return workoutCalories;
    }
}
