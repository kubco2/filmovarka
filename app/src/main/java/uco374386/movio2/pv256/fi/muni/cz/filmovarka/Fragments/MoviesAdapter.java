package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.MainActivity;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.R;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;

/**
 * Created by user on 10/4/16.
 */

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = DiscoverListFragment.class.getSimpleName();
    private static final int TYPE_SECTION_NAME = 0;
    private static final int TYPE_MOVIE = 1;
    private ArrayList<Object> mItems;
    private Context mContext;
    private ListFragment.ListClickable mCallback;

    public static class SectionViewHolder extends RecyclerView.ViewHolder {
        public TextView sectionTextView;

        public SectionViewHolder(View itemView) {
            super(itemView);
            sectionTextView = (TextView) itemView.findViewById(R.id.movie_item_section);
        }
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView backdropImageView;
        public ViewGroup infoGroup;
        public TextView nameTextView;
        public TextView ratingTextView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            backdropImageView = (ImageView) itemView.findViewById(R.id.movie_item_image);
            infoGroup = (ViewGroup) itemView.findViewById(R.id.movie_item_info_group);
            nameTextView = (TextView) itemView.findViewById(R.id.movie_item_name);
            ratingTextView = (TextView) itemView.findViewById(R.id.movie_item_rating);
        }
    }

    public MoviesAdapter(Context context, ArrayList<Object> items, ListFragment.ListClickable callback) {
        this.mItems = items;
        mContext = context;
        mCallback = callback;
    }

    public void setItems(ArrayList<Object> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        switch (viewType) {
            case TYPE_SECTION_NAME:
                view = inflater.inflate(R.layout.item_section, parent, false);
                return new SectionViewHolder(view);

            case TYPE_MOVIE:
                view = inflater.inflate(R.layout.item_movie, parent, false);
                view.findViewById(R.id.movie_item_image);
                return new MovieViewHolder(view);
        }
        throw new IllegalStateException("viewType not supported");
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder");

        switch (getItemViewType(position)) {
            case TYPE_SECTION_NAME:
                String sectionName = (String) mItems.get(position);
                ((SectionViewHolder) viewHolder).sectionTextView.setText(sectionName);
                break;

            case TYPE_MOVIE:
                final MovieViewHolder movieViewHolder = (MovieViewHolder) viewHolder;
                final MovieResponse movie = (MovieResponse) mItems.get(position);
                TextView textView = movieViewHolder.nameTextView;
                textView.setText(movie.title);
                textView = movieViewHolder.ratingTextView;
                textView.setText(String.format("%.1f", movie.voteAverage));

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.onItemClicked(movie);
                    }
                });

                Picasso.with(getContext()).load(movie.getBackdropUrl("w1280")).into(movieViewHolder.backdropImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap backdropBitmap = ((BitmapDrawable) movieViewHolder.backdropImageView.getDrawable()).getBitmap();
                        if (backdropBitmap != null && !backdropBitmap.isRecycled()) {
                            movieViewHolder.backdropImageView.setImageBitmap(backdropBitmap);
                            Palette.from(backdropBitmap).generate(new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette palette) {
                                    movieViewHolder.infoGroup.setBackgroundColor(palette.getDarkVibrantColor(0x000000));
                                    movieViewHolder.infoGroup.getBackground().setAlpha(128);
                                }
                            });
                        }
                    }

                    @Override
                    public void onError() {
                        Log.e(TAG, "load backdrop failed.");
                    }
                });
                break;
        }
    }



    @Override
    public int getItemCount() {
        if(mItems == null) {
            return 0;
        }
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position) instanceof String ? TYPE_SECTION_NAME : TYPE_MOVIE;
    }
}
