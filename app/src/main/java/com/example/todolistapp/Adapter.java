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



// The main function of the adapter is to create and binds ViewHolders
public class Adapter extends RecyclerView.Adapter<Adapter.TaskViewHolder> {

    // Member variable to handle item clicks
    final private ItemClickListener itemClickListener;
    // Class variables
    private List<Item> items;
    private Context mContext;


    // The Adapter's constructor.
    public Adapter(Context context, ItemClickListener listener) {
        // initialize the context and the itemClickListener
        mContext = context;
        itemClickListener = listener;
    }



    // In order to fill the RecyclerView, this will called After a ViewHolders are created
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item_layout to a view
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.item_layout, parent, false);

        return new TaskViewHolder(v); // Holds the view for each item
    }



    // This will be called by the RecyclerView to display data at a specific position
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) { // Holder (ViewHolder to binf the data to)
        //position (Position of the data in the cursor )

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

        String categoryString = "" + category; // converts int to String
        holder.categoryView.setText(categoryString);


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




    //Returns the number of items to display.
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



    // Notify the adapter every time the data has changed and updates the items
    public void setTasks(List<Item> listOfItems) {
        items = listOfItems;
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