package uco374386.movio2.pv256.fi.muni.cz.filmovarka;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

/**
 * Created by user on 10/4/16.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private static final String TAG = ListFragment.class.getSimpleName();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView ratingTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.movie_item_name);
            ratingTextView = (TextView) itemView.findViewById(R.id.movie_item_rating);
        }
    }

    private List<Movie> mMovies;
    private Context mContext;

    public MoviesAdapter(Context context, List<Movie> movies) {
        mMovies = movies;
        mContext = context;
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

        ViewHolder viewHolder = new ViewHolder(movieView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MoviesAdapter.ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder");
        final Movie movie = mMovies.get(position);

        TextView textView = viewHolder.nameTextView;
        textView.setText(movie.title);
        textView = viewHolder.ratingTextView;
        textView.setText("" + movie.popularity);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)MoviesAdapter.this.getContext()).openDetails(movie);
            }
        });

        Bitmap myBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.placeholder);
        if (myBitmap != null && !myBitmap.isRecycled()) {
            Palette.from(myBitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    View movieItemInfo = viewHolder.itemView.findViewById(R.id.movie_item_info);
                    movieItemInfo.setBackgroundColor(palette.getDarkVibrantColor(0x000000));
                    movieItemInfo.getBackground().setAlpha(128);
                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}
