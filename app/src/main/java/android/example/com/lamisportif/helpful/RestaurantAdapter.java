package android.example.com.lamisportif.helpful;

import android.content.Context;
import android.content.Intent;
import android.example.com.lamisportif.R;
import android.example.com.lamisportif.models.Restaurant;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {
    private LinkedList<Restaurant> myRestaurants;
    private static final String TAG = "OrderDetailsAdapter";
    Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public RestaurantAdapter(LinkedList<Restaurant> myRestaurants, Context context) {
        this.myRestaurants = myRestaurants;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RestaurantAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_card_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v,context);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.name.setText(myRestaurants.get(position).getName());
        holder.deliveryPrice.setText(myRestaurants.get(position).getDeliveryPrice());
        holder.deliveryTime.setText(myRestaurants.get(position).getDeliveryTime());

        // Reference to an image file in Cloud Storage
        /*StorageReference storageReference = FirebaseStorage.getInstance().getReference(myRestaurants.get(position).getImage());
        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        GlideApp.with(context)
                .load(storageReference)
                .into(holder.image);*/



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myRestaurants.size();
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public ImageView image;
        public TextView name;
        public TextView deliveryPrice;
        public TextView deliveryTime;

        public Context context;
        public LinearLayout parentLayout;
        public MyViewHolder(View v, Context context) {
            super(v);
            this.context = context;
            //initialize views
            parentLayout = v.findViewById(R.id.parent_layout_restaurants);
            deliveryPrice = v.findViewById(R.id.restaurant_delivery_price);
            deliveryTime = v.findViewById(R.id.restaurant_delivery_time);
            name = v.findViewById(R.id.restaurant_name);
            image = v.findViewById(R.id.restaurant_image);
        }

        @Override
        public void onClick(View v) {

        }
    }
}