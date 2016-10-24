package uco374386.movio2.pv256.fi.muni.cz.filmovarka;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.IOException;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieListResponse;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;

/**
 * Created by user on 10/4/16.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private static final String TAG = ListFragment.class.getSimpleName();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView backdropImageView;
        public ViewGroup infoGroup;
        public TextView nameTextView;
        public TextView ratingTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            backdropImageView = (ImageView) itemView.findViewById(R.id.movie_item_image);
            infoGroup = (ViewGroup) itemView.findViewById(R.id.movie_item_info_group);
            nameTextView = (TextView) itemView.findViewById(R.id.movie_item_name);
            ratingTextView = (TextView) itemView.findViewById(R.id.movie_item_rating);
        }
    }

    private MovieListResponse mMovies;
    private Context mContext;

    public MoviesAdapter(Context context, MovieListResponse movies) {
        mMovies = movies;
        mContext = context;
    }

    public void setMovieList(MovieListResponse movieList) {
        mMovies = movieList;
        notifyDataSetChanged();
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View movieView = inflater.inflate(R.layout.item_movie, parent, false);
        View img = movieView.findViewById(R.id.movie_item_image);
        img.setMinimumHeight(MainActivity.getDisplayWidth(getContext())/16*9);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(final MoviesAdapter.ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder");
        final MovieResponse movie = mMovies.results[position];

        TextView textView = viewHolder.nameTextView;
        textView.setText(movie.title);
        textView = viewHolder.ratingTextView;
        textView.setText(String.format("%.1f", movie.voteAverage));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)MoviesAdapter.this.getContext()).openDetails(movie);
            }
        });

        Picasso.with(getContext()).load(movie.getMostSuitableBackdropUrl(getContext())).into(viewHolder.backdropImageView, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap backdropBitmap = ((BitmapDrawable) viewHolder.backdropImageView.getDrawable()).getBitmap();
                if (backdropBitmap != null && !backdropBitmap.isRecycled()) {
                    viewHolder.backdropImageView.setImageBitmap(backdropBitmap);
                    Palette.from(backdropBitmap).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            viewHolder.infoGroup.setBackgroundColor(palette.getDarkVibrantColor(0x000000));
                            viewHolder.infoGroup.getBackground().setAlpha(128);
                        }
                    });
                }
            }

            @Override
            public void onError() {
                Log.e(TAG, "load backdrop failed.");
            }
        });
    }



    @Override
    public int getItemCount() {
        if(mMovies == null || mMovies.results == null) {
            return 0;
        }
        return mMovies.results.length;
    }
}
