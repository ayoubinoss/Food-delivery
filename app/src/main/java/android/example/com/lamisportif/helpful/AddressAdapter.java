package android.example.com.lamisportif.helpful;

import android.content.Context;
import android.example.com.lamisportif.R;
import android.example.com.lamisportif.models.Location;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {
    private LinkedList<Location> myLocations;
    private static final String TAG = "LocationAdapter";
    Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AddressAdapter(LinkedList<Location> myLocations, Context context) {
        this.myLocations = myLocations;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AddressAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v,context);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.address.setText(myLocations.get(position).getAddress());
        holder.longitude.setText(String.valueOf(myLocations.get(position).getLongitude()));
        holder.latitude.setText(String.valueOf(myLocations.get(position).getLatitude()));

        holder.address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Adresse = " + holder.address.getText()+"");
                Log.d(TAG,"latitude = " + holder.latitude.getText()+"");
                Log.d(TAG,"longitude = " + holder.longitude.getText()+"");

                if(holder.icon.getVisibility() == View.GONE)
                    holder.icon.setVisibility(View.VISIBLE);
                else
                    holder.icon.setVisibility(View.GONE);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myLocations.size();
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView address;
        public ImageView icon;
        public Context context;
        public TextView latitude;
        public TextView longitude;
        CardView parentLayout;
        MyViewHolder(View v, Context context) {
            super(v);
            this.context = context;
            //initialize views
            parentLayout = v.findViewById(R.id.parent_layout);
            address = v.findViewById(R.id.address);
            icon = v.findViewById(R.id.selected);
            latitude = v.findViewById(R.id.latitude);
            longitude = v.findViewById(R.id.longitude);

        }

    }
}