package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Database.MovieDbManager;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;

/**
 * Created by user on 12/1/16.
 */

public class SavedListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<MovieResponse>>{

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    public void reload() {
        getLoaderManager().initLoader(0, null, this).forceLoad();
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
        mAdapter.setItems(new ArrayList<Object>(data));
    }

    @Override
    public void onLoaderReset(Loader<List<MovieResponse>> loader) {
        mAdapter.setItems(new ArrayList<Object>());
    }


    static class MoviesLoader extends AsyncTaskLoader<List<MovieResponse>> {

        private List<MovieResponse> mData;
        public boolean hasResult = false;

        public MoviesLoader(Context context) {
            super(context);
        }

        @Override
        public List<MovieResponse> loadInBackground() {
            return new MovieDbManager(getContext()).getAll();
        }

    }
}
