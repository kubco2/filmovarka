package uco374386.movio2.pv256.fi.muni.cz.filmovarka;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.Arrays;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieListResponse;

/**
 * Created by user on 10/9/16.
 */

public class ListFragment extends android.support.v4.app.Fragment {

    private static final String TAG = ListFragment.class.getSimpleName();

    protected RecyclerView mRecyclerView;
    protected MoviesAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected AsyncTask loaderTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        final View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvMovies);
        setRecyclerViewLayoutManager();

        mAdapter = new MoviesAdapter(getContext(), new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);

        loaderTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    MovieListResponse movieList1 = MovieDbService.getInstance().getMostPopularMovies();
                    MovieListResponse movieList2 = MovieDbService.getInstance().getMostVotedMovies();
                    final ArrayList<Object> items = new ArrayList<>(14);
                    items.add(getResources().getString(R.string.sectionMostPopular));
                    items.addAll(Arrays.asList(movieList1.results));
                    items.add(getResources().getString(R.string.sectionMostVoted));
                    items.addAll(Arrays.asList(movieList2.results));
                    ListFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!((MainActivity)getActivity()).isSystemOnline()) {
                                mRecyclerView.setVisibility(View.GONE);
                                rootView.findViewById(R.id.empty_view_no_internet).setVisibility(View.VISIBLE);
                            } else if(items.size() <= 2) {
                                mRecyclerView.setVisibility(View.GONE);
                                rootView.findViewById(R.id.empty_view_no_data).setVisibility(View.VISIBLE);
                            } else {
                                mAdapter.setItems(items);
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        loaderTask.execute();

        return rootView;
    }

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
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
        loaderTask.cancel(true);
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
