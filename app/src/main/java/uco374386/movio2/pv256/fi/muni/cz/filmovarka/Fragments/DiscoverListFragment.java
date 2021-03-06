package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.DownloadService;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Logger;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.MainActivity;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.R;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;

/**
 * Created by user on 10/9/16.
 */

public class DiscoverListFragment extends ListFragment {
    protected static final String TAG = DiscoverListFragment.class.getSimpleName();
    public static final String EXTRA_SHOW_FIRST = "showFirst";
    public static final String EXTRA_LOAD_NEXT = "loadNext";
    private boolean loadNext = false;
    @Override
    public void onAttach(Context context) {
        Logger.d(TAG, "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            loadNext = savedInstanceState.getBoolean(EXTRA_LOAD_NEXT);
        } else {
            loadNext = getArguments().getBoolean(EXTRA_SHOW_FIRST);
        }
        IntentFilter intentFilter = new IntentFilter(DownloadService.DOWNLOAD_SERVICE_INTENT);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMovieListReceiver, intentFilter);
    }

    @Override
    public void onStart() {
        super.onStart();
        reload();
    }

    @Override
    public void onDetach() {
        Logger.d(TAG, "onDetach");
        Intent intent = new Intent(getContext(), DownloadService.class);
        getContext().stopService(intent);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMovieListReceiver);
        super.onDetach();
    }

    public void reload() {
        mAdapter.reset();
        Intent intent = new Intent(getContext(), DownloadService.class);
        intent.setAction(DownloadService.ACTION_DOWNLOAD_LIST_MOST_POPULAR);
        getContext().startService(intent);
        intent = new Intent(getContext(), DownloadService.class);
        intent.setAction(DownloadService.ACTION_DOWNLOAD_LIST_MOST_VOTED);
        getContext().startService(intent);
    }

    protected BroadcastReceiver mMovieListReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String error = intent.getStringExtra(DownloadService.EXTRA_RESPONSE_ERROR);
            if(error != null) {
                mRecyclerView.setVisibility(View.GONE);
                rootView.findViewById(R.id.empty_view_parse_error).setVisibility(View.VISIBLE);
            }
            ArrayList<Object> items = new ArrayList<>();
            List<MovieResponse> movies = intent.getExtras().getParcelableArrayList(DownloadService.EXTRA_RESPONSE);
            if(movies == null || movies.isEmpty()) {
                if(mAdapter.getItemCount() == 0) {
                    mRecyclerView.setVisibility(View.GONE);
                    rootView.findViewById(R.id.empty_view_no_data).setVisibility(View.VISIBLE);
                }
                return;
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.empty_view_no_data).setVisibility(View.GONE);
            }
            items.add(intent.getStringExtra(DownloadService.EXTRA_SECTION));
            items.addAll(movies.subList(0,movies.size() > 6 ? 6 : movies.size()));
            mAdapter.addItems(items);
            if(loadNext) {
                loadNext = false;
                mCallback.onItemClicked((MovieResponse) items.get(1));
            }
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EXTRA_LOAD_NEXT, loadNext);
        super.onSaveInstanceState(outState);
    }
}
