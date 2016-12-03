package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.DownloadService;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.MainActivity;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.R;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;

/**
 * Created by user on 10/9/16.
 */

public class DiscoverListFragment extends ListFragment {

    public static final String EXTRA_SHOW_FIRST = "showFirst";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
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
                if(getArguments().getBoolean(EXTRA_SHOW_FIRST)) {
                    ((MainActivity) getActivity()).openDetails((MovieResponse) items.get(1));
                }
            }
        }
    };
}
