package com.example.movielist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.movielist.SearchActivity;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mMovieListRV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Remove shadow under action bar.
         getSupportActionBar().setElevation(0);

        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        mMovieListRV = findViewById(R.id.rv_movie_list);


        //Instantiates the bottom nav bar and creates a listener just like options selected
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search_movie:
                        Intent searchIntent = new Intent(MainActivity.this,SearchActivity.class);
                        startActivity(searchIntent);
                        return true;
                    case R.id.favorite_list:
                        //Here we can have "favorite list intent activity to seperate it or w/e
                        return true;
                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
