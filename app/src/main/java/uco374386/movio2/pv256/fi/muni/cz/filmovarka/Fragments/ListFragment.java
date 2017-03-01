package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.DownloadService;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Logger;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.MainActivity;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.R;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;

/**
 * Created by user on 12/1/16.
 */
public class ListFragment extends android.support.v4.app.Fragment {
    protected static final String TAG = ListFragment.class.getSimpleName();
    protected RecyclerView mRecyclerView;
    protected MoviesAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected View rootView;
    protected ListClickable mCallback;

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        Logger.d(TAG, "onInflate");
        super.onInflate(context, attrs, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        Logger.d(TAG, "onAttach");
        super.onAttach(context);
        try {
            mCallback = (ListClickable) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ListClickable");
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Logger.d(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        Logger.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Logger.d(TAG, "onResume");
        super.onResume();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logger.d(TAG, "onCreateView");

        final View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvMovies);
        setRecyclerViewLayoutManager();

        mAdapter = new MoviesAdapter(getContext(), new ArrayList<>(), mCallback);
        mRecyclerView.setAdapter(mAdapter);

        this.rootView = rootView;
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Logger.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    public interface ListClickable {
        void onItemClicked(MovieResponse movie);
    }
}
