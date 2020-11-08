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
import java.util.List;

/**
 * This Adapter creates and binds ViewHolders, that hold the description and priority of a task,
 * to a RecyclerView to efficiently display data.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.TaskViewHolder> {

    // Member variable to handle item clicks
    final private ItemClickListener itemClickListener;
    // Class variables for the List that holds task data and the Context
    private List<Item> items;
    private Context mContext;

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

        // Get the values
        Item item              = items.get(position);
        String description     = item.getDescription();
        String date            = item.getDate();
        String clock           = item.getTime();
        String category        = item.getCategory();
        String progress        = item.getProgress();
        String progress_number = item.getProgress_number();

        //Set values
        holder.descriptionView.setText(description);
        holder.dueDateView.setText(date);
        holder.timeView.setText(clock);

        // Set the text and color for the category TextView
        String categoryString = "" + category; // converts int to String
        holder.categoryView.setText(categoryString);

        // Set the text for the progress TextView
        String progressString = "" + progress; // converts int to String
        holder.progressView.setText(progressString);
        holder.progressNumView.setText(progress_number);

        // Background color for the progress of the task (Rectangle shape)
        GradientDrawable progressShape = (GradientDrawable) holder.progressView.getBackground();
        int progressColor = getProgressTaskColor(progress);
        progressShape.setColor(progressColor);
    }



    private int getProgressTaskColor(String progress) {
        String progressColor = "";

        switch (progress) {
            case AddItem.DONE:
                progressColor = String.valueOf(ContextCompat.getColor(mContext, R.color.green));
                break;
            case AddItem.IN_PROGRESS:
                progressColor = String.valueOf(ContextCompat.getColor(mContext, R.color.yellow));
                break;
            case AddItem.LATE:
                progressColor = String.valueOf(ContextCompat.getColor(mContext, R.color.red));
                break;
            default:
                break;
        }
        return Integer.parseInt(progressColor);
    }


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

        // Class variables
        TextView descriptionView;
        TextView categoryView;
        TextView progressView;
        TextView progressNumView;
        TextView dueDateView;
        TextView timeView;

        /**
         * Constructor for the TaskViewHolders. (ITEM_LAYOUT.XML)
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);

            descriptionView = itemView.findViewById(R.id.descriptionView);
            dueDateView     = itemView.findViewById(R.id.dueDateView);
            timeView        = itemView.findViewById(R.id.timeView);
            categoryView    = itemView.findViewById(R.id.categoryView);
            progressView    = itemView.findViewById(R.id.progressView);
            progressNumView = itemView.findViewById(R.id.progressNumberView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = items.get(getAdapterPosition()).getId();
            itemClickListener.onItemClickListener(elementId);
        }
    }
}