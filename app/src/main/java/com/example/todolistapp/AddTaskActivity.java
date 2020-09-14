package com.example.todolistapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.todolistapp.database.AppDatabase;
import com.example.todolistapp.database.TaskEntry;

import java.util.Date;


public class AddTaskActivity extends AppCompatActivity  {

    // Extra for the task ID to be received in the intent
    public static final String EXTRA_TASK_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    // Constants for priority
    /*public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;*/

    public static final String DONE = "DONE";
    public static final String IN_PROGRESS = "IN PROGRESS";
    public static final String LATE = "LATE";

    public static final String WORK = "WORK";
    public static final String SCHOOL = "SCHOOL";
    public static final String HOME= "HOME";
    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_TASK_ID = -1;
    // Constant for logging
    private static final String TAG = AddTaskActivity.class.getSimpleName();
    // Fields for views
    EditText editText;
    EditText mEditText1;
    // Spinner mEditText1;
    RadioGroup radioGroup1;
    RadioGroup radioGroup2;
   // Button mButton;

    private int mTaskId = DEFAULT_TASK_ID;

    // Member variable for the Database
    private AppDatabase db;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        initViews();

        db = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            // mButton.setText(R.string.update_button);
            if (mTaskId == DEFAULT_TASK_ID) {
                // populate the UI
                mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);

                // COMPLETED (9) Remove the logging and the call to loadTaskById, this is done in the ViewModel now
                // COMPLETED (10) Declare a AddTaskViewModelFactory using mDb and mTaskId
                AddTaskViewModelFactory factory = new AddTaskViewModelFactory(db, mTaskId);
                // COMPLETED (11) Declare a AddTaskViewModel variable and initialize it by calling ViewModelProviders.of
                // for that use the factory created above AddTaskViewModel
                final AddTaskViewModel viewModel
                        = ViewModelProviders.of(this, factory).get(AddTaskViewModel.class);

                // COMPLETED (12) Observe the LiveData object in the ViewModel. Use it also when removing the observer
                viewModel.getTask().observe(this, new Observer<TaskEntry>() {
                    @Override
                    public void onChanged(@Nullable TaskEntry taskEntry) {
                        viewModel.getTask().removeObserver(this);
                        populateUI(taskEntry);
                    }
                });
            }
        }


    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID, mTaskId);
        super.onSaveInstanceState(outState);
    }

    /**
     * initViews is called from onCreate to init the member variable views
     */
    private void initViews() {
        editText = findViewById(R.id.editTextTaskDescription);
        //  mEditText1 = findViewById(R.id.editTextTaskDescription1);
        // mEditText1 = findViewById(R.id.spinner);
       /* ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.topic, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEditText1.setAdapter(adapter);
        mEditText1.setOnItemSelectedListener(this);*/



        radioGroup1 = findViewById(R.id.radioGroup1);
        radioGroup2 = findViewById(R.id.radioGroup2);

       /* mButton = findViewById(R.id.saveButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        /** We use the up button's ID (android.R.id.home) to listen for when the up button is
         * clicked and then call onBackPressed to navigate to the previous Activity when this
         * happens.
         */
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        if (id == R.id.save) {
            onSaveButtonClicked();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * populateUI would be called to populate the UI when in update mode
     *
     * @param task the taskEntry to populate the UI
     */
    private void populateUI(TaskEntry task) {
        if (task == null) {
            return;
        }

        editText.setText(task.getDescription());
        // mEditText1.setText(task.getName());
        //setOnItemSelectedListener(task.getName());
        setPriorityInViews(task.getPriority());
        setPriorityInViews(task.getName());
    }

    /**
     * onSaveButtonClicked is called when the "save" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */
    public void onSaveButtonClicked() {
        String description = editText.getText().toString();
        //String name = mEditText1.getText().toString();
        // String name = mEditText1.getOnItemSelectedListener().toString();
        // int priority = getPriorityFromViews();
        String priority = getPriorityFromViews();
        String name = getPriorityFromViews2();
        Date date = new Date();

        final TaskEntry item = new TaskEntry( name, description, priority, date);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mTaskId == DEFAULT_TASK_ID) {
                    // insert new task
                    db.taskDao().insertTask(item);
                } else {
                    //update task
                    item.setId(mTaskId);
                    db.taskDao().updateTask(item);
                }
                finish();
            }
        });
    }

    /**
     * getPriority is called whenever the selected priority needs to be retrieved
     * @return
     */
   /* public int getPriorityFromViews() {
        int priority = 1;
        int checkedId = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.radButton1:
                priority = PRIORITY_HIGH;
                break;
            case R.id.radButton2:
                priority = PRIORITY_MEDIUM;
                break;
            case R.id.radButton3:
                priority = PRIORITY_LOW;
        }
        return priority;
    }*/

    public String getPriorityFromViews() {
        String priority = "";
        int checkedId = ((RadioGroup) findViewById(R.id.radioGroup2)).getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.radioButtonD:
                priority = IN_PROGRESS;
                break;
            case R.id.radioButtonE:
                priority = DONE;
                break;
            case R.id.radioButtonF:
                priority = LATE;
        }
        return priority;
    }


    public String getPriorityFromViews2() {
        String priority = "";
        int checkedId = ((RadioGroup) findViewById(R.id.radioGroup1)).getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.radioButtonA:
                priority = HOME;
                break;
            case R.id.radioButtonB:
                priority = SCHOOL;
                break;
            case R.id.radioButtonC:
                priority = WORK;
        }
        return priority;
    }

    /**
     * setPriority is called when we receive a task from MainActivity
     *
     * @param priority the priority value
     */
   /* public void setPriorityInViews(int priority) {
        switch (priority) {
            case PRIORITY_HIGH:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton1);
                break;
            case PRIORITY_MEDIUM:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton2);
                break;
            case PRIORITY_LOW:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton3);
        }*/

    public void setPriorityInViews(String priority) {
        switch (priority) {
            case IN_PROGRESS:
                ((RadioGroup) findViewById(R.id.radioGroup2)).check(R.id.radioButtonD);
                break;
            case DONE:
                ((RadioGroup) findViewById(R.id.radioGroup2)).check(R.id.radioButtonE);
                break;
            case LATE:
                ((RadioGroup) findViewById(R.id.radioGroup2)).check(R.id.radioButtonF);
        }


    }

    public void setPriorityInViews2(String priority) {
        switch (priority) {
            case HOME:
                ((RadioGroup) findViewById(R.id.radioGroup1)).check(R.id.radioButtonA);
                break;
            case SCHOOL:
                ((RadioGroup) findViewById(R.id.radioGroup1)).check(R.id.radioButtonB);
                break;
            case WORK:
                ((RadioGroup) findViewById(R.id.radioGroup1)).check(R.id.radioButtonC);
        }


    }
/*
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }*/
}
