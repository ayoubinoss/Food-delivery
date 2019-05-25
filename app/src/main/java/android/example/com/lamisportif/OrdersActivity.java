package android.example.com.lamisportif;

import android.example.com.lamisportif.helpful.OrderAdapter;
import android.example.com.lamisportif.helpful.RestaurantAdapter;
import android.example.com.lamisportif.models.Order;
import android.example.com.lamisportif.models.Restaurant;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;

public class OrdersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private OrderAdapter orderAdapter;

    LinkedList<Order> orders = new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        //RecyclerView & NestedScroll
        recyclerView = findViewById(R.id.order_list);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        orderAdapter = new OrderAdapter(orders,this);
        recyclerView.setAdapter(orderAdapter);

        //Fill the list
        for(int i = 0;i<5;i++){
            orders.add(new Order("RES Name",
                                 "Accepted",
                                 "https://www.gstatic.com/webp/gallery/5.jpg",
                                 14.5,
                                 "4 x Menu double Twister Normal, Pepsi, Frites normales"));
            orderAdapter.notifyDataSetChanged();
        }
        //refresh list
        orderAdapter.notifyDataSetChanged();
    }
}
