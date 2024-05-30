package com.example.fittracker.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.fittracker.Adapter.BookmarkAdapter;
import com.example.fittracker.Models.Bookmark;
import com.example.fittracker.R;
import com.example.fittracker.Utils.BookmarkUtils;

import java.util.List;

public class TrackerFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookmarkAdapter adapter;
    private List<Bookmark> bookmarkList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracker, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bookmarkList = BookmarkUtils.getBookmarks(getContext());
        adapter = new BookmarkAdapter(bookmarkList, getContext(), this::onBookmarkDeleted);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void onBookmarkDeleted(int position) {
        // The item has already been removed from the adapter
        // No need to handle it here again
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView iv_exercises = view.findViewById(R.id.iv_exercises_fragment);

        iv_exercises.setOnClickListener(v -> {
            ExercisesFragment exercisesFragment = new ExercisesFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, exercisesFragment)
                    .commit();
        });

    }
}
