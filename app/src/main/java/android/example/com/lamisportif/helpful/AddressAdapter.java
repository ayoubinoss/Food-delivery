package android.example.com.lamisportif.helpful;

import android.content.Context;
import android.example.com.lamisportif.R;
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
    private LinkedList<String> myLocations;
    private static final String TAG = "LocationAdapter";
    Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AddressAdapter(LinkedList<String> myLocations, Context context) {
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
        holder.address.setText(myLocations.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myLocations.size();
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        TextView address;
        ImageView icon;
        public Context context;
        CardView parentLayout;
        MyViewHolder(View v, Context context) {
            super(v);
            this.context = context;
            //initialize views
            parentLayout = v.findViewById(R.id.parent_layout);
            address = v.findViewById(R.id.address);
            icon = v.findViewById(R.id.selected);

            parentLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.parent_layout:
                    if(icon.getVisibility() == View.GONE)
                        icon.setVisibility(View.VISIBLE);
                    else
                        icon.setVisibility(View.GONE);
                    Toast.makeText(context," ",Toast.LENGTH_LONG).show();

            }
        }
    }
}