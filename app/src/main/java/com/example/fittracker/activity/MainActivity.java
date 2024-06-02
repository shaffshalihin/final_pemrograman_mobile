package com.example.fittracker.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.fittracker.R;
import com.example.fittracker.fragment.ExercisesFragment;
import com.example.fittracker.fragment.TrackerFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private MeowBottomNavigation meowBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meowBottomNavigation = findViewById(R.id.meow_bottom_navigation);

        // Add navigation items
        meowBottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.baseline_accessibility_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.baseline_admin_panel_settings_24));

        // Set default fragment
        loadFragment(new TrackerFragment());

        // Set listener for navigation item click
        meowBottomNavigation.setOnClickMenuListener(model -> {
            Fragment fragment = null;
            switch (model.getId()) {
                case 1:
                    fragment = new ExercisesFragment();
                    break;
                case 2:
                    fragment = new TrackerFragment();
                    break;
            }
            loadFragment(fragment);
            return null;
        });

        // Set listener for default selected item
        meowBottomNavigation.setOnShowListener(model -> {
            Fragment fragment = null;
            switch (model.getId()) {
                case 1:
                    fragment = new ExercisesFragment();
                    break;
                case 2:
                    fragment = new TrackerFragment();
                    break;
            }
            loadFragment(fragment);
            return null;
        });

        // Set the first selected item
        meowBottomNavigation.show(2, true);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
            return true;
        }
        return false;
    }
}


