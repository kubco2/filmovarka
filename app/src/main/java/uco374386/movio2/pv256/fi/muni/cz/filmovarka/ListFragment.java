package uco374386.movio2.pv256.fi.muni.cz.filmovarka;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;

/**
 * Created by user on 10/9/16.
 */

public class ListFragment extends android.support.v4.app.Fragment {

    private static final String TAG = ListFragment.class.getSimpleName();

    protected RecyclerView mRecyclerView;
    protected MoviesAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    private View rootView;
    private ArrayList<Object> items = new ArrayList<>(14);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        final View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        rootView.setTag(TAG);

        if(!((MainActivity)getActivity()).isSystemOnline()) {
            mRecyclerView.setVisibility(View.GONE);
            rootView.findViewById(R.id.empty_view_no_internet).setVisibility(View.VISIBLE);
        }

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvMovies);
        setRecyclerViewLayoutManager();

        mAdapter = new MoviesAdapter(getContext(), new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);

        this.rootView = rootView;

        Intent intent = new Intent(getContext(), DownloadService.class);
        intent.setAction(DownloadService.ACTION_DOWNLOAD_LIST_MOST_POPULAR);
        getContext().startService(intent);
        intent = new Intent(getContext(), DownloadService.class);
        intent.setAction(DownloadService.ACTION_DOWNLOAD_LIST_MOST_VOTED);
        getContext().startService(intent);

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

        IntentFilter intentFilter = new IntentFilter(DownloadService.DOWNLOAD_SERVICE_INTENT);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMovieListReceiver, intentFilter);
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
        Intent intent = new Intent(getContext(), DownloadService.class);
        getContext().stopService(intent);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMovieListReceiver);
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

    private BroadcastReceiver mMovieListReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String error = intent.getStringExtra(DownloadService.EXTRA_RESPONSE_ERROR);
            if(error != null) {
                switch (error) {
                    case DownloadService.RESPONSE_ERROR_OFFLINE:
                        mRecyclerView.setVisibility(View.GONE);
                        rootView.findViewById(R.id.empty_view_no_internet).setVisibility(View.VISIBLE);
                        break;
                    case DownloadService.RESPONSE_ERROR_PARSE:
                        mRecyclerView.setVisibility(View.GONE);
                        rootView.findViewById(R.id.empty_view_parse_error).setVisibility(View.VISIBLE);
                        break;
                }
                return;
            }
            String action = intent.getStringExtra(DownloadService.EXTRA_ACTION);
            ArrayList<MovieResponse> movies = intent.getExtras().getParcelableArrayList(DownloadService.EXTRA_RESPONSE);
            switch (action) {
                case DownloadService.ACTION_DOWNLOAD_LIST_MOST_POPULAR:
                    items.add(getResources().getString(R.string.sectionMostPopular));
                    items.addAll(movies);
                    break;
                case DownloadService.ACTION_DOWNLOAD_LIST_MOST_VOTED:
                    items.add(getResources().getString(R.string.sectionMostVoted));
                    items.addAll(movies);
                    break;
            }
            if(items.size() <= 2) {
                mRecyclerView.setVisibility(View.GONE);
                rootView.findViewById(R.id.empty_view_no_data).setVisibility(View.VISIBLE);
            } else {
                mAdapter.setItems(items);
            }
        }
    };
}
