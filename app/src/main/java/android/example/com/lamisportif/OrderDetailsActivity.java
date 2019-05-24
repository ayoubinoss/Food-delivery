package android.example.com.lamisportif;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kofigyan.stateprogressbar.StateProgressBar;

public class OrderDetailsActivity extends AppCompatActivity {

    String[] descriptionData = {"pending", "Accepted", "Dispatched", "Delivered"};
    TextView mItemDescription;
    ImageView mDescriptionImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.status);
        stateProgressBar.setStateDescriptionData(descriptionData);

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
