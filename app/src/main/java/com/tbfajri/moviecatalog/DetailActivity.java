package com.tbfajri.moviecatalog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tbfajri.moviecatalog.db.MovieHelper;
import com.tbfajri.moviecatalog.entity.Movie;


import static com.tbfajri.moviecatalog.NoteAddUpdateActivity.EXTRA_POSITION;
import static com.tbfajri.moviecatalog.NoteAddUpdateActivity.RESULT_ADD;
import static com.tbfajri.moviecatalog.db.DatabaseContract.MovieColumns.DESCRIPTION;
import static com.tbfajri.moviecatalog.db.DatabaseContract.MovieColumns.NAME;
import static com.tbfajri.moviecatalog.db.DatabaseContract.MovieColumns.PHOTO;
import static com.tbfajri.moviecatalog.db.DatabaseContract.MovieColumns.POPULARITY;
import static com.tbfajri.moviecatalog.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.tbfajri.moviecatalog.db.DatabaseContract.MovieColumns.VOTE_COUNT;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_PERSON = "extra_person";
    public MovieHelper movieHelper;
    private TextView txtName, txtDescription, txtPopularity, txtRelease, txtVoteCount;
    private Movie movie;
    private ImageView foto;
    private Button btnFavourite, btnBack;
    private int position;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtName = findViewById(R.id.text_title);
        txtDescription = findViewById(R.id.text_description);
        txtPopularity = findViewById(R.id.text_popularity);
        txtRelease = findViewById(R.id.text_release);
        txtVoteCount = findViewById(R.id.text_vote);
        foto = findViewById(R.id.image_detail);
        btnBack = findViewById(R.id.button_back);
        btnFavourite = findViewById(R.id.btn_favourit);

        movieHelper = MovieHelper.getInstance(getApplicationContext());


        movie = getIntent().getParcelableExtra(EXTRA_PERSON);

        if (movie != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        } else {
            movie = new Movie();
        }

        txtName.setText(movie.getName());
        txtDescription.setText(movie.getDescription());
        txtPopularity.setText(movie.getPopularity());
        txtRelease.setText(movie.getRelease_date());
        txtVoteCount.setText(movie.getVote_count());

        Glide.with(this)
                .load(movie.getPhoto())
                .into(foto);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = txtName.getText().toString().trim();
                String description = txtDescription.getText().toString().trim();
                String popularity = txtPopularity.getText().toString().trim();
                String realase_data = txtRelease.getText().toString().trim();
                String vote = txtVoteCount.getText().toString().trim();
                final String fotoPoster = movie.getPhoto();


                Intent intent = new Intent();
                intent.putExtra(EXTRA_PERSON, movie);
                intent.putExtra(EXTRA_POSITION, position);

                ContentValues values = new ContentValues();
                values.put(NAME, title);
                values.put(DESCRIPTION, description);
                values.put(POPULARITY, popularity);
                values.put(RELEASE_DATE, realase_data);
                values.put(VOTE_COUNT, vote);
                values.put(PHOTO, String.valueOf(fotoPoster));

                //values = intent.getParcelableExtra(EXTRA_PERSON);
                long result = movieHelper.insert(values);
                if (result > 0) {
                    movie.setId((int) result);
                    setResult(RESULT_ADD, intent);
                    finish();

                } else {
                    Toast.makeText(DetailActivity.this, "Gagal menambah data", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}