package android.example.com.lamisportif.helpful;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.example.com.lamisportif.FormActivity;
import android.example.com.lamisportif.R;
import android.example.com.lamisportif.fragments.PreCartFragment;
import android.example.com.lamisportif.models.Meal;
import android.example.com.lamisportif.models.OrderLine;
import android.media.Image;
import android.os.Bundle;
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

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MyViewHolder> {
    private LinkedList<Meal> myMeals;
    private static final String TAG = "MealAdapter";
    Context context;
    String restaurantName;
    String restaurantImageLink;
    Double deliveryPrice;
    static String oldCategory = " ";
    int quantity = 1;
    private static final String SHARED_FILE = "LAmiSportif.cart";


    // Provide a suitable constructor (depends on the kind of dataset)
    public MealAdapter(LinkedList<Meal> myMeals, Context context,String restaurantName, String restaurantImageLink, Double deliveryPrice) {
        this.restaurantName = restaurantName;
        this.restaurantImageLink = restaurantImageLink;
        this.deliveryPrice = deliveryPrice;
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
    public void onBindViewHolder(final MyViewHolder holder, final int position)  {
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

        holder.addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Add to Cart");
                Intent intent = new Intent(context,FormActivity.class);
                Bundle bundle =  new Bundle();
                //data for order
                bundle.putString("restaurantName",restaurantName);
                bundle.putString("restaurantImageLink",restaurantImageLink);
                bundle.putDouble("deliveryPrice",deliveryPrice);

                bundle.putString("restaurantID",myMeals.get(position).getRestaurantID());
                bundle.putString("categoryID",myMeals.get(position).getCategoryID());
                bundle.putString("mealID",myMeals.get(position).getMealID());
                bundle.putString("designation",myMeals.get(position).getDesignation());
                bundle.putDouble("price",myMeals.get(position).getPrice());
                intent.putExtra("bundle",bundle);
                context.startActivity(intent);

                //Toast.makeText(context, "Add to cart", Toast.LENGTH_SHORT).show();
                // this should be done on the Form but who is 'parentLayout' ?


                /*LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View child = li.inflate(R.layout.pre_cart_card,null);

                TextView orderQuantityView = (TextView) child.findViewById(R.id.order_quantity);
                TextView labelMealView = (TextView) child.findViewById(R.id.label_plat_);
                TextView labelAttributesView = (TextView) child.findViewById(R.id.label_attributes); // Fill it from the form, How ?
                TextView labelPriceView = (TextView) child.findViewById(R.id.price_);
                ImageView removeIcon = (ImageView) child.findViewById(R.id.remove);
                ImageView addIcon = (ImageView) child.findViewById(R.id.add);
                orderQuantityView.setText(String.valueOf(quantity).concat("x")); //Default Value
                labelMealView.setText(myMeals.get(position).getDesignation());
                labelPriceView.setText(new DecimalFormat("#0.00").format(myMeals.get(position).getPrice()).concat(" MAD"));
                removeIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(quantity == 1){
                           // holder.parentLayout.removeView(child);
                        }
                        quantity --;
                    }
                });
                addIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        quantity++;
                    }
                });
                holder.parentLayout.addView(child);*/


                // Remove all this code, put in a fragment or activity, send data via Intent
                // this is just for test atm /
                /*SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_FILE,context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                OrderLine orderLine = new OrderLine(
                        myMeals.get(position).getDesignation(),
                        1, // change it later
                        myMeals.get(position).getPrice()
                );
                Gson gson = new Gson();
                String json  = gson.toJson(orderLine);
                editor.putString(myMeals.get(position).getDesignation(),json);
                Log.d(TAG, " Json format : " + json);

                Set<String> keys = sharedPreferences.getStringSet("keys", new TreeSet<String>());
                keys.add(myMeals.get(position).getDesignation());
                editor.putStringSet("keys", keys);
                editor.apply();
                Log.d(TAG, "value of keys here: "+keys.toString());*/

            }
        });

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
        public ImageView image;
        public TextView description;
        public TextView price;
        public TextView designation;
        public TextView category;

        public ImageView addIcon;
        public Context context;
        public LinearLayout categoryLayout;
        public LinearLayout parentLayout;

        public MyViewHolder(View v, Context context) {
            super(v);
            this.context = context;
            //initialize views
            parentLayout = v.findViewById(R.id.container_details);
            categoryLayout = v.findViewById(R.id.category_label);

            addIcon = v.findViewById(R.id.add_to_my_order);

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