package com.example.fittracker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fittracker.R;
import com.example.fittracker.activity.ExerciseDetailActivity;
import com.example.fittracker.Models.Bookmark;
import com.example.fittracker.Utils.BookmarkUtils;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {

    private List<Bookmark> bookmarkList;
    private Context context;
    private OnBookmarkDeleteListener deleteListener;

    public interface OnBookmarkDeleteListener {
        void onBookmarkDeleted(int position);
    }

    public BookmarkAdapter(List<Bookmark> bookmarkList, Context context, OnBookmarkDeleteListener deleteListener) {
        this.bookmarkList = bookmarkList;
        this.context = context;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bookmark bookmark = bookmarkList.get(position);
        holder.exerciseName.setText(bookmark.getExerciseName());
        holder.exerciseMuscle.setText(bookmark.getExerciseMuscle());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ExerciseDetailActivity.class);
            intent.putExtra("exercise", bookmark.toExercise());
            context.startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {
            BookmarkUtils.removeBookmark(context, bookmark);
            bookmarkList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, bookmarkList.size());
            deleteListener.onBookmarkDeleted(position);
        });
    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName;
        TextView exerciseMuscle;
        ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.tv_exercise_name);
            exerciseMuscle = itemView.findViewById(R.id.tv_exercise_muscle);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
