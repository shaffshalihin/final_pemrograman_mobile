package com.example.fittracker.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.fittracker.Models.Bookmark;
import com.example.fittracker.api.Exercise;
import com.example.fittracker.R;
import com.example.fittracker.Utils.BookmarkUtils;
import com.example.fittracker.fragment.ExercisesFragment;

public class ExerciseDetailActivity extends AppCompatActivity {

    private TextView exerciseName;
    private TextView exerciseType;
    private TextView exerciseMuscle;
    private TextView exerciseEquipment;
    private TextView exerciseDifficulty;
    private TextView exerciseInstructions;
    private TextView timerTextView;
    private Button startTimerButton;
    private Button bookmarkButton;

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        exerciseName = findViewById(R.id.exercise_name);
        exerciseType = findViewById(R.id.exercise_type);
        exerciseMuscle = findViewById(R.id.exercise_muscle);
        exerciseEquipment = findViewById(R.id.exercise_equipment);
        exerciseDifficulty = findViewById(R.id.exercise_difficulty);
        exerciseInstructions = findViewById(R.id.exercise_instructions);
        timerTextView = findViewById(R.id.timer_text_view);
        startTimerButton = findViewById(R.id.start_timer_button);
        bookmarkButton = findViewById(R.id.bookmark_button);

        startTimerButton.setOnClickListener(v -> startTimer(180000)); // 180000 ms = 3 menit


        Exercise exercise = getIntent().getParcelableExtra("exercise");
        if (exercise != null) {
            exerciseName.setText(exercise.getName());
            exerciseType.setText(exercise.getType());
            exerciseMuscle.setText(exercise.getMuscle());
            exerciseEquipment.setText(exercise.getEquipment());
            exerciseDifficulty.setText(exercise.getDifficulty());
            exerciseInstructions.setText(exercise.getInstructions());

            bookmarkButton.setOnClickListener(v -> {
                BookmarkUtils.saveBookmark(this, new Bookmark(exercise));
                finish();
            });
        }
    }
    private void startTimer(long duration) {
        countDownTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 1000 / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
                timerTextView.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                timerTextView.setText("00:00");
                // Aksi apa pun yang ingin Anda lakukan ketika timer selesai
            }
        };

        countDownTimer.start();

    }


}
