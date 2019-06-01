package android.example.com.lamisportif.helpful;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.example.com.lamisportif.R;
import android.example.com.lamisportif.RestaurantActivity;
import android.example.com.lamisportif.models.OrderLine;
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
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public class OrderLineAdapter extends RecyclerView.Adapter<OrderLineAdapter.MyViewHolder> {
    private LinkedList<OrderLine> myOrderlines;
    private static final String TAG = "OrderLine Adapter";
    private static final String SHARED_FILE = "LAmiSportif.cart";

    Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public OrderLineAdapter(LinkedList<OrderLine> myOrderlines, Context context) {
        this.myOrderlines = myOrderlines;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OrderLineAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plat_item_in_cart, parent, false);
        MyViewHolder vh = new MyViewHolder(v,context);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.designation.setText(myOrderlines.get(position).getDesignation());
        holder.quantity.setText(String.valueOf(myOrderlines.get(position).getQuantity()).concat("x"));
        holder.total.setText(new DecimalFormat("#0.00").format(myOrderlines.get(position).getTotal()).concat(" MAD"));
        String details = " ";
        String buff = " ";
        for(String key :  myOrderlines.get(position).getMapAnswer().keySet()){
            details += myOrderlines.get(position).getMapAnswer().get(key);
            buff = details;
            details += " + ";
            Log.d(TAG,"values" + myOrderlines.get(position).getMapAnswer().get(key));
        }
        Log.d(TAG,"details" + buff);
        holder.details.setText(buff);

        String orderID = myOrderlines.get(position).getDesignation().concat(buff);
        //OnClickListener
        holder.iconAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myOrderlines.get(position).getQuantity() < 10){
                    myOrderlines.get(position).setQuantity(myOrderlines.get(position).getQuantity()+1);
                    myOrderlines.get(position).setTotal(myOrderlines.get(position).getPrice() * myOrderlines.get(position).getQuantity());
                    notifyDataSetChanged();
                    SharedPreferences sp = context.getSharedPreferences(SHARED_FILE,context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    Set<String> newOrderLines = new TreeSet<>();
                    Gson gson = new Gson();

                    for(OrderLine ord : myOrderlines){
                        String json  = gson.toJson(ord);
                        newOrderLines.add(json);
                    }
                    editor.putStringSet("orderlines",newOrderLines);
                    editor.apply();
                }
            }
        });
        holder.iconRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myOrderlines.get(position).getQuantity() > 1){
                    myOrderlines.get(position).setQuantity(myOrderlines.get(position).getQuantity()-1);
                    myOrderlines.get(position).setTotal(myOrderlines.get(position).getPrice() * myOrderlines.get(position).getQuantity());
                    notifyDataSetChanged();
                    SharedPreferences sp = context.getSharedPreferences(SHARED_FILE,context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    Set<String> newOrderLines = new TreeSet<>();
                    Gson gson = new Gson();

                    for(OrderLine ord : myOrderlines){
                        String json  = gson.toJson(ord);
                        newOrderLines.add(json);
                    }
                    editor.putStringSet("orderlines",newOrderLines);
                    editor.apply();
                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myOrderlines.size();
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public TextView quantity;
        public TextView designation;
        public TextView total;
        public TextView details;
        public ImageView iconAdd;
        public ImageView iconRemove;

        public Context context;
        public LinearLayout parentLayout;
        public MyViewHolder(View v, Context context) {
            super(v);
            this.context = context;
            //initialize views
            parentLayout = v.findViewById(R.id.parent_meal_item);
            quantity = v.findViewById(R.id.order_quantity);
            designation = v.findViewById(R.id.label_plat_);
            total = v.findViewById(R.id.price_);
            details = v.findViewById(R.id.label_attributes);
            iconAdd = v.findViewById(R.id.add);
            iconRemove = v.findViewById(R.id.remove);

        }

        @Override
        public void onClick(View v) {

        }
    }
}