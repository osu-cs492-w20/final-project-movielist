package com.example.movielist;
import java.util.List;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.google.android.material.snackbar.Snackbar;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

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
        //End test-----------------------------

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
                }
                return true;
            }
        });

    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
    itemTouchHelper.attachToRecyclerView(mCreatedUserListRV);

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int position = viewHolder.getAdapterPosition();
            final CreatedUserList deletedList;
           deletedList = mCreatedUserListAdapter.returnListFromPosition(position);
            switch (direction){
                case ItemTouchHelper.LEFT:
                    savedVM.deleteList(deletedList);
                    mCreatedUserListAdapter.notifyItemRemoved(position);
                    Snackbar.make(mCreatedUserListRV, deletedList.list_title, Snackbar.LENGTH_LONG)
                            .setAction("Undo and Insert at Bottom", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    savedVM.insertUserList(deletedList);
                                   // mCreatedUserListAdapter.notifyItemInserted(position);
                                }
                            }).show();
                    break;
            }

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark ))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        }
    };

    //After going back to main activity rerun animation
    @Override
    protected void onResume(){
        super.onResume();
        runLayoutAnimation(mCreatedUserListRV);
    }

    //Add animation when back button pressed

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                //Intent settingsIntent = new Intent(this, SettingsActivity.class);
                //startActivity(settingsIntent);

                //For testing name the list Bob
                //clicker(iterator);
                
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

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent);
        }
        else{
            startActivity(intent);
        }
    }


    //For testing insertions
    public void clicker(int i){
        Movies movie= new Movies();
        movie.movie_title = "Banachocula - " + i;
        movie.movie_id = 1245534 + i;
        movie.movie_list_title = "Sean";
        savedVM.insertMovie(movie);
    }
}
