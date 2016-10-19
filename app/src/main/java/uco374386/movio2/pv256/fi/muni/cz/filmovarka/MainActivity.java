package uco374386.movio2.pv256.fi.muni.cz.filmovarka;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public static final String SECONDARY_THEME = "secondary_theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean alternative = this.getPreferences(Context.MODE_PRIVATE).getBoolean(MainActivity.SECONDARY_THEME, false);
        Log.d("MainActivity", "Alternative theme " + alternative);
        if (alternative) {
            MainActivity.this.setTheme(R.style.AppThemeSecond);
        } else {
            MainActivity.this.setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (findViewById(R.id.details) != null) {
            if(savedInstanceState != null) {
                return;
            }
            MovieFragment displayFrag = new MovieFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.details, displayFrag).commit();

        }
    }

    public void openDetails(Movie movie) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MovieFragment displayFrag = (MovieFragment) fragmentManager.findFragmentById(R.id.details);

        if (displayFrag == null) {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        } else {
            displayFrag = new MovieFragment();
            Bundle data = new Bundle();
            data.putParcelable("movie", movie);
            displayFrag.setArguments(data);
            fragmentTransaction.replace(R.id.details, displayFrag);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
