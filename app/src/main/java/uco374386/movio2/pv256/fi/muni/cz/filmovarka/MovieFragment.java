package uco374386.movio2.pv256.fi.muni.cz.filmovarka;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by user on 10/9/16.
 */

public class MovieFragment extends Fragment {

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        return rootView;
    }

    public void updateContent(Movie movie) {
        TextView titleB = (TextView)rootView.findViewById(R.id.titleB);
        TextView coverB = (TextView)rootView.findViewById(R.id.coverB);
        titleB.setText(movie.title);
        coverB.setText(movie.coverPath);
    }
}
