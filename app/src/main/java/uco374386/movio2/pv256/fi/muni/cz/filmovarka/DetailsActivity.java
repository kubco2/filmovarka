package uco374386.movio2.pv256.fi.muni.cz.filmovarka;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Fragments.MovieFragment;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.d(TAG, "onCreate");
        boolean alternative = BuildConfig.APPLICATION_ID.endsWith(".paid");
        Logger.d(TAG, "Alternative theme " + alternative);
        if (alternative) {
            DetailsActivity.this.setTheme(R.style.AppThemeSecond);
        } else {
            DetailsActivity.this.setTheme(R.style.AppThemeFirst);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(savedInstanceState != null) {
            return;
        }
        ((MovieFragment)getSupportFragmentManager().findFragmentById(R.id.details)).updateContent(getIntent().getExtras());

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        Logger.d(TAG, "onPostCreate");
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        Logger.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onPostResume() {
        Logger.d(TAG, "onPostResume");
        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        Logger.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Logger.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Logger.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Logger.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.EXTRA_OPEN_SAVED, getIntent().getExtras().getBoolean(MainActivity.EXTRA_OPEN_SAVED));
        finish();
        startActivity(intent);
    }


}
