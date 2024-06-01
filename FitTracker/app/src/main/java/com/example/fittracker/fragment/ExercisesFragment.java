package com.example.fittracker.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fittracker.R;
import com.example.fittracker.Adapter.ExercisesAdapter;
import com.example.fittracker.api.Api;
import com.example.fittracker.api.Exercise;
import com.example.fittracker.api.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExercisesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ExercisesAdapter adapter;
    private List<Exercise> exerciseList = new ArrayList<>();
    private ProgressBar progressBar;
    private SearchView searchView;
    private ExecutorService executorService;
    private Handler mainHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercises, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ExercisesAdapter(exerciseList, getContext());
        recyclerView.setAdapter(adapter);
        progressBar = view.findViewById(R.id.progressBar);
        searchView = view.findViewById(R.id.search_view);
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());
        setupSearchView();
        loadData();
        return view;
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String query) {
        List<Exercise> filteredList = new ArrayList<>();
        for (Exercise exercise : exerciseList) {
            if (exercise.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(exercise);
            }
        }
        adapter.filterList(filteredList);
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);

        executorService.execute(() -> {
            Api api = RetrofitClient.getClient("https://api.api-ninjas.com/v1/").create(Api.class);
            Call<List<Exercise>> call = api.getExercises();
            call.enqueue(new Callback<List<Exercise>>() {
                @Override
                public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                    mainHandler.post(() -> progressBar.setVisibility(View.GONE));
                    if (response.isSuccessful() && response.body() != null) {
                        exerciseList.addAll(response.body());
                        mainHandler.post(() -> adapter.notifyDataSetChanged());
                    } else {
                        mainHandler.post(() -> Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show());
                    }
                }

                @Override
                public void onFailure(Call<List<Exercise>> call, Throwable t) {
                    mainHandler.post(() -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Failed to connect", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
