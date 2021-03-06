package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Fragments;

import android.content.Context;
import android.graphics.Movie;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Database.MovieDbHelper;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Database.MovieDbManager;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Logger;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.MainActivity;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.R;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;

/**
 * Created by user on 10/9/16.
 */

public class MovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<MovieResponse>{

    private static final String TAG = MovieFragment.class.getSimpleName();

    View rootView;
    MovieLoader mLoader;
    MovieResponse movie;
    boolean saved = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logger.d(TAG, "onCreateView");
        rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        Logger.d(TAG, "onResume");
        super.onResume();
    }

    public void updateContent(Bundle data) {
        if(data == null) {
            return;
        }
        movie = data.getParcelable("movie");
        if(movie == null) {
            return;
        }

        ((TextView)rootView.findViewById(R.id.movieTitle)).setText(movie.title);
        ((TextView)rootView.findViewById(R.id.movieYear)).setText(MovieDbHelper.getDateString(movie.releaseDate));
        ((TextView)rootView.findViewById(R.id.overview)).setText(movie.overview);
        rootView.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDbManager manager = new MovieDbManager(getContext());
                saved = !saved;
                manager.changeSaveState(movie, saved);
                updateFab(saved);
            }
        });
        mLoader.setMovieId(movie.movieDbId).forceLoad();
        Picasso.with(getContext()).load(movie.getBackdropUrl("w1280")).into((ImageView) rootView.findViewById(R.id.backdrop_image));
        Picasso.with(getContext()).load(movie.getPosterUrl("w342")).into((ImageView) rootView.findViewById(R.id.poster_image));
    }

    private void updateFab(boolean saved) {
        FloatingActionButton fab = ((FloatingActionButton) rootView.findViewById(R.id.fab));
        if(saved) {
            fab.setImageResource(R.drawable.ic_favorite_white_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_favorite_border_white_24dp);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Logger.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        Logger.d(TAG, "onInflate");
        super.onInflate(context, attrs, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        Logger.d(TAG, "onAttach");
        super.onAttach(context);
        mLoader = (MovieLoader) getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Logger.d(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        updateContent(savedInstanceState != null ? savedInstanceState : getArguments());
    }

    @Override
    public void onStart() {
        Logger.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onPause() {
        Logger.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Logger.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Logger.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Logger.d(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        Logger.d(TAG, "onDetach");
        super.onDetach();
    }

    @Override
    public Loader<MovieResponse> onCreateLoader(int id, Bundle args) {
        return new MovieLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<MovieResponse> loader, MovieResponse data) {
        saved = data != null;
        updateFab(saved);
    }

    @Override
    public void onLoaderReset(Loader<MovieResponse> loader) {
        ((MovieLoader)loader).setMovieId(movie.movieDbId);
    }

    static class MovieLoader extends AsyncTaskLoader<MovieResponse> {
        private long movieId;

        public MovieLoader(Context context) {
            super(context);
        }

        public MovieLoader setMovieId(long movieId) {
            this.movieId = movieId;
            return this;
        }

        @Override
        public MovieResponse loadInBackground() {
            return new MovieDbManager(getContext()).getMovie((int)movieId);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {

        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("movie", movie);
        super.onSaveInstanceState(outState);
    }
}
