package com.example.todolistapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import java.util.Calendar;
import android.widget.TimePicker;
import com.example.todolistapp.database.AppDatabase;
import com.example.todolistapp.database.Item;


public class AddItem extends AppCompatActivity  {

    // Extra for the task ID to be received in the intent
    public static final String EXTRA_TASK_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_TASK_ID = -1;

    // Constants for progress
    public static final String DONE = "DONE";
    public static final String IN_PROGRESS = "IN PROGRESS";
    public static final String LATE = "LATE";
    // Constants for categories
    public static final String WORK = "WORK";
    public static final String SCHOOL = "SCHOOL";
    public static final String HOME= "HOME";


    // VIEWS
    EditText editTextDescrip;
    EditText editTextNum;
    EditText editTextDataPicker;
    EditText editTextDueDate;
    EditText editTextTime;
    EditText editTextTimePicker;

    RadioGroup radioGroup1;
    RadioGroup radioGroup2;

    DatePickerDialog dataPicker;
    TimePickerDialog timePicker;


    private int itemId = DEFAULT_TASK_ID;

    // Member variable for the Database
    private AppDatabase db;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

       // DATA PICKER
        editTextDataPicker=(EditText) findViewById(R.id.editDueDate);
        editTextDataPicker.setInputType(InputType.TYPE_NULL);
        editTextDataPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // date picker dialog
                dataPicker = new DatePickerDialog(AddItem.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editTextDataPicker.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                dataPicker.show();
            }
        });

       // TIME PICKER
        editTextTimePicker=(EditText) findViewById(R.id.editDueTime);
        editTextTimePicker.setInputType(InputType.TYPE_NULL);
        editTextTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                timePicker = new TimePickerDialog(AddItem.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                editTextTimePicker.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                timePicker.show();
            }
        });

        initViews();



        db = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            itemId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            // mButton.setText(R.string.update_button);
            if (itemId == DEFAULT_TASK_ID) {
                // populate the UI
                itemId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);

                // COMPLETED (9) Remove the logging and the call to loadTaskById, this is done in the ViewModel now
                // COMPLETED (10) Declare a AddTaskViewModelFactory using mDb and mTaskId
                AddTaskViewModelFactory factory = new AddTaskViewModelFactory(db, itemId);
                // COMPLETED (11) Declare a AddTaskViewModel variable and initialize it by calling ViewModelProviders.of
                // for that use the factory created above AddTaskViewModel
                final AddTaskViewModel viewModel
                        = ViewModelProviders.of(this, factory).get(AddTaskViewModel.class);

                // COMPLETED (12) Observe the LiveData object in the ViewModel. Use it also when removing the observer
                viewModel.getTask().observe(this, new Observer<Item>() {
                    @Override
                    public void onChanged(@Nullable Item item) {
                        viewModel.getTask().removeObserver(this);
                        populateUI(item);
                    }
                });


            }

        }


    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID, itemId);
        super.onSaveInstanceState(outState);
    }

    /**
     * initViews is called from onCreate to init the member variable views
     */
    private void initViews() {
        editTextDescrip =  findViewById(R.id.itemDesc);
        editTextDueDate =  findViewById(R.id.editDueDate);
        editTextTime    =  findViewById(R.id.editDueTime);
        editTextNum     =  findViewById(R.id.edit_progress_number);
        radioGroup1     =  findViewById(R.id.radioGroup1);
        radioGroup2     =  findViewById(R.id.radioGroup2);

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
    private void populateUI(Item task) {
        if (task == null) {
            return;
        }

        editTextDescrip.setText(task.getDescription());
        editTextDueDate.setText(task.getDate());
        editTextTime.setText(task.getTime());
        editTextNum.setText(task.getProgress_number());

        setCategoryFromRadioButton1(task.getCategory());
        setProgressFromRadioButton2(task.getProgress());

    }

    /**
     * onSaveButtonClicked is called when the "save" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */
    public void onSaveButtonClicked() {
        String description     =  editTextDescrip.getText().toString();
        String dueDate         =  editTextDueDate.getText().toString();
        String time            =  editTextTime.getText().toString();
        String category        =  getCategoryFromRadioButton1();
        String priority        =  getProgressFromRadioButton2();
        String progress_number =  editTextNum.getText().toString();


       final Item item = new Item( category, description,  priority, progress_number, dueDate,time);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (itemId == DEFAULT_TASK_ID) {
                    // insert new task
                    db.taskDao().insertTask(item);
                } else {
                    //update task
                    item.setId(itemId);
                    db.taskDao().updateTask(item);
                }
                finish();
            }
        });

    }


    public String getCategoryFromRadioButton1() {
        String category = "";
        int checkedId = ((RadioGroup) findViewById(R.id.radioGroup1)).getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.radioButtonA:
                category = HOME;
                break;
            case R.id.radioButtonB:
                category = SCHOOL;
                break;
            case R.id.radioButtonC:
                category = WORK;
        }
        return category;
    }


    public String getProgressFromRadioButton2() {
        String progress = "";
        int checkedId = ((RadioGroup) findViewById(R.id.radioGroup2)).getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.radioButtonD:
                progress = IN_PROGRESS;
                break;
            case R.id.radioButtonE:
                progress = DONE;
                break;
            case R.id.radioButtonF:
                progress = LATE;
        }
        return progress;
    }


    public void setCategoryFromRadioButton1(String priority) {
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


    /**
     * setPriority is called when we receive a task from MainActivity
     *
     * @param priority the priority value
     */

    public void setProgressFromRadioButton2(String priority) {
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

}
