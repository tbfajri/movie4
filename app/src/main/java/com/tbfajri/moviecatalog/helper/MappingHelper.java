package com.tbfajri.moviecatalog.helper;

import android.database.Cursor;

import com.tbfajri.moviecatalog.db.DatabaseContract;
import com.tbfajri.moviecatalog.db.DatabaseHelper;
import com.tbfajri.moviecatalog.entity.Movie;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<Movie> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<Movie> notesList = new ArrayList<>();
        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.NAME));
            String description = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.DESCRIPTION));
            String date = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.RELEASE_DATE));
            String foto = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.PHOTO));
            String popularity = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POPULARITY));
            String vote = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.VOTE_COUNT));
            notesList.add(new Movie(id, title, description, date, foto, popularity, vote));
        }
        return notesList;
    }
}
