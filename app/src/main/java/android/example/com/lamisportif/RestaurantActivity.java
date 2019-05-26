package android.example.com.lamisportif;

import android.example.com.lamisportif.helpful.MealAdapter;
import android.example.com.lamisportif.helpful.RestaurantAdapter;
import android.example.com.lamisportif.models.Meal;
import android.media.Image;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;

public class RestaurantActivity extends AppCompatActivity {

    private static final String TAG = " Restaurant Activity ";

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MealAdapter mealAdapter;

    LinkedList<Meal> myMeals = new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        //RecyclerView & NestedScroll
        recyclerView = findViewById(R.id.meals_list);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mealAdapter = new MealAdapter(myMeals,this);
        recyclerView.setAdapter(mealAdapter);

        // fill the list
        int j = 0;
        for(int i = 0;i<15;i++){
            myMeals.add(new Meal(
                    "price_"+i,
                    "description_"+i,
                    "designation_"+i,
                    "category_"+j
            ));
            if(i%5 == 0 && i!=0)
                j++;
        }
        // Changes when the the Toolbar is/is not collapsed
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.restaurant_collapsingToolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.restaurant_appbar);
        TextView restaurantNameField = (TextView) findViewById(R.id.restaurant_name_field);
        final ImageView collapseBackBtn = (ImageView) findViewById(R.id.collapse_back_btn); // Need OnClickListener
        final ImageView toolBarBackBtn = (ImageView) findViewById(R.id.toolbar_back_btn); // need OnClickListener (same code as collapseBackBtn)
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

}
