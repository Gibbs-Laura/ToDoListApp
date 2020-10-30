package com.example.todolistapp;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.todolistapp.database.Item;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * This Adapter creates and binds ViewHolders, that hold the description and priority of a task,
 * to a RecyclerView to efficiently display data.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.TaskViewHolder> {

    // Constant for date format
    private static final String DATE_FORMAT = "dd/MM/yyy";

    // Member variable to handle item clicks
    final private ItemClickListener itemClickListener;
    // Class variables for the List that holds task data and the Context
    private List<Item> items;
    private Context mContext;
    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    /**
     * Constructor for the Adapter that initializes the Context.
     *
     * @param context  the current Context
     * @param listener the ItemClickListener
     */
    public Adapter(Context context, ItemClickListener listener) {
        mContext = context;
        itemClickListener = listener;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new ItemViewHolder that holds the view for each task
     */
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item_layout to a view
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.item_layout, parent, false);

        return new TaskViewHolder(v);
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
        Item item = items.get(position);
        String description = item.getDescription();
        String name = item.getName();
        //int priority = item.getPriority();
        String priority = item.getPriority();

        String updatedAt = dateFormat.format(item.getUpdatedAt());
        String progress_number = item.getProgress_number();

        //Set values
       // holder.taskDescriptionView1.setText(name);
        holder.descriptionView.setText(description);
        holder.dateView.setText(updatedAt);
        holder.progressNumView.setText(progress_number);


        // Programmatically set the text and color for the priority TextView
        String priorityString = "" + priority; // converts int to String
        holder.progressView.setText(priorityString);

        String priorityString1 = "" + name; // converts int to String
        holder.typeView.setText(priorityString1);

       /* GradientDrawable priorityCircle = (GradientDrawable) holder.priorityView.getBackground();
        // Get the appropriate background color based on the priority
        int priorityColor = getPriorityColor(priority);
        priorityCircle.setColor(priorityColor);*/

          GradientDrawable priorityCircle = (GradientDrawable) holder.progressView.getBackground();
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
            case AddItem.PRIORITY_HIGH:
                priorityColor = ContextCompat.getDrawable(mContext, R.drawable.ic_save_black_24dp);
                break;
            case AddItem.PRIORITY_MEDIUM:
                priorityColor = ContextCompat.getDrawable(mContext, R.drawable.ic_save_black_24dp);
                break;
            case AddItem.PRIORITY_LOW:
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
            case AddItem.DONE:
                priorityColor = String.valueOf(ContextCompat.getColor(mContext, R.color.materialGreen));
                break;
            case AddItem.IN_PROGRESS:
                priorityColor = String.valueOf(ContextCompat.getColor(mContext, R.color.materialYellow));
                break;
            case AddItem.LATE:
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
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public List<Item> getTasks() {
        return items;
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<Item> taskEntries) {
        items = taskEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    // Inner class for creating ViewHolders
    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Class variables for the task description and priority TextViews
        TextView descriptionView;
        //TextView taskDescriptionView1;
        TextView typeView;
        TextView dateView;
        TextView progressView;
        TextView progressNumView;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);

            descriptionView = itemView.findViewById(R.id.description);
            //  taskDescriptionView1 = itemView.findViewById(R.id.taskDescription1);
            typeView = itemView.findViewById(R.id.type);
            dateView = itemView.findViewById(R.id.dateView);
            progressView = itemView.findViewById(R.id.progress);
            progressNumView = itemView.findViewById(R.id.progress_number);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = items.get(getAdapterPosition()).getId();
            itemClickListener.onItemClickListener(elementId);
        }
    }
}