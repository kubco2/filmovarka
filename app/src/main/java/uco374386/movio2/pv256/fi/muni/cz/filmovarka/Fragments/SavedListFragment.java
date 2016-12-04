package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Fragments;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Database.MovieDbManager;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.R;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;

/**
 * Created by user on 12/1/16.
 */

public class SavedListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<MovieResponse>>{
    protected static final String TAG = SavedListFragment.class.getSimpleName();


    @Override
    public void onResume() {
        super.onResume();
        reload();
        getContext().getContentResolver().registerContentObserver(MovieResponse.MovieEntry.CONTENT_URI, true, mObserver);
    }

    @Override
    public void onDetach() {
        getContext().getContentResolver().unregisterContentObserver(mObserver);
        super.onDetach();
    }

    public void reload() {
        if(getContext() != null) {
            getLoaderManager().initLoader(0, null, this).forceLoad();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public Loader<List<MovieResponse>> onCreateLoader(int id, Bundle args) {
        return new MoviesLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<MovieResponse>> loader, List<MovieResponse> data) {
        if(data.isEmpty()) {
            mAdapter.setItems(new ArrayList<Object>(Arrays.asList(new String[] {getResources().getString(R.string.no_data)})));
        } else {
            mAdapter.setItems(new ArrayList<Object>(data));
        }
    }

    @Override
    public void onLoaderReset(Loader<List<MovieResponse>> loader) {
        mAdapter.setItems(new ArrayList<Object>());
    }


    static class MoviesLoader extends AsyncTaskLoader<List<MovieResponse>> {

        public MoviesLoader(Context context) {
            super(context);
        }

        @Override
        public List<MovieResponse> loadInBackground() {
            return new MovieDbManager(getContext()).getAll();
        }
    }

    private ContentObserver mObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            reload();
        }
    };
}
