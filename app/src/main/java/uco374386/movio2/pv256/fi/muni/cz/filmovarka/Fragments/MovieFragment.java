package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Fragments;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Database.MovieDbHelper;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Database.MovieDbManager;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.MainActivity;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.R;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;

/**
 * Created by user on 10/9/16.
 */

public class MovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<MovieResponse>{

    private static final String TAG = MovieFragment.class.getSimpleName();

    View rootView;
    MovieResponse movie;
    boolean saved = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        rootView = inflater.inflate(R.layout.fragment_movie, container, false);

        return rootView;
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        updateContent();
    }

    public void updateContent() {
        Bundle data = getArguments();
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
                if(saved) {
                    manager.createMovie(movie);
                } else {
                    manager.deleteMovie(movie);
                }
                updateViews(saved);
            }
        });
        getLoaderManager().initLoader(1, null, this).forceLoad();

        Picasso.with(getContext()).load(movie.getBackdropUrl("w1280")).into((ImageView) rootView.findViewById(R.id.backdrop_image));
        Picasso.with(getContext()).load(movie.getPosterUrl("w342")).into((ImageView) rootView.findViewById(R.id.poster_image));
    }

    private void updateViews(boolean saved) {
        FloatingActionButton fab = ((FloatingActionButton) rootView.findViewById(R.id.fab));
        if(saved) {
            fab.setImageResource(R.drawable.ic_favorite_white_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_favorite_border_white_24dp);
        }
        if(getActivity() instanceof MainActivity) {
            ListFragment list = (ListFragment)getFragmentManager().findFragmentById(R.id.list1);
            if(list instanceof SavedListFragment) {
                ((SavedListFragment) list).reload();
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        Log.d(TAG, "onInflate");
        super.onInflate(context, attrs, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
        super.onDetach();
    }

    @Override
    public Loader<MovieResponse> onCreateLoader(int id, Bundle args) {
        return new MovieLoader(getContext(), movie.movieDbId);
    }

    @Override
    public void onLoadFinished(Loader<MovieResponse> loader, MovieResponse data) {
        saved = data != null;
        updateViews(saved);
    }

    @Override
    public void onLoaderReset(Loader<MovieResponse> loader) {

    }

    static class MovieLoader extends AsyncTaskLoader<MovieResponse> {
        private long movieId;

        public MovieLoader(Context context, long movieId) {
            super(context);
            this.movieId = movieId;
        }

        @Override
        public MovieResponse loadInBackground() {
            return new MovieDbManager(getContext()).getMovie((int)movieId);
        }

    }
}
