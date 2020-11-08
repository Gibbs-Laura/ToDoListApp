package com.example.todolistapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.todolistapp.database.AppDatabase;
import com.example.todolistapp.database.Item;
import java.util.List;


public class MainActivity extends AppCompatActivity implements Adapter.ItemClickListener {

    private RecyclerView recyclerView;
    private Adapter adapter;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the RecyclerView to its corresponding view
        recyclerView = findViewById(R.id.recyclerView);

        // Set the layout for the RecyclerView to be a linear layout, which measures and
        // positions items within a RecyclerView into a linear list
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and attach it to the RecyclerView
        adapter = new Adapter(this, this);
        recyclerView.setAdapter(adapter);

      /*  DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mRecyclerView.addItemDecoration(decoration); */

        /*
         Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<Item> items = adapter.getTasks();
                        appDatabase.taskDao().deleteTask(items.get(position));
                    }
                });
            }
        }).attachToRecyclerView(recyclerView);



        appDatabase = AppDatabase.getInstance(getApplicationContext());
        setupViewModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.menu_add) {
            startActivity(new Intent(this, AddItem.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getTasks().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                adapter.setTasks(items);
            }
        });
    }



    @Override
    public void onItemClickListener(int itemId) {
        // Launch AddItem adding the itemId as an extra in the intent
        Intent intent = new Intent(MainActivity.this, AddItem.class);
        intent.putExtra(AddItem.EXTRA_TASK_ID, itemId);
        startActivity(intent);
    }


}
