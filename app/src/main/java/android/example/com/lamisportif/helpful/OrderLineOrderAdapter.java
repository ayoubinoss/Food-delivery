package android.example.com.lamisportif.helpful;


import android.content.Context;
import android.content.Intent;
import android.example.com.lamisportif.OrderDetailsActivity;
import android.example.com.lamisportif.R;
import android.example.com.lamisportif.models.Order;
import android.example.com.lamisportif.models.OrderLine;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.util.LinkedList;


public class OrderLineOrderAdapter extends RecyclerView.Adapter<OrderLineOrderAdapter.MyViewHolder> {

    private LinkedList<OrderLine> myOrdersLines;
    private static final String TAG = "OrderAdapter";
    public Context context;

    // Provide a suitable constructor (depends on the kind of data set)
    public OrderLineOrderAdapter(LinkedList<OrderLine> myProducts, Context context) {
        this.myOrdersLines = myProducts;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public OrderLineOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                        int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plat_item_in_order_details, parent, false);
        MyViewHolder vh = new MyViewHolder(v,context);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        // - get element from your data set at this position
        // - replace the contents of the view with that element

        holder.designation.setText(myOrdersLines.get(position).getDesignation());
        holder.nb_meals.setText("x " + String.valueOf(myOrdersLines.get(position).getQuantity()));
        holder.total.setText(new DecimalFormat("#0").
                format(myOrdersLines.get(position).getPrice()).concat(" MAD"));


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myOrdersLines.size();
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case

        public TextView designation;
        public TextView total;
        public TextView nb_meals;
        public TextView fields;

        public Context context;
        public RelativeLayout parentLayout;
        public MyViewHolder(View v, Context context) {
            super(v);
            this.context = context;
            //initialize views
            parentLayout = v.findViewById(R.id.parent_layout);
            total = v.findViewById(R.id.price);
            designation = v.findViewById(R.id.designation);
            nb_meals = v.findViewById(R.id.nb_meals);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //handle clicks
            switch (v.getId()) {
                case R.id.parent_layout:
                    Toast.makeText(context, "shiiiiit", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }
}
