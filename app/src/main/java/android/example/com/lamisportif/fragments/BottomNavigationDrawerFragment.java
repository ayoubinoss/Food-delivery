package android.example.com.lamisportif.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.example.com.lamisportif.MainActivity;
import android.example.com.lamisportif.OrderDetailsActivity;
import android.example.com.lamisportif.R;
import android.example.com.lamisportif.helpful.AddressAdapter;
import android.example.com.lamisportif.helpful.OrderLineAdapter;
import android.example.com.lamisportif.models.Location;
import android.example.com.lamisportif.models.Order;
import android.example.com.lamisportif.models.OrderLine;
import android.example.com.lamisportif.models.Restaurant;
import android.example.com.lamisportif.models.User;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.chip.Chip;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firestore.v1.StructuredQuery;
import com.google.gson.Gson;
import com.google.rpc.Help;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static android.content.Context.MODE_PRIVATE;

public class BottomNavigationDrawerFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    LinkedList<OrderLine> mOrderLine = new LinkedList<>();
    FirebaseFirestore mdb;
    SharedPreferences sp1;
    Intent intent;

    private static final String SHARED_FILE = "LAmiSportif.cart";
    private static final String SHARED_FILE_1 = "order";
    private static final String TAG = "Cart Fragment";
    private static final String LABEL_COLLECTION_USERS = "users";
    private static final String LABEL_COLLECTION_ORDERS = "order_list";
    private static final String LABEL_EMAIL = "email";
    private static final String LABEL_NAME = "name";
    private static final String LABEL_PHONE = "phone_number";
    private static final String LABEL_PHONE_USER = "phone";


    private ItemTouchHelper mhelper;
    private Location location;
    private User mUser = new User();
    private Restaurant mResaurant = new Restaurant();
    private Order mOrder = new Order();
    private RecyclerView myRecycler;
    private LinearLayoutManager linearLayoutManager;
    private OrderLineAdapter orderLineAdapter;

    ImageView confirmBtn;
    LinearLayout detailsLayout;
    TextView totalMealsView;
    TextView totalView;
    TextView deliveryPriceView;
    TextView addressView;
    Chip checkOut;
    ImageView editLocation;


    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };
    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.
                inflate(R.layout.fragment_bottom_navigation_drawer_cart, container, false);
        return view;
    }*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        mdb = FirebaseFirestore.getInstance();
        intent = new Intent(getActivity(),OrderDetailsActivity.class);

        sp1 = getContext().getSharedPreferences(SHARED_FILE_1,MODE_PRIVATE);

        View inflatedView = View.inflate(getContext(), R.layout.fragment_bottom_navigation_drawer_cart, null);
        dialog.setContentView(inflatedView);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) inflatedView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        View parent = (View) inflatedView.getParent();
        parent.setFitsSystemWindows(true);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(parent);
        inflatedView.measure(0, 0);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        bottomSheetBehavior.setPeekHeight(screenHeight);

        if (params.getBehavior() instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior)params.getBehavior()).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        params.height = screenHeight;
        parent.setLayoutParams(params);

        ImageView closeButton = inflatedView.findViewById(R.id.close_imageview);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"close fragment", Toast.LENGTH_SHORT).show();
                dismiss();

            }
        });

        //Views
        confirmBtn = inflatedView.findViewById(R.id.confirm_fields);
        detailsLayout = inflatedView.findViewById(R.id.extra_details_label);
        totalMealsView = inflatedView.findViewById(R.id.total_meals);
        deliveryPriceView = inflatedView.findViewById(R.id.total_delivery);
        totalView = inflatedView.findViewById(R.id.total);
        addressView = inflatedView.findViewById(R.id.address);
        checkOut = inflatedView.findViewById(R.id.check_out);
        editLocation = inflatedView.findViewById(R.id.edit_location);
        editLocation.setOnClickListener(this);
        checkOut.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);

        // Recycler view & adapter
        myRecycler = inflatedView.findViewById(R.id.meals_list);
        myRecycler.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        myRecycler.setLayoutManager(linearLayoutManager);
        orderLineAdapter = new OrderLineAdapter(mOrderLine,mOrder,getActivity(),totalMealsView,totalView);
        myRecycler.setAdapter(orderLineAdapter);



        // Data
        getOrderLines();
        getRestaurant();
        getLocation();
        getOrder();
        Log.d(TAG,"order :" + mOrder.toString());
        Log.d(TAG, "orderLines : " +mOrderLine);

        //View's data
        totalView.setText(new DecimalFormat("#0.00").format(mOrder.getTotal()).concat(" MAD"));
        totalMealsView.setText(new DecimalFormat("#0.00").format(mOrder.getPrice_products()).concat(" MAD"));
        deliveryPriceView.setText(new DecimalFormat("#0.00").format(mOrder.getPrice_delivery()).concat(" MAD"));

        if(mOrderLine.isEmpty()){
            detailsLayout.setVisibility(View.GONE);
        }

        if(location != null){
            addressView.setText(location.getAddress());
            checkOut.setEnabled(true);
        }

        // Swipe Stuff
        mhelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                 int direction) {
                Toast.makeText(getActivity(),"Repas supprimé de votre panier",Toast.LENGTH_SHORT).show();

                //call a function to suppress the element from the sharedPreferences

                mOrderLine.remove(viewHolder.getAdapterPosition());
                getOrder();
                totalView.setText(new DecimalFormat("#0.00").format(mOrder.getTotal()).concat(" MAD"));
                totalMealsView.setText(new DecimalFormat("#0.00").format(mOrder.getPrice_products()).concat(" MAD"));
                SharedPreferences sp = getContext().getSharedPreferences(SHARED_FILE,getContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                Set<String> newOrderLines = new TreeSet<>();
                Gson gson = new Gson();
                for(OrderLine ord : mOrderLine){
                    String json  = gson.toJson(ord);
                    newOrderLines.add(json);
                }
                if(mOrderLine.isEmpty()){
                    editor.clear();
                    detailsLayout.setVisibility(View.GONE);
                }
                else{
                    editor.putStringSet("orderlines",newOrderLines);
                }
                editor.apply();
                orderLineAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

            }
        });
        mhelper.attachToRecyclerView(myRecycler);



    }
    public void getOrderLines(){
        SharedPreferences sp = getContext().getSharedPreferences(SHARED_FILE,getContext().MODE_PRIVATE);
        OrderLine orderLine = new OrderLine();
        Set<String> myOrderLines = sp.getStringSet("orderlines", new TreeSet<String>());
        Gson gson = new Gson();
        for(String key : myOrderLines){
            mOrderLine.add(gson.fromJson(key,OrderLine.class));
            orderLineAdapter.notifyDataSetChanged();
        }
    }
    public void insertOrder(){
        Map<String, Object> properties = new HashMap<>();
        final DocumentReference doc = mdb.collection("orders").document(mUser.getEmail());
        properties.put(LABEL_EMAIL,mUser.getEmail());
        properties.put(LABEL_NAME,mUser.getName());
        properties.put(LABEL_PHONE_USER,mUser.getPhone());
        doc.set(properties)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Map<String, Object> properties = new HashMap<>();
                properties.put("address",mOrder.getLocation().getAddress());
                properties.put("content",mOrderLine.get(0).getQuantity() + "x " + mOrderLine.get(0).getDesignation());
                properties.put("location",new GeoPoint(mOrder.getLocation().getLatitude(),mOrder.getLocation().getLatitude()));
                properties.put("logo_restaurant",mOrder.getLogoRestaurant());
                properties.put("phone_number",mUser.getPhone());
                properties.put("client_name",mUser.getName());
                properties.put("restaurant_name",mOrder.getRestaurantName());
                properties.put("status",mOrder.getStatus());
                properties.put("total",String.valueOf(mOrder.getTotal()));
                properties.put("total_meals",String.valueOf(mOrder.getPrice_products()));
                properties.put("total_delivery",String.valueOf(mOrder.getPrice_delivery()));
                final DocumentReference d = doc.collection(LABEL_COLLECTION_ORDERS).document();
                Log.d(TAG,"Doc Ref " + d.getPath());
                DocumentReference documentReference = mdb.collection("all_orders").document();
                Map<String, Object> properties_ = new HashMap<>();
                properties_.put("email",mUser.getEmail());
                properties_.put("ref_document",d);
                properties_.put("status","not");
                documentReference.set(properties_);
                d.set(properties).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mOrder.setOrderID(d.getId());
                        Log.d(TAG,"user hereee :" + mUser);
                        Log.d(TAG,"Order hereee :" + mOrder);

                        SharedPreferences.Editor editor = sp1.edit();
                        editor.putString("id_order",mOrder.getOrderID());
                        Log.d(TAG,"id_order :" + sp1.getString("id_order",""));
                        editor.apply();
                        for(OrderLine ord : mOrderLine){
                            DocumentReference docu = d.collection("order_lines").document();
                            Map<String, Object> properties = new HashMap<>();
                            properties.put("designation",ord.getDesignation());
                            properties.put("price", String.valueOf(ord.getPrice()));
                            properties.put("total",String.valueOf(ord.getTotal()));
                            properties.put("quantity",ord.getQuantity());
                            String buff = "";
                            String answer = "";
                            for(String key : ord.getMapAnswer().keySet()){
                                buff += ord.getMapAnswer().get(key);
                                answer = buff;
                                buff += " + ";
                            }
                            properties.put("mapAnswers",answer);
                            docu.set(properties).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                }
                            });
                        }
                    }
                });


            }
        });

    }

    public void getCurrentUser(){
        FirebaseFirestore.getInstance()
                .collection(LABEL_COLLECTION_USERS)
                .document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            mUser = new User(
                                    (String)task.getResult().get(LABEL_EMAIL),
                                    (String)task.getResult().get(LABEL_NAME),
                                    (String)task.getResult().get(LABEL_PHONE)
                            );
                            Log.d(TAG,"user here " +mUser.toString() );
                            insertOrder();
                        }
                    }
                });
    }
    public void getRestaurant(){
        SharedPreferences sp = getContext().getSharedPreferences(SHARED_FILE,getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String buff = null;
        mResaurant.setImage(sp.getString("restaurantImageLink",buff));
        mResaurant.setName(sp.getString("restaurantName",buff));
        if(sp.getString("deliveryPrice",buff)!= null){
            mResaurant.setDeliveryPrice(Double.parseDouble(sp.getString("deliveryPrice",buff)));
        }

        Log.d(TAG,"restaurant" +mResaurant.toString());
    }

    public void getOrder(){
        mOrder.setLogoRestaurant(mResaurant.getImage());
        mOrder.setStatus(Order.PENDING);
        mOrder.setPrice_delivery(mResaurant.getDeliveryPrice());
        mOrder.setRestaurantName(mResaurant.getName());
        Double total = 0.0;
        for(OrderLine ord : mOrderLine){
            total += ord.getTotal();
        }
        mOrder.setPrice_products(total);
        mOrder.setTotal(total + mResaurant.getDeliveryPrice());
        mOrder.setLocation(location);
        Log.d(TAG,"Order :" + mOrder.toString());
    }

    public void getLocation(){
        SharedPreferences sp = getActivity().getSharedPreferences(SHARED_FILE,MODE_PRIVATE);
        Gson gson = new Gson();
        String buff = null ;
        location = gson.fromJson(sp.getString("chosenLocation",buff),Location.class);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.confirm_fields :
                Log.d(TAG,"clicked" + mOrder);
                //AdressFragment adressFragment = AdressFragment.newInstance("Choisissez une adresse");
                    //adressFragment.show(getActivity().getSupportFragmentManager(),TAG);
                break;
            case R.id.edit_location:
                AdressFragment adressFragment = AdressFragment.newInstance("Choisissez une adresse",this);
                adressFragment.show(getActivity().getSupportFragmentManager(),TAG);
                break;
            case R.id.check_out :
                getCurrentUser();
                SharedPreferences sp = getContext().getSharedPreferences(SHARED_FILE,getContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();
                dismiss();
                Toast.makeText(getContext()," Veuillez vous rendre au menu Mes Commandes afin de suvire l'avancement de votre commande ",Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
