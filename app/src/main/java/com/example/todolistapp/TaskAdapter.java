package com.example.todolistapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.todolistapp.database.TaskEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * This TaskAdapter creates and binds ViewHolders, that hold the description and priority of a task,
 * to a RecyclerView to efficiently display data.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    // Constant for date format
    private static final String DATE_FORMAT = "dd/MM/yyy";

    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;
    // Class variables for the List that holds task data and the Context
    private List<TaskEntry> mTaskEntries;
    private Context mContext;
    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    /**
     * Constructor for the TaskAdapter that initializes the Context.
     *
     * @param context  the current Context
     * @param listener the ItemClickListener
     */
    public TaskAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new TaskViewHolder that holds the view for each task
     */
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.task_layout, parent, false);

        return new TaskViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        // Determine the values of the wanted data
        TaskEntry taskEntry = mTaskEntries.get(position);
        String description = taskEntry.getDescription();
        String name = taskEntry.getName();
        //int priority = taskEntry.getPriority();
        String priority = taskEntry.getPriority();

        String updatedAt = dateFormat.format(taskEntry.getUpdatedAt());

        //Set values
        holder.taskDescriptionView1.setText(name);
        holder.taskDescriptionView.setText(description);
        holder.updatedAtView.setText(updatedAt);

        // Programmatically set the text and color for the priority TextView
        String priorityString = "" + priority; // converts int to String
        holder.priorityView.setText(priorityString);

        String priorityString1 = "" + name; // converts int to String
        holder.taskDescriptionView1.setText(priorityString1);

       /* GradientDrawable priorityCircle = (GradientDrawable) holder.priorityView.getBackground();
        // Get the appropriate background color based on the priority
        int priorityColor = getPriorityColor(priority);
        priorityCircle.setColor(priorityColor);*/

          GradientDrawable priorityCircle = (GradientDrawable) holder.priorityView.getBackground();
        // Get the appropriate background color based on the priority
        int priorityColor = getPriorityColor(priority);
        priorityCircle.setColor(priorityColor);
    }

    /*
    Helper method for selecting the correct priority circle color.
    P1 = red, P2 = orange, P3 = yellow
    */
   /* private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch (priority) {
            case 1:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                break;
            case 2:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialGreen);
                break;
            case 3:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
            default:
                break;
        }
        return priorityColor;
    }
*/
/* private int getPriorityColor(String priority) {
        Drawable priorityColor = Drawable.createFromPath("");

        switch (priority) {
            case AddTaskActivity.PRIORITY_HIGH:
                priorityColor = ContextCompat.getDrawable(mContext, R.drawable.ic_save_black_24dp);
                break;
            case AddTaskActivity.PRIORITY_MEDIUM:
                priorityColor = ContextCompat.getDrawable(mContext, R.drawable.ic_save_black_24dp);
                break;
            case AddTaskActivity.PRIORITY_LOW:
                priorityColor = ContextCompat.getDrawable(mContext, R.drawable.ic_save_black_24dp);
                break;
            default:
                break;
        }
        return Integer.parseInt(String.valueOf(priorityColor));
    }
*/

    private int getPriorityColor(String priority) {
        String priorityColor = "";

        switch (priority) {
            case AddTaskActivity.PRIORITY_HIGH:
                priorityColor = String.valueOf(ContextCompat.getColor(mContext, R.color.materialGreen));
                break;
            case AddTaskActivity.PRIORITY_MEDIUM:
                priorityColor = String.valueOf(ContextCompat.getColor(mContext, R.color.materialYellow));
                break;
            case AddTaskActivity.PRIORITY_LOW:
                priorityColor = String.valueOf(ContextCompat.getColor(mContext, R.color.materialRed));
                break;
            default:
                break;
        }
        return Integer.parseInt(priorityColor);
    }


  /*  private int getPriorityColor(String priority) {
        String priorityColor = "";

        switch (priority) {
            case 1:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                break;
             case 2:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialGreen);
                break;
            case 3:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
            default:
                break;
        }
        return priorityColor;
    }
*/
    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mTaskEntries == null) {
            return 0;
        }
        return mTaskEntries.size();
    }

    public List<TaskEntry> getTasks() {
        return mTaskEntries;
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<TaskEntry> taskEntries) {
        mTaskEntries = taskEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    // Inner class for creating ViewHolders
    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Class variables for the task description and priority TextViews
        TextView taskDescriptionView;
        //TextView taskDescriptionView1;
        TextView taskDescriptionView1;
        TextView updatedAtView;
        TextView priorityView;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);

            taskDescriptionView = itemView.findViewById(R.id.taskDescription);
            //  taskDescriptionView1 = itemView.findViewById(R.id.taskDescription1);
            taskDescriptionView1 = itemView.findViewById(R.id.taskDescription1);
            updatedAtView = itemView.findViewById(R.id.taskUpdatedAt);
            priorityView = itemView.findViewById(R.id.priorityTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mTaskEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}