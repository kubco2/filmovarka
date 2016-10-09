package uco374386.movio2.pv256.fi.muni.cz.filmovarka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        MovieFragment displayFrag = (MovieFragment) getSupportFragmentManager()
                .findFragmentById(R.id.details);
        displayFrag.updateContent((Movie)getIntent().getParcelableExtra("movie"));
    }
}
