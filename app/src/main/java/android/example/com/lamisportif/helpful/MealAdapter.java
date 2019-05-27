package android.example.com.lamisportif.helpful;

import android.content.Context;
import android.example.com.lamisportif.R;
import android.example.com.lamisportif.models.Meal;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.LinkedList;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MyViewHolder> {
    private LinkedList<Meal> myMeals;
    private static final String TAG = "MealAdapter";
    Context context;
    static String oldCategory = " ";

    // Provide a suitable constructor (depends on the kind of dataset)
    public MealAdapter(LinkedList<Meal> myMeals, Context context) {
        this.myMeals = myMeals;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MealAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plat_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v,context);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.description.setText(myMeals.get(position).getDescription());
        holder.price.setText(new DecimalFormat("#0.00").format(myMeals.get(position).getPrice()).concat(" MAD"));

        holder.designation.setText(myMeals.get(position).getDesignation());
        if(oldCategory.equals(myMeals.get(position).getCategory())){
            holder.categoryLayout.setVisibility(View.GONE);
        }
        Log.d(TAG,"Before "+oldCategory);
        oldCategory = myMeals.get(position).getCategory();
        holder.category.setText(oldCategory);
        Log.d(TAG,oldCategory);

        // Reference to an image file in Cloud Storage
        /*StorageReference storageReference = FirebaseStorage.getInstance().getReference(myMeals.get(position).getImage());
        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        GlideApp.with(context)
                .load(storageReference)
                .into(holder.image);*/



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myMeals.size();
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public ImageView image;
        public TextView description;
        public TextView price;
        public TextView designation;
        public TextView category;
        public Context context;
        public LinearLayout categoryLayout;
        public LinearLayout parentLayout;
        public MyViewHolder(View v, Context context) {
            super(v);
            this.context = context;
            //initialize views
            parentLayout = v.findViewById(R.id.container_details);
            categoryLayout = v.findViewById(R.id.category_label);
            description = v.findViewById(R.id.label_plat);
            price = v.findViewById(R.id.price);
            category = v.findViewById(R.id.category_field);
            designation = v.findViewById(R.id.meal_title);
            image = v.findViewById(R.id.restaurant_image);
        }

        @Override
        public void onClick(View v) {

        }
    }
}