package com.tbfajri.moviecatalog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tbfajri.moviecatalog.entity.Movie;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.MovieViewHolder> {

    private Context mContext;
    private ArrayList<Movie> mData = new ArrayList<>();
    private OnItemClickListener mListener;
    Activity activity;


    public MovieFavoriteAdapter(Activity activity, Context context, ArrayList<Movie> mMovies) {

        mContext = context;
        this.mData = mMovies;


        this.activity = activity;
    }

    public MovieFavoriteAdapter(FragmentActivity activity, ArrayList<Movie> mMovies) {

    }

    public void setData(ArrayList<Movie> items) {
        if (items.size() > 0){
            mData.clear();
        }
        mData.addAll(items);

        notifyDataSetChanged();

    }

    public void addItem(Movie movie) {
        mData.add(movie);
        notifyItemChanged(mData.size() -1);
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mData.size());
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_film, viewGroup, false);
        return new MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        movieViewHolder.bind(mData.get(position));
        movieViewHolder.cvMovie.setOnClickListener(new CustomOnItemClickListener(new CustomOnItemClickListener.OnItemClickCallback() {
                    @Override
                    public void onItemClicked(View view, int position) {
                        Intent intent = new Intent(activity, NoteAddUpdateActivity.class);
                        intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, position);
                        intent.putExtra(NoteAddUpdateActivity.EXTRA_PERSON, mData.get(position));
                        activity.startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_UPDATE);
                    }
                } , position));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        final TextView textViewNama, textViewDescription;
        final ImageView imgPhotos;
        final CardView cvMovie;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNama = itemView.findViewById(R.id.txt_name);
            textViewDescription = itemView.findViewById(R.id.txt_description);
            imgPhotos = itemView.findViewById(R.id.img_photo);
            cvMovie = itemView.findViewById(R.id.cv_item_note);

        }


        void bind(Movie movie){

            textViewNama.setText(movie.getName());
            textViewDescription.setText(movie.getDescription());

            Glide.with(itemView.getContext())
                    .load(movie.getPhoto())
                    .into(imgPhotos);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
