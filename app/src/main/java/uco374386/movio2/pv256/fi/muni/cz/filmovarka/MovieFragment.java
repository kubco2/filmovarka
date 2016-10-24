package uco374386.movio2.pv256.fi.muni.cz.filmovarka;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;

/**
 * Created by user on 10/9/16.
 */

public class MovieFragment extends Fragment {

    private static final String TAG = MovieFragment.class.getSimpleName();

    View rootView;

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
        MovieResponse movie = data.getParcelable("movie");
        if(movie == null) {
            return;
        }

        TextView titleB = (TextView)rootView.findViewById(R.id.movieTitle);
        titleB.setText(movie.title);

        Picasso.with(getContext()).load(movie.getBackdropUrl("w1280")).into((ImageView) rootView.findViewById(R.id.backdrop_image));
        Picasso.with(getContext()).load(movie.getPosterUrl("w1280")).into((ImageView) rootView.findViewById(R.id.poster_image));
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
}
