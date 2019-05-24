package android.example.com.lamisportif;

import android.media.Image;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
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
