package android.example.com.lamisportif;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.example.com.lamisportif.helpful.GlideApp;
import android.example.com.lamisportif.helpful.OrderAdapter;
import android.example.com.lamisportif.helpful.OrderLineOrderAdapter;
import android.example.com.lamisportif.models.Order;
import android.example.com.lamisportif.models.OrderLine;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firestore.v1.FirestoreGrpc;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class OrderDetailsActivity extends AppCompatActivity {

    private static final String SHARED_FILE_1 = "order";

    private String[] descriptionData = {"Pending", "Accepted", "Dispatched", "Delivered"};
    private TextView mItemDescription;
    private ImageView mDescriptionImg;
    String  currentStatus;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private OrderLineOrderAdapter orderAdapter;

    private ImageView mDeliveryManImage;
    private TextView mDeliveryManName;
    private TextView mDeliveryManPhone;

    private StateProgressBar mStateProgressBar;
    private Map<String, StateProgressBar.StateNumber> mMapStatus = new HashMap<>();
    private Map<Integer, String> mMapStatusInversed = new HashMap<>();

    private TextView mTotal;
    private TextView mMealTotal;
    private TextView mDeliveryPrice;
    private TextView label_delivery_man;
    private CardView card_delivery_man;

    private ImageView mCallIcon;

    private final String TAG = "OrderDetailsActivity";

    String id_order; // get this field from extras and use it to get the details todo
    String myEmail; //get this field from FirebaseAuth todo

    LinkedList<OrderLine> orders = new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        myEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        id_order = bundle.getString("id_order");
        mMapStatus.put("pending",StateProgressBar.StateNumber.ONE);
        mMapStatus.put("accepted",StateProgressBar.StateNumber.TWO);
        mMapStatus.put("dispatched",StateProgressBar.StateNumber.THREE);
        mMapStatus.put("delivered",StateProgressBar.StateNumber.FOUR);

        mMapStatusInversed.put(1, "pending");
        mMapStatusInversed.put(2, "accepted");
        mMapStatusInversed.put(3, "dispatched");
        mMapStatusInversed.put(4, "delivered");

        /*get the views here*/
        label_delivery_man = findViewById(R.id.label_delivery_man);
        card_delivery_man = findViewById(R.id.card_delivery_man);
        mDeliveryManImage = findViewById(R.id.img_delivery_man);
        mDeliveryManName = findViewById(R.id.delevery_man_name);
        mDeliveryManPhone = findViewById(R.id.delivery_man_phone_number);
        mStateProgressBar = findViewById(R.id.status);
        mTotal = findViewById(R.id.total);
        mDeliveryPrice = findViewById(R.id.total_delivery);
        mMealTotal = findViewById(R.id.total_meals);
        mCallIcon = findViewById(R.id.call_icon);
        mCallIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",
                        mDeliveryManPhone.getText().toString(), null)));
            }
        });


        mStateProgressBar.setStateDescriptionData(descriptionData);


        //RecyclerView & NestedScroll
        recyclerView = findViewById(R.id.list_item);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        orderAdapter = new OrderLineOrderAdapter(orders,this);
        recyclerView.setAdapter(orderAdapter);

        //Fill the list
        /*for(int i = 0;i<5;i++){
            orders.add(new OrderLine("RES Name bla bla bla bla bla bla bla bla bla",
                    15,
                    40,
                    14.5));
        }*/
        //getDeliveryManDetails();
        getOrderStatus();
        getOrderDetails();
        //refresh list
        orderAdapter.notifyDataSetChanged();
    }

    /**
     * a function to get the Delivery man details
     */
    public void getDeliveryManDetails() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("orders").document(myEmail)
                .collection("order_list").document(id_order).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Log.d(TAG,"here :" + document.getData());
                    if(document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        mDeliveryManName.setText(document.getString("delivery_man"));
                        mDeliveryManPhone.setText(document.getString("delivery_man_phone"));
                        StorageReference storageReference = FirebaseStorage
                                .getInstance().getReference(document.getString("delivery_man_image"));
                        // Download directly from StorageReference using Glide
                        // (See MyAppGlideModule for Loader registration)

                        GlideApp.with(getApplicationContext())
                                .load(storageReference)
                                .into(mDeliveryManImage);

                        /*end*/
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
    /**
     * a function to get the order status
     */
    public void getOrderStatus() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("orders").document(myEmail)
                .collection("order_list").document(id_order).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                mStateProgressBar.setCurrentStateNumber(mMapStatus.get(document.getString("status")));
                                currentStatus = document.getString("status");
                                mDeliveryPrice.setText(document.getString("total_delivery").concat(" MAD"));
                                mTotal.setText(document.getString("total").concat(" MAD"));
                                mMealTotal.setText(document.getString("total_meals").concat(" MAD"));
                            } else {
                                Log.d(TAG, "No such document");
                            }
                            if(!currentStatus.equals("pending")){
                                label_delivery_man.setVisibility(View.VISIBLE);
                                card_delivery_man.setVisibility(View.VISIBLE);
                                getDeliveryManDetails();
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
        DocumentReference docRef = db.collection("orders").document(myEmail)
                .collection("order_list").document(id_order);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                // see which fields changed
                Log.d(TAG, "status changed to = " + snapshot.getString("status"));
                Log.d(TAG, "value of current state number " + mStateProgressBar.getCurrentStateNumber());
                Log.d(TAG, "value of mMapInversed = " + mMapStatusInversed.get(mStateProgressBar.getCurrentStateNumber()));

                if(!mMapStatusInversed.get(mStateProgressBar.getCurrentStateNumber()).equals(snapshot.getString("status"))) {
                    Log.d(TAG, "status changed");
                    mStateProgressBar.setCurrentStateNumber(mMapStatus.get(snapshot.getString("status")));
                    label_delivery_man.setVisibility(View.VISIBLE);
                    card_delivery_man.setVisibility(View.VISIBLE);
                    getDeliveryManDetails();
                } else {
                    Log.d(TAG,"status was not changed");
                }

            }
        });
    }

    /**
     * a function to get the order details
     */
    public void getOrderDetails() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("orders").document(myEmail)
                .collection("order_list").document(id_order)
                .collection("order_lines")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                orders.add(new OrderLine(document.getString("designation"),
                                        ((Long)document.getLong("quantity")).intValue(),
                                        Double.valueOf((String)document.get("price"))));
                                Log.d(TAG,"orders = " + orders.toString());
                            }
                            orderAdapter.notifyDataSetChanged();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
