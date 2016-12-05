package uco374386.movio2.pv256.fi.muni.cz.filmovarka;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Fragments.DiscoverListFragment;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Fragments.ListFragment;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Fragments.MovieFragment;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Fragments.SavedListFragment;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses.MovieResponse;
import uco374386.movio2.pv256.fi.muni.cz.filmovarka.Sync.UpdaterSyncAdapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ListFragment.ListClickable {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String SECONDARY_THEME = "secondary_theme";
    public static final String PROP_DISABLED_CATEGORIES = "disabled_categories";
    public static final String EXTR_CAT_ID = "category_id";
    public static final String EXTRA_OPEN_SAVED = "openSaved";
    public boolean openSaved = false;
    public boolean tablet = false;
    public boolean firstLoad = true;
    private boolean selectedCategoriesChanged = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        openSaved = getIntent().getExtras() != null && getIntent().getExtras().getBoolean(EXTRA_OPEN_SAVED, false);
        UpdaterSyncAdapter.initializeSyncAdapter(this);
        boolean alternative = BuildConfig.APPLICATION_ID.endsWith(".paid");
        Logger.d(TAG, "Alternative theme " + alternative);
        if (alternative) {
            MainActivity.this.setTheme(R.style.AppThemeSecond);
        } else {
            MainActivity.this.setTheme(R.style.AppThemeFirst);
        }
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                ListFragment listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.list1);
                if(listFragment != null && listFragment instanceof DiscoverListFragment && selectedCategoriesChanged) {
                    selectedCategoriesChanged = false;
                    ((DiscoverListFragment) listFragment).reload();
                }
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Switch switchButton = ((Switch)findViewById(R.id.saved));
        switchButton.setChecked(openSaved);
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSaved = ((Switch)v).isChecked();
                updateListFragment(toggle);
            }
        });

        Set<String> disabled = getDisabledCategories();
        for(int i = 0; i < categories_id.length; i++) {
            Menu menu = navigationView.getMenu().getItem(0).getSubMenu();
            MenuItem item = menu.add(categories_names[i]);
            item.setIntent(new Intent(categories_id[i]));
            setupNavItem(item, categories_id[i], disabled.contains(categories_id[i]));
        }

        if(savedInstanceState != null) {
            return;
        }
        if (findViewById(R.id.details) != null) {
            tablet = true;
            MovieFragment displayFrag = new MovieFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.details, displayFrag).commit();
        }
        updateListFragment(toggle);
        firstLoad = false;
    }

    private void updateListFragment(ActionBarDrawerToggle toggle) {
        Fragment list;
        ImageButton btn = (ImageButton)findViewById(R.id.refresh);

        if(!isSystemOnline()) {
            findViewById(R.id.list1).setVisibility(View.GONE);
            findViewById(R.id.empty_view_no_internet).setVisibility(View.VISIBLE);
            return;
        } else {
            findViewById(R.id.list1).setVisibility(View.VISIBLE);
            findViewById(R.id.empty_view_no_internet).setVisibility(View.GONE);
        }

        if(openSaved) {
            list = new SavedListFragment();
            btn.setVisibility(View.VISIBLE);
            btn.setImageResource(R.drawable.ic_refresh_white_24dp);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.i(TAG, "requestSync");
                    UpdaterSyncAdapter.syncImmediately(getApplicationContext());
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.request_sync, Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
            toggle.setDrawerIndicatorEnabled(false);
        } else {
            list = new DiscoverListFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean(DiscoverListFragment.EXTRA_SHOW_FIRST, firstLoad && tablet);
            list.setArguments(bundle);
            btn.setVisibility(View.GONE);
            btn.setOnClickListener(null);
            toggle.setDrawerIndicatorEnabled(true);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.list1, list).commit();
    }

    public void openDetails(MovieResponse movie) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        MovieFragment displayFrag = (MovieFragment) fragmentManager.findFragmentById(R.id.details);
        if (displayFrag == null) {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("movie", movie);
            intent.putExtra(EXTRA_OPEN_SAVED, openSaved);
            finish();
            startActivity(intent);
        } else {
            Bundle data = new Bundle();
            data.putParcelable("movie", movie);
            displayFrag.updateContent(data);
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
        Logger.d(TAG, "onNavigationItemSelected");
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
        selectedCategoriesChanged = true;
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

    public static final String[] categories_id = {"28", "12", "16", "35", "80", "99", "18", "10751", "14", "36", "27", "10402", "9648",
            "10749", "878", "10770", "53", "10752", "37"};
    public static final String[] categories_names = {"Action", "Adventure", "Animation", "Comedy", "Crime", "Documentary", "Drama", "Family",
            "Fantasy", "History", "Horror", "Music", "Mystery", "Romance", "Science Fiction", "TV Movie", "Thriller", "War", "Western"};

    @Override
    public void onItemClicked(MovieResponse movie) {
        openDetails(movie);
    }
}
