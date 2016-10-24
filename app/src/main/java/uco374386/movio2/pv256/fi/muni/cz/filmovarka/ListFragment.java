package uco374386.movio2.pv256.fi.muni.cz.filmovarka;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieListResponse;

/**
 * Created by user on 10/9/16.
 */

public class ListFragment extends android.support.v4.app.Fragment {

    private static final String TAG = ListFragment.class.getSimpleName();

    protected RecyclerView mRecyclerView;
    protected MoviesAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected MovieListResponse movieList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

//        if(movieList == null || movieList.results.length == 0) {
//            View rootView = inflater.inflate(R.layout.fragment_list_empty, container, false);
//            rootView.setTag(TAG);
//            return rootView;
//        }
//        if(!((MainActivity)getActivity()).isSystemOnline()) {
//            View rootView = inflater.inflate(R.layout.fragment_list_offline, container, false);
//            rootView.setTag(TAG);
//            return rootView;
//        }

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvMovies);
        setRecyclerViewLayoutManager();

        mAdapter = new MoviesAdapter(getContext(), movieList);
        mRecyclerView.setAdapter(mAdapter);

        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    movieList = MovieDbService.getInstance().getMostPopularMovies();
                    ListFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setMovieList(movieList);
                        }
                    });
                    //ListFragment.this.mRecyclerView.refreshDrawableState();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        task.execute();

        return rootView;
    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     */
    public void setRecyclerViewLayoutManager() {
        int scrollPosition = 0;
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
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
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
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
}
