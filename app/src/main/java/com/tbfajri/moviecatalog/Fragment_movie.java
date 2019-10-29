package com.tbfajri.moviecatalog;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;

import com.tbfajri.moviecatalog.entity.Movie;

import java.util.ArrayList;


import static com.tbfajri.moviecatalog.R.string.set_language;



/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_movie extends Fragment implements MovieAdapter.OnItemClickListener {
    private ProgressBar progressBar;
    private MovieAdapter adapter;
    private MainViewModel mainViewModel;
    private RecyclerView recyclerView;
    private ArrayList<Movie> mMovies;
    private String setlanguage;

    public Fragment_movie() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerView_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMovies = new ArrayList<>();

        adapter = new MovieAdapter(getActivity(), mMovies);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        showLoading(true);

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        setlanguage = getString(set_language);
        mainViewModel.setMovie(setlanguage);

        mainViewModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> weatherItems) {
                if (weatherItems != null) {
                    adapter.setData(weatherItems);
                    showLoading(false);
                }
            }
        });



        return view;
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), NoteAddUpdateActivity.class);
        intent.putExtra(NoteAddUpdateActivity.EXTRA_PERSON, mMovies.get(position));
        startActivity(intent);
    }
}
