package android.example.com.lamisportif;

import android.example.com.lamisportif.helpful.GlideApp;
import android.example.com.lamisportif.helpful.OrderAdapter;
import android.example.com.lamisportif.helpful.RestaurantAdapter;
import android.example.com.lamisportif.models.Order;
import android.example.com.lamisportif.models.OrderLine;
import android.example.com.lamisportif.models.Restaurant;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.LinkedList;

public class OrdersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private OrderAdapter orderAdapter;

    private final String TAG = "OrdersActivity";
    LinkedList<Order> orders = new LinkedList<>();

    private String myEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        myEmail = "larhlimihamza@gmail.com";

        //RecyclerView & NestedScroll
        recyclerView = findViewById(R.id.order_list);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        orderAdapter = new OrderAdapter(orders,this);
        recyclerView.setAdapter(orderAdapter);

        //Fill the list
       getAllOrders();
        orderAdapter.notifyDataSetChanged();
    }
    /**
     * get all orders
     */
    //todo getImageByIdRestaurant()
    public void getAllOrders() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("orders").document(myEmail)
                .collection("order_list")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        /*
                         public Order(String restaurantID, String status, String logoRestaurant,
                 double total, String content) {
                        * */
                        orders.add(new Order(
                                document.getString("restaurant_name"),
                                document.getString("status"),
                                document.getString("logo_restaurant"),
                                Double.valueOf(document.getString("total")),
                                document.getString("content")
                        ));
                    }
                    orderAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
