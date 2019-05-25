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

    String[] descriptionData = {"Pending", "Accepted", "Dispatched", "Delivered"};
    TextView mItemDescription;
    ImageView mDescriptionImg;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private OrderLineOrderAdapter orderAdapter;

    LinkedList<OrderLine> orders = new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

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
            orderAdapter.notifyDataSetChanged();
        }
        //refresh list
        orderAdapter.notifyDataSetChanged();
        /*mItemDescription = (TextView) findViewById(R.id.item_description);
        mDescriptionImg = (ImageView) findViewById(R.id.item_description_img);

        mDescriptionImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collapseExpandTextView();
            }
        });*/
    }

    /*void collapseExpandTextView() {
        if (mItemDescription.getVisibility() == View.GONE) {
            // it's collapsed - expand it
            mItemDescription.setVisibility(View.VISIBLE);
            mDescriptionImg.setImageResource(R.drawable.ic_less_infos);
        } else {
            // it's expanded - collapse it
            mItemDescription.setVisibility(View.GONE);
            mDescriptionImg.setImageResource(R.drawable.ic_more_infos);
        }

        ObjectAnimator animation = ObjectAnimator.ofInt(mItemDescription, "maxLines", mItemDescription.getMaxLines());
        animation.setDuration(200).start();
    }*/
}
