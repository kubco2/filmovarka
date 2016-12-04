package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.DownloadService;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.MainActivity;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.R;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;

/**
 * Created by user on 10/9/16.
 */

public class DiscoverListFragment extends ListFragment {
    protected static final String TAG = DiscoverListFragment.class.getSimpleName();
    public static final String EXTRA_SHOW_FIRST = "showFirst";
    private boolean loadNext = false;
    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach");
        super.onAttach(context);
        loadNext = getArguments().getBoolean(EXTRA_SHOW_FIRST);
        IntentFilter intentFilter = new IntentFilter(DownloadService.DOWNLOAD_SERVICE_INTENT);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMovieListReceiver, intentFilter);
        Intent intent = new Intent(getContext(), DownloadService.class);
        intent.setAction(DownloadService.ACTION_DOWNLOAD_LIST_MOST_POPULAR);
        getContext().startService(intent);
        intent = new Intent(getContext(), DownloadService.class);
        intent.setAction(DownloadService.ACTION_DOWNLOAD_LIST_MOST_VOTED);
        getContext().startService(intent);
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
        Intent intent = new Intent(getContext(), DownloadService.class);
        getContext().stopService(intent);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMovieListReceiver);
        super.onDetach();
    }

    protected BroadcastReceiver mMovieListReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String error = intent.getStringExtra(DownloadService.EXTRA_RESPONSE_ERROR);
            if(error != null) {
                mRecyclerView.setVisibility(View.GONE);
                rootView.findViewById(R.id.empty_view_parse_error).setVisibility(View.VISIBLE);
            }
            List<MovieResponse> movies = intent.getExtras().getParcelableArrayList(DownloadService.EXTRA_RESPONSE);
            if(movies == null || movies.isEmpty()) {
                if(items.isEmpty()) {
                    mRecyclerView.setVisibility(View.GONE);
                    rootView.findViewById(R.id.empty_view_no_data).setVisibility(View.VISIBLE);
                }
                return;
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.empty_view_no_data).setVisibility(View.GONE);
            }
            items.add(intent.getStringExtra(DownloadService.EXTRA_SECTION));
            items.addAll(movies);
            mAdapter.setItems(items);
            if(loadNext) {
                loadNext = false;
                mCallback.onItemClicked((MovieResponse) items.get(1));
            }
        }
    };
}
