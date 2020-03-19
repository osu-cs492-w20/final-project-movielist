package com.example.movielist;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movielist.ListActivity.ListActivity;
import com.example.movielist.SearchActivity.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.movielist.data.CreatedUserList;
import com.example.movielist.data.Movies;

public class MainActivity extends AppCompatActivity implements CreatedUserListAdapter.CreatedUserListClickListener {
    private RecyclerView mCreatedUserListRV;
    private SavedListViewModel savedVM;
    private CreatedUserListAdapter mCreatedUserListAdapter;
    private int iterator = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Remove shadow under action bar.
         getSupportActionBar().setElevation(0);

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        mCreatedUserListRV = findViewById(R.id.rv_movie_list);
        mCreatedUserListAdapter = new CreatedUserListAdapter(this);
        mCreatedUserListRV.setAdapter(mCreatedUserListAdapter);
        mCreatedUserListRV.setLayoutManager(new LinearLayoutManager(this));
        mCreatedUserListRV.setHasFixedSize(true);
        //ViewModel
        savedVM = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(SavedListViewModel.class);

        savedVM.getAllLists().observe(this, new Observer<List<CreatedUserList>>() {
            @Override
            public void onChanged(List<CreatedUserList> createdUserLists) {
                mCreatedUserListAdapter.updateCreatedUserList(createdUserLists);
            }
        });

        //Instantiates the bottom nav bar and creates a listener just like options selected
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_add:
                        addList(MainActivity.this);
                        return true;
                    case R.id.search_movie:
                        Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                //Intent settingsIntent = new Intent(this, SettingsActivity.class);
                //startActivity(settingsIntent);

                //For testing name the list Bob
                clicker(iterator);
                return true;
            default:
                return false;
        }
    }

    //The function that prompts the user to add a new list
    private void addList(Context c){

        final CreatedUserList createdUserList = new CreatedUserList();

            //Edit text box------------------------------------------------------------------
            final EditText taskEditText = new EditText(c);
            AlertDialog dialog = new AlertDialog.Builder(c)
                    .setTitle("Create a new list")
                    .setMessage("What do you want to name it?")
                    .setView(taskEditText)
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String task = String.valueOf(taskEditText.getText());
                            //Implement adding list
                            createdUserList.list_title = task;
                            createdUserList.user_name = "sean";
                            savedVM.insertUserList(createdUserList);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();
            //Edit text box end---------------------------------------------------------------
    }
  
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onCreatedUserListsClick(CreatedUserList createdUserList) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra(ListActivity.EXTRA_LIST_OBJECT, createdUserList);
        startActivity(intent);
    }


    //For testing insertions
    public void clicker(int i){
        Movies movie= new Movies();
        movie.movie_title = "Friday";
        movie.movie_id = 10634;
        movie.movie_poster_URL = "/isWyP4jWmcCHjUcanVwGBDwVSsA.jpg";
        movie.movie_votes = 932;
        movie.movie_release_date = "1995-04-26";
        movie.movie_language = "en";
        movie.movie_imdb_link = "https://www.imdb.com/title/tt0113118/?ref_=fn_al_tt_1"; //TODO needs to be grabbed during api call or at movie creation
        movie.movie_overview = "Craig and Smokey are two guys in Los Angeles hanging out on their porch on a Friday afternoon, smoking and drinking, looking for something to do.";
        movie.movie_list_title = "Bob";
        movie.movie_completion_status = false;
        savedVM.insertMovie(movie);
        /*movie.movie_title = "Freaky Friday";
        movie.movie_id = 10330;
        movie.movie_poster_URL = "/w6TR7kTsKmlBYps2i5hzoukBLCn.jpg";
        movie.movie_votes = 2304;
        movie.movie_release_date = "2003-08-03";
        movie.movie_language = "en";
        movvie.movie_imdb_link = "https://www.imdb.com/title/tt0322330/?ref_=fn_al_tt_1";
        movie.movie_overview = "Mother and daughter bicker over everything -- what Anna wears, whom she likes and what she wants to do when she's older. In turn, Anna detests Tess's fiancé. When a magical fortune cookie switches their personalities, they each get a peek at how the other person feels, thinks and lives.";
        movie.movie_list_title = "Bob";
        movie.movie_completion_status = false;
        savedVM.insertMovie(movie);*/
    }
}
