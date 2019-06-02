package android.example.com.lamisportif.fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.StructuredQuery;
import com.google.gson.Gson;
import com.google.rpc.Help;

import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public class BottomNavigationDrawerFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    LinkedList<OrderLine> mOrderLine = new LinkedList<>();
    private static final String SHARED_FILE = "LAmiSportif.cart";
    private static final String TAG = "Cart Fragment";
    private static final String LABEL_COLLECTION_USERS = "users";
    private static final String LABEL_COLLECTION_ORDERS = "order_list";
    private static final String LABEL_EMAIL = "email";
    private static final String LABEL_NAME = "name";
    private static final String LABEL_PHONE = "phone_number";


    private ItemTouchHelper mhelper;
    private User mUser = new User();
    private Restaurant mResaurant = new Restaurant();
    private Order mOrder = new Order();
    private RecyclerView myRecycler;
    private LinearLayoutManager linearLayoutManager;
    private OrderLineAdapter orderLineAdapter;

    ImageView confirmBtn;

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
        confirmBtn.setOnClickListener(this);

        // Recycler view & adapter
        myRecycler = inflatedView.findViewById(R.id.meals_list);
        myRecycler.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        myRecycler.setLayoutManager(linearLayoutManager);
        orderLineAdapter = new OrderLineAdapter(mOrderLine,getActivity());
        myRecycler.setAdapter(orderLineAdapter);

        // Data
        getOrderLines();


        Log.d(TAG, "heeere " +mOrderLine);


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
                }
                else{
                    editor.putStringSet("orderlines",newOrderLines);
                }
                editor.apply();
                orderLineAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

            }
        });
        mhelper.attachToRecyclerView(myRecycler);

        //some More Data
        getCurrentUser();

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

    public void getCurrentUser(){
        FirebaseFirestore.getInstance()
                .collection(LABEL_COLLECTION_USERS)
                .document(/*FirebaseAuth.getInstance().getCurrentUser().getEmail()*/
                "larhlimihamza@gmail.com")
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
                            getRestaurant();
                            getOrder();
                        }
                    }
                });
    }
    public void getRestaurant(){
        SharedPreferences sp = getContext().getSharedPreferences(SHARED_FILE,getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String buff = "";
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
        Log.d(TAG,"Order :" + mOrder.toString());
    }
    public void insertOrder(){

    }
    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.confirm_fields :
                SharedPreferences sp = getContext().getSharedPreferences(SHARED_FILE,getContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                Gson gson = new Gson();
                String buff = null ;
                buff = sp.getString("chosenLocation",buff);
                Log.d(TAG,"buff !" + buff);
                Location location = gson.fromJson(sp.getString("chosenLocation",buff),Location.class);
                if(location == null ){
                    AdressFragment adressFragment = AdressFragment.newInstance("Choisissez une adresse");
                    adressFragment.show(getActivity().getSupportFragmentManager(),TAG);
                }
                else{
                    Log.d(TAG,"Locatiiion "+ location.toString());
                }
                break;
        }
    }
}
