package com.tbfajri.moviecatalog;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.tbfajri.moviecatalog.db.MovieHelper;
import com.tbfajri.moviecatalog.entity.Movie;
import com.tbfajri.moviecatalog.helper.MappingHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.tbfajri.moviecatalog.R.string.set_language;

public class FavouriteMovie extends Fragment implements LoadMovieCallback {

    private ProgressBar progressBar;
    private RecyclerView rvNotes;
    private MainViewModel mainViewModel;
    private FavoritMovieAdapter adapter;
    private MovieHelper movieHelper;
    private ArrayList<Movie> mMovies;
    private String setlanguage;

    private static final String EXTRA_STATE = "EXTRA_STATE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.item_film, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        rvNotes = view.findViewById(R.id.recyclerView_movie);
        rvNotes.setLayoutManager(new LinearLayoutManager(getContext()));
        mMovies = new ArrayList<>();
        rvNotes.setHasFixedSize(true);

        adapter = new FavoritMovieAdapter(getActivity());
        rvNotes.setAdapter(adapter);
        //adapter.setOnItemClickListener(this);

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        movieHelper = MovieHelper.getInstance(getActivity());
        movieHelper.open();

        setlanguage = getString(set_language);
        mainViewModel.setMovie(setlanguage);

        if (savedInstanceState == null) {
            new LoadNotesAsync(movieHelper, this).execute();
        } else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setData(list);
                progressBar.setVisibility(View.VISIBLE);
            }
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.get());
    }

    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void runOnUiThread(Runnable runnable) {

    }

    @Override
    public void postExecute(ArrayList<Movie> notes) {
        progressBar.setVisibility(View.INVISIBLE);
        if (notes.size() > 0) {
            adapter.setListNotes(notes);
        } else {
            adapter.setListNotes(new ArrayList<Movie>());
            showSnackbarMessage("Tidak ada data saat ini");
        }
    }


    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_PERSON, mMovies.get(position));
        startActivity(intent);
    }


    private static class LoadNotesAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {

        private final WeakReference<MovieHelper> weakNoteHelper;
        private final WeakReference<LoadMovieCallback> weakCallback;

        private LoadNotesAsync(MovieHelper movieHelper, FavouriteMovie callback) {
            weakNoteHelper = new WeakReference<>(movieHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            Cursor dataCursor = weakNoteHelper.get().queryAll();
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> notes) {
            super.onPostExecute(notes);

            weakCallback.get().postExecute(notes);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvNotes, message, Snackbar.LENGTH_SHORT).show();
    }

}

interface LoadMovieCallback {
    void preExecute();
    void postExecute(ArrayList<Movie> notes);
}
