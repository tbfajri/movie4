package com.tbfajri.moviecatalog;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tbfajri.moviecatalog.entity.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {

    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private MutableLiveData<ArrayList<Movie>> listMovie = new MutableLiveData<>();

    void setMovie (final String language ) {

        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=" + language;
        final String urlImage = "https://image.tmdb.org/t/p/w185";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++){
                        JSONObject movie = list.getJSONObject(i);
                        Movie movieItems = new Movie();
                        movieItems.setPhoto((urlImage) + movie.getString("poster_path"));
                        movieItems.setName(movie.getString("title"));
                        movieItems.setDescription(movie.getString("overview"));
                        movieItems.setVote_count(movie.getString("vote_count"));
                        movieItems.setPopularity(movie.getString("popularity"));
                        movieItems.setRelease_date(movie.getString("release_date"));

                        listItems.add(movieItems);

                    } listMovie.postValue(listItems);

                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    void setTv (final String language ) {

        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=" + language;
        final String urlImage = "https://image.tmdb.org/t/p/w185";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++){
                        JSONObject movie = list.getJSONObject(i);
                        Movie movieItems = new Movie();
                        movieItems.setPhoto((urlImage) + movie.getString("backdrop_path"));
                        movieItems.setName(movie.getString("name"));
                        movieItems.setDescription(movie.getString("overview"));
                        movieItems.setVote_count(movie.getString("vote_count"));
                        movieItems.setPopularity(movie.getString("popularity"));
                        movieItems.setRelease_date(movie.getString("first_air_date"));

                        listItems.add(movieItems);

                    } listMovie.postValue(listItems);

                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    LiveData<ArrayList<Movie>> getMovies() {
        return listMovie;
    }
}
