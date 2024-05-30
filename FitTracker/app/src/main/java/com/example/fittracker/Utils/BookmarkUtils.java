package com.example.fittracker.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.fittracker.Models.Bookmark;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookmarkUtils {

    private static final String PREFS_NAME = "fittracker_prefs";
    private static final String KEY_BOOKMARKS = "bookmarks";

    public static void saveBookmark(Context context, Bookmark bookmark) {
        List<Bookmark> bookmarks = getBookmarks(context);
        for (Bookmark b : bookmarks) {
            if (b.getExerciseName().equals(bookmark.getExerciseName())) {
                Toast.makeText(context, "This item is already bookmarked", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Toast.makeText(context, "Exercise bookmarked!", Toast.LENGTH_SHORT).show();
        bookmarks.add(bookmark);
        saveBookmarks(context, bookmarks);
    }

    public static void removeBookmark(Context context, Bookmark bookmark) {
        List<Bookmark> bookmarks = getBookmarks(context);
        bookmarks.removeIf(b -> b.getExerciseName().equals(bookmark.getExerciseName()));
        saveBookmarks(context, bookmarks);
    }

    public static List<Bookmark> getBookmarks(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_BOOKMARKS, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Bookmark>>() {}.getType();
        return json != null ? gson.fromJson(json, type) : new ArrayList<>();
    }

    private static void saveBookmarks(Context context, List<Bookmark> bookmarks) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bookmarks);
        editor.putString(KEY_BOOKMARKS, json);
        editor.apply();
    }
}
