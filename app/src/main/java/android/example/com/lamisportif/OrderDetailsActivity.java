package android.example.com.lamisportif;

import android.animation.ObjectAnimator;
import android.example.com.lamisportif.helpful.OrderAdapter;
import android.example.com.lamisportif.helpful.OrderLineOrderAdapter;
import android.example.com.lamisportif.models.Order;
import android.example.com.lamisportif.models.OrderLine;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.LinkedList;

public class OrderDetailsActivity extends AppCompatActivity {

    private String[] descriptionData = {"Pending", "Accepted", "Dispatched", "Delivered"};
    private TextView mItemDescription;
    private ImageView mDescriptionImg;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private OrderLineOrderAdapter orderAdapter;

    private ImageView mDeliveryManImage;
    private TextView mDeliveryManName;
    private TextView mDeliveryManPhone;

    String id_order; // get this field from extras and use it to get the details todo
    String myEmail; //get this field from FirebaseAuth todo

    LinkedList<OrderLine> orders = new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        /*get the views here*/
        mDeliveryManImage = findViewById(R.id.img_delivery_man);
        mDeliveryManName = findViewById(R.id.delevery_man_name);
        mDeliveryManPhone = findViewById(R.id.delivery_man_phone_number);

        StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.status);
        stateProgressBar.setStateDescriptionData(descriptionData);


        //RecyclerView & NestedScroll
        recyclerView = findViewById(R.id.list_item);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        orderAdapter = new OrderLineOrderAdapter(orders,this);
        recyclerView.setAdapter(orderAdapter);

        //Fill the list
        for(int i = 0;i<5;i++){
            orders.add(new OrderLine("RES Name bla bla bla bla bla bla bla bla bla",
                    15,
                    40,
                    14.5));
        }
        //refresh list
        orderAdapter.notifyDataSetChanged();
    }

    /**
     * a function to get the Delivery man details
     */
    public void getDeliveryManDetails() {
        //todo
    }
    /**
     * a function to get the order status
     */
    public void getOrderStatus() {
        //todo
    }

    /**
     * a function to get the order details
     */
    public void getOrderDetails() {
        //todo
    }
}
