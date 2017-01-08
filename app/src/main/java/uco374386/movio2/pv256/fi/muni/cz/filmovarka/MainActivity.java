package uco374386.movio2.pv256.fi.muni.cz.filmovarka;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.Display;
import android.view.MenuItem;
import android.view.WindowManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String SECONDARY_THEME = "secondary_theme";
    public static final String PROP_DISABLED_CATEGORIES = "disabled_categories";
    public static final String EXTR_CAT_ID = "category_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
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

        Set<String> disabled = getDisabledCategories();
        for(int i = 0; i < categories_id.length; i++) {
            Menu menu = navigationView.getMenu().getItem(0).getSubMenu();
            MenuItem item = menu.add(categories_names[i]);
            item.setIntent(new Intent(categories_id[i]));
            setupNavItem(item, categories_id[i], disabled.contains(categories_id[i]));
        }

        if (findViewById(R.id.details) != null) {
            if(savedInstanceState != null) {
                return;
            }
            MovieFragment displayFrag = new MovieFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.details, displayFrag).commit();

        }
    }

    public void openDetails(MovieResponse movie) {
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

    public boolean isSystemOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    public Set<String> getDisabledCategories() {
        return new HashSet<>(getPreferences(MODE_PRIVATE).getStringSet(PROP_DISABLED_CATEGORIES, new HashSet<String>()));
    }

    public void setDisabledCategories(Set<String> list) {
        getPreferences(MODE_PRIVATE).edit().putStringSet(PROP_DISABLED_CATEGORIES, list).apply();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.d(TAG, "onNavigationItemSelected");
        String id = item.getIntent().getAction();
        Set<String> disabled = getDisabledCategories();
        boolean isDisabled = disabled.contains(id);
        setupNavItem(item, id, !isDisabled);
        if(isDisabled) {
            disabled.remove(id);
        } else {
            disabled.add(id);
        }
        setDisabledCategories(disabled);
        return false;
    }

    private void setupNavItem(MenuItem item, String catId, boolean disabled) {
        if(!disabled) {
            item.setChecked(true);
            item.setIcon(R.drawable.ic_done_black_24dp);
        } else {
            item.setChecked(false);
            item.setIcon(R.drawable.ic_clear_black_24dp);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onPostCreate");
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onPostResume() {
        Log.d(TAG, "onPostResume");
        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    public static int getDisplayWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static final String[] categories_id = {"28", "12", "16", "35", "80", "99", "18", "10751", "14", "36", "27", "10402", "9648",
            "10749", "878", "10770", "53", "10752", "37"};
    public static final String[] categories_names = {"Action", "Adventure", "Animation", "Comedy", "Crime", "Documentary", "Drama", "Family",
            "Fantasy", "History", "Horror", "Music", "Mystery", "Romance", "Science Fiction", "TV Movie", "Thriller", "War", "Western"};

}
