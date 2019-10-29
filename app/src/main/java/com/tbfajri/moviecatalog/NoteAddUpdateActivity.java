package com.tbfajri.moviecatalog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tbfajri.moviecatalog.db.DatabaseContract;
import com.tbfajri.moviecatalog.db.MovieHelper;
import com.tbfajri.moviecatalog.entity.Movie;

import static android.provider.ContactsContract.Intents.Insert.NAME;
import static android.provider.MediaStore.Video.VideoColumns.DESCRIPTION;
import static com.tbfajri.moviecatalog.db.DatabaseContract.MovieColumns.PHOTO;
import static com.tbfajri.moviecatalog.db.DatabaseContract.MovieColumns.POPULARITY;
import static com.tbfajri.moviecatalog.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.tbfajri.moviecatalog.db.DatabaseContract.MovieColumns.VOTE_COUNT;

public class NoteAddUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtName, txtDescription, txtPopularity, txtRelease, txtVoteCount;
    private ImageView foto;
    private Button btn_favorit, btn_delete;

    private boolean isEdit = false;
    private Movie movie;
    private int position;
    private MovieHelper movieHelper;

    public static final String EXTRA_PERSON = "extra_person";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;
    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add_update);

        movie = getIntent().getParcelableExtra(EXTRA_PERSON);
        movieHelper = MovieHelper.getInstance(getApplicationContext());


        txtName = findViewById(R.id.text_title);
        txtDescription = findViewById(R.id.text_description);
        txtPopularity = findViewById(R.id.text_popularity);
        txtRelease = findViewById(R.id.text_release);
        txtVoteCount = findViewById(R.id.text_vote);
        foto = findViewById(R.id.image_detail);
        btn_delete = findViewById(R.id.button_back);
        btn_favorit = findViewById(R.id.btn_favourit);


        txtName.setText(movie.getName());
        txtDescription.setText(movie.getDescription());
        txtPopularity.setText(movie.getPopularity());
        txtRelease.setText(movie.getRelease_date());
        txtVoteCount.setText(movie.getVote_count());

        Glide.with(this)
                .load(movie.getPhoto())
                .into(foto);

        btn_favorit.setOnClickListener(this);
        btn_delete.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        final Movie movie = getIntent().getParcelableExtra(EXTRA_PERSON);


        if (v.getId() == R.id.btn_favourit) {


            Intent intent = new Intent();
            intent.putExtra(EXTRA_PERSON, movie);
            intent.putExtra(EXTRA_POSITION, position);

            ContentValues values = new ContentValues();
            values.put(DatabaseContract.MovieColumns.NAME, txtName.getText().toString().trim());
            values.put(DatabaseContract.MovieColumns.DESCRIPTION, txtDescription.getText().toString().trim());
            values.put(RELEASE_DATE, txtRelease.getText().toString().trim());
            values.put(PHOTO, String.valueOf(foto));
            values.put(POPULARITY, txtPopularity.getText().toString().trim());
            values.put(VOTE_COUNT, txtVoteCount.getText().toString().trim());

            long result = movieHelper.insert(values);

            if (result > 0) {
                movie.setId((int) result);
                setResult(RESULT_ADD, intent);
                finish();
            } else {
                Toast.makeText(NoteAddUpdateActivity.this, "Gagal menambah data", Toast.LENGTH_SHORT).show();
            }

        } else if (v.getId() == R.id.button_back) {
            int type = 0;

            final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
            String dialogTitle, dialogMessage;

            if (isDialogClose) {
                dialogTitle = "Batal";
                dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?";
            } else {
                dialogMessage = "Apakah anda yakin ingin menghapus item ini?";
                dialogTitle = "Hapus Note";
            }

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setTitle(dialogTitle);
            alertDialogBuilder
                    .setMessage(dialogMessage)
                    .setCancelable(false)
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (isDialogClose) {
                                finish();
                            } else {
                                long result = movieHelper.deleteById(String.valueOf(movie.getId()));
                                if (result > 0) {
                                    Intent intent = new Intent();
                                    intent.putExtra(EXTRA_POSITION, position);
                                    setResult(RESULT_DELETE, intent);
                                    finish();
                                } else {
                                    Toast.makeText(NoteAddUpdateActivity.this, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    })
                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }



    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit) {
            getMenuInflater().inflate(R.menu.menu_form, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }
}
