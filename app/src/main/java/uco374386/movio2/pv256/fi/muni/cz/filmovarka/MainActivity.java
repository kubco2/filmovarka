package uco374386.movio2.pv256.fi.muni.cz.filmovarka;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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

        RecyclerView rvMovies = (RecyclerView) findViewById(R.id.rvMovies);

        List<Movie> movies = new ArrayList<Movie>();
        for(int i = 0; i < 30; i++) {
            movies.add(new Movie("Movie name " + i, (double)i));
        }

        MoviesAdapter adapter = new MoviesAdapter(this, movies);
        rvMovies.setAdapter(adapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        Button themeButton = (Button) findViewById(R.id.button);
        themeButton.setOnClickListener(new ThemeSwitcherListener());
    }

    public class ThemeSwitcherListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            SharedPreferences prefs = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
            boolean alternative = prefs.getBoolean(MainActivity.SECONDARY_THEME, false);
            SharedPreferences.Editor editor = prefs.edit();
            if (alternative) {
                editor.putBoolean(MainActivity.SECONDARY_THEME, false);
            } else {
                editor.putBoolean(MainActivity.SECONDARY_THEME, true);
            }
            editor.commit();
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, MainActivity.class);
            MainActivity.this.startActivity(intent);
            MainActivity.this.finish();
        }
    }
}
