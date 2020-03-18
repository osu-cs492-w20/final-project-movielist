package com.example.movielist;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.movielist.SearchActivity;
import com.example.movielist.data.CreatedUserList;
import com.example.movielist.data.Movies;
import com.google.android.material.navigation.NavigationView;

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

        //Uncomment to Start test---------------------------
        //testSQLQuieries();
        //End test-----------------------------

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                //put "addList()" here once implemented
                addList(this);

                return true;
            case R.id.action_settings:
                //Intent settingsIntent = new Intent(this, SettingsActivity.class);
                //startActivity(settingsIntent);

                //For testing name the list Bob
                clicker(iterator);
                
                iterator++;
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

    private void testSQLQuieries(){

       /* savedVM = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(SavedListViewModel.class);*/

        Movies test1 = new Movies();
        CreatedUserList test2 = new CreatedUserList();
        test2.list_title = "1M2234yLis451t12asd";
        test2.user_name = "Sean1231121123";
        savedVM.insertUserList(test2);
        test1.movie_title = "Banachocula - 2";
        test1.movie_id = 1245534;
        test1.movie_list_title = "1M2234yLis451t12asd";
        savedVM.insertMovie(test1);
        test1.movie_title = "Banachocula - 3";
        test1.movie_id = 1245334;
        test1.movie_list_title = "1M2234yLis451t12asd";
        savedVM.insertMovie(test1);
        test1.movie_title = "Banachocula - 4";
        test1.movie_id = 1245434;
        test1.movie_list_title = "1M2234yLis451t12asd";
        savedVM.insertMovie(test1);



        savedVM.insertMovie(test1);
        final Observer<List<Movies>> testObv = new Observer<List<Movies>>() {
            @Override
            public void onChanged(List<Movies> movies) {
                Log.d("TEST", "onChanged Movies: " + movies.get(0).movie_title);
            }
        };

        final Observer<List<CreatedUserList>> testObv2 = new Observer<List<CreatedUserList>>() {
            @Override
            public void onChanged(List<CreatedUserList> movies) {
                mCreatedUserListAdapter.updateCreatedUserList(movies);
                Log.d("TEST", "onChanged CreatedUserList: " + movies.get(0).list_title);
            }
        };

        test1.movie_list_title = "MyLis1t12";
        test1.movie_id = 1652;
        test1.movie_title = "BooBerryCrunch - 3";
        String listName = "1M2234yLis451t12asd";
        savedVM.getAllMovies().observe(this, testObv);
        savedVM.getListOfMovies(listName).observe(this, testObv);
        savedVM.getAllLists().observe(this, testObv2);

        //savedVM.deleteList(test2);

        /*savedVM.getAllMovies().observe(this, testObv);
        savedVM.getListOfMovies(321).observe(this, testObv);
        savedVM.getAllLists().observe(this, testObv2);*/
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

    public void clicker(int i){
        Movies movie= new Movies();
        movie.movie_title = "Banachocula - " + i;
        movie.movie_id = 1245534 + i;
        movie.movie_list_title = "Bob";
        savedVM.insertMovie(movie);
    }
}
