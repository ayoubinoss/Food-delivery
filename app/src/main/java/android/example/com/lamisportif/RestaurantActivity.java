package android.example.com.lamisportif;

import android.example.com.lamisportif.helpful.MealAdapter;
import android.example.com.lamisportif.helpful.RestaurantAdapter;
import android.example.com.lamisportif.models.Meal;
import android.example.com.lamisportif.models.Restaurant;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.LabelDescriptorOrBuilder;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.LinkedList;

public class RestaurantActivity extends AppCompatActivity {

    private static final String TAG = " Restaurant Activity ";
    private static final String LABEL_COLLECTION = "restaurants";
    private static final String LABEL_COLLECTION_1 = "categories";
    private static final String LABEL_COLLECTION_2 = "meals";
    private static final String LABEL_CATEGORY = "category";
    private static final String LABEL_DESCRIPTION = "description";
    private static final String LABEL_DESIGNATION = "designation";
    private static final String LABEL_IMAGE = "image";
    private static final String LABEL_PRICE = "price";

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MealAdapter mealAdapter;

    LinkedList<Meal> myMeals = new LinkedList<>();
    Restaurant restaurant = new Restaurant();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        //View
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.restaurant_collapsingToolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.restaurant_appbar);
        TextView restaurantNameField = (TextView) findViewById(R.id.restaurant_name_field);
        TextView deliveryTimeField = (TextView) findViewById(R.id.delivery_time_field);
        TextView deliveryPriceField = (TextView) findViewById(R.id.delivery_price_field);
        ImageView restaurantImageField = (ImageView) findViewById(R.id.restaurant_image_field);
        final ImageView collapseBackBtn = (ImageView) findViewById(R.id.collapse_back_btn); // Need OnClickListener
        final ImageView toolBarBackBtn = (ImageView) findViewById(R.id.toolbar_back_btn); // need OnClickListener (same code as collapseBackBtn)

        //intent & bundle
        Bundle bundle = getIntent().getBundleExtra("bundle");
        restaurant.setName(bundle.getString("name"));
        restaurant.setDeliveryTime(bundle.getString("deliveryTime"));
        restaurant.setImage(bundle.getString("image_link"));
        restaurant.setRestaurantID(bundle.getString("restaurantID"));
        restaurant.setDeliveryPrice(bundle.getDouble("deliveryPrice"));

        //Views' Data
        restaurantNameField.setText(restaurant.getName());
        deliveryPriceField.setText(String.valueOf(new DecimalFormat("#0.00").format(restaurant.getDeliveryPrice())).concat(" MAD"));
        deliveryTimeField.setText(restaurant.getDeliveryTime());
        byte [] arrayImage = bundle.getByteArray("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(arrayImage,0,arrayImage.length);
        ((ImageView)restaurantImageField).setImageBitmap(bitmap);

        //RecyclerView & NestedScroll
        recyclerView = findViewById(R.id.meals_list);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mealAdapter = new MealAdapter(myMeals,this);
        recyclerView.setAdapter(mealAdapter);

        // fill the list
        //getMeals();
        getAllMeals();
        mealAdapter.notifyDataSetChanged();
        /*int j = 0;
        for(int i = 0;i<15;i++){
            myMeals.add(new Meal(
                    (double)i,
                    "description_"+i,
                    "designation_"+i,
                    "category_"+j
            ));
            if(i%5 == 0 && i!=0)
                j++;
        }*/
        // Changes when the the Toolbar is/is not collapsed
        final String restaurantName = restaurantNameField.getText().toString();
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                // Collapsed
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(restaurantName);
                    collapseBackBtn.setClickable(false);
                    collapseBackBtn.setVisibility(View.GONE);
                    toolBarBackBtn.setClickable(true);
                    toolBarBackBtn.setVisibility(View.VISIBLE);
                    isShow = true;
                }
                // Not Collapsed
                else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    collapseBackBtn.setClickable(true);
                    collapseBackBtn.setVisibility(View.VISIBLE);
                    toolBarBackBtn.setClickable(false);
                    toolBarBackBtn.setVisibility(View.GONE);
                    isShow = false;
                }
            }
        });
    }

    public void getAllMeals(){
        FirebaseFirestore.getInstance().collection(LABEL_COLLECTION)
                .document(restaurant.getRestaurantID())
                .collection(LABEL_COLLECTION_1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(final QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG,  document.getId() + " data :" + document.getData() );
                                FirebaseFirestore.getInstance().collection(LABEL_COLLECTION)
                                    .document(restaurant.getRestaurantID())
                                    .collection(LABEL_COLLECTION_1)
                                    .document(document.getId())
                                    .collection(LABEL_COLLECTION_2)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful()){
                                                for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                 Log.d(TAG, " Current Category inside Loop : " + document.get(LABEL_CATEGORY));
                                                 Log.d(TAG, "Inside Loop : " +documentSnapshot.getId() + " => " + documentSnapshot.getData());
                                                 myMeals.add(new Meal(
                                                         (String) documentSnapshot.get(LABEL_IMAGE),
                                                         ((Long)documentSnapshot.get(LABEL_PRICE)).intValue(),
                                                         (String) documentSnapshot.get(LABEL_DESCRIPTION),
                                                         (String) documentSnapshot.get(LABEL_DESIGNATION),
                                                         (String) document.get(LABEL_CATEGORY)
                                                         ));
                                                    Log.d(TAG, "Inside Loop : " +documentSnapshot.getId() + " => " + documentSnapshot.getData());
                                                }
                                                mealAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    });
                                Log.d(TAG, "Meals 1 : " + myMeals.toString()); // Il s'affiche avant les Logs précédent for some reason,
                                // en gros faut attendre Data tatsali, cuz Data kayna
                                // check Log /
                            }
                        }
                    }
                });
    Log.d(TAG, " Meals :" + myMeals.toString());
    }
}
