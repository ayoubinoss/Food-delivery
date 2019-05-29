package android.example.com.lamisportif.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.example.com.lamisportif.R;


public class PreCartFragment extends Fragment {


    public PreCartFragment() {
        // Required empty public constructor
    }

    public static PreCartFragment newInstance() {

        return new PreCartFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pre_cart_card,null);
        return v;
    }



}
