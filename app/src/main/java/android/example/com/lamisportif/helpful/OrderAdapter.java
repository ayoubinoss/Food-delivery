package android.example.com.lamisportif.helpful;


import android.content.Context;
import android.content.Intent;
import android.example.com.lamisportif.OrderDetailsActivity;
import android.example.com.lamisportif.R;
import android.example.com.lamisportif.models.Order;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.util.LinkedList;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private LinkedList<Order> myOrders;
    private static final String TAG = "OrderAdapter";
    public Context context;

    // Provide a suitable constructor (depends on the kind of data set)
    public OrderAdapter(LinkedList<Order> myProducts, Context context) {
        this.myOrders = myProducts;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                        int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v,context);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        // - get element from your data set at this position
        // - replace the contents of the view with that element

        holder.status.setText(myOrders.get(position).getStatus());
        holder.content.setText(myOrders.get(position).getContent());
        holder.restaurantID.setText(myOrders.get(position).getRestaurantID());
        holder.total.setText(new DecimalFormat("#0.00").
                                                format(myOrders.get(position).getTotal()));

        /*set image*/
        StorageReference sr = FirebaseStorage.
                            getInstance().getReference(myOrders.get(position).getLogoRestaurant());
        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)

        GlideApp.with(context )
                .load(sr)
                .into(holder.logoRestaurant);

        /*end*/


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myOrders.size();
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case

        public TextView total;
        public TextView status;
        public TextView content;
        public TextView restaurantID;
        public ImageView logoRestaurant;
        public Context context;
        public RelativeLayout parentLayout;
        public MyViewHolder(View v, Context context) {
            super(v);
            this.context = context;
            //initialize views
            parentLayout = v.findViewById(R.id.parent_layout);
            total = v.findViewById(R.id.total);
            status = v.findViewById(R.id.status);
            content = v.findViewById(R.id.content);
            logoRestaurant = v.findViewById(R.id.logo_restaurant);
            restaurantID = v.findViewById(R.id.label_restaurant);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //handle clicks
            switch (v.getId()) {
                case R.id.parent_layout:
                    context.startActivity(new Intent(context,OrderDetailsActivity.class));
                    break;
            }
        }

    }
}
