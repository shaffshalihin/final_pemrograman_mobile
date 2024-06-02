package com.example.fittracker;

import com.example.fittracker.api.Exercise;
import java.util.List;

public class ExerciseResponse {
    private List<Exercise> data;

    public List<Exercise> getData() {
        return data;
    }

    public void setData(List<Exercise> data) {
        this.data = data;
    }
}
