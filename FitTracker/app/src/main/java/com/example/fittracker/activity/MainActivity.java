package com.example.fittracker.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fittracker.R;
import com.example.fittracker.fragment.TrackerFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        TrackerFragment profile = new TrackerFragment();

        Fragment fragment = fragmentManager.findFragmentByTag(TrackerFragment.class.getSimpleName());

        if (!(fragment instanceof TrackerFragment)) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.frame_container, profile)
                    .commit();
        }
    }
}