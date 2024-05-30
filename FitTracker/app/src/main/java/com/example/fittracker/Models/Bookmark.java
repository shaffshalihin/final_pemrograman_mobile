package com.example.fittracker.Models;

import com.example.fittracker.api.Exercise;

public class Bookmark {
    private String exerciseName;
    private String exerciseType;
    private String exerciseMuscle;
    private String exerciseEquipment;
    private String exerciseDifficulty;
    private String exerciseInstructions;

    public Bookmark(Exercise exercise) {
        this.exerciseName = exercise.getName();
        this.exerciseType = exercise.getType();
        this.exerciseMuscle = exercise.getMuscle();
        this.exerciseEquipment = exercise.getEquipment();
        this.exerciseDifficulty = exercise.getDifficulty();
        this.exerciseInstructions = exercise.getInstructions();
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getExerciseMuscle() {
        return exerciseMuscle;
    }

    public Exercise toExercise() {
        Exercise exercise = new Exercise();
        exercise.setName(exerciseName);
        exercise.setType(exerciseType);
        exercise.setMuscle(exerciseMuscle);
        exercise.setEquipment(exerciseEquipment);
        exercise.setDifficulty(exerciseDifficulty);
        exercise.setInstructions(exerciseInstructions);
        return exercise;
    }
}
