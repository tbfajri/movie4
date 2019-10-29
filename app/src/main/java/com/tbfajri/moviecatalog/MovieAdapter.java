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

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context mContext;
    private ArrayList<Movie> mData;
    private OnItemClickListener mListener;

    public MovieAdapter(Context context, ArrayList<Movie> mMovies) {

        mContext = context;
        this.mData = mMovies;

    }

    public MovieAdapter(FragmentActivity activity) {

    }

    public void setData(ArrayList<Movie> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();

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

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewNama;
        private TextView textViewDescription;
        private ImageView imgPhotos;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNama = itemView.findViewById(R.id.txt_name);
            textViewDescription = itemView.findViewById(R.id.txt_description);
            imgPhotos = itemView.findViewById(R.id.img_photo);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (mListener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
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
