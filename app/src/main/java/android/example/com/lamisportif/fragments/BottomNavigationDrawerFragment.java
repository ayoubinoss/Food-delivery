package android.example.com.lamisportif.fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.example.com.lamisportif.R;
import android.example.com.lamisportif.models.OrderLine;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.rpc.Help;

import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public class BottomNavigationDrawerFragment extends BottomSheetDialogFragment {

    LinkedList<OrderLine> mOrderLine = new LinkedList<>();
    private static final String SHARED_FILE = "LAmiSportif.cart";
    private static final String TAG = "Cart Fragment";


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

        getOrderLines();
        Log.d(TAG, "heeere " +mOrderLine);
    }
    public void getOrderLines(){
        SharedPreferences sp = getContext().getSharedPreferences(SHARED_FILE,getContext().MODE_PRIVATE);
        OrderLine orderLine = new OrderLine();
        Set<String> myOrderLines = sp.getStringSet("orderlines", new TreeSet<String>());
        Gson gson = new Gson();
        for(String key : myOrderLines){
            mOrderLine.add(gson.fromJson(key,OrderLine.class));
        }
    }
}
