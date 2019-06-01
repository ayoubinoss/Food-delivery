package android.example.com.lamisportif.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.button.MaterialButton;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.example.com.lamisportif.R;

import static android.content.Context.MODE_PRIVATE;

public class ConfirmFragment extends DialogFragment implements View.OnClickListener {
    MaterialButton confirm;
    MaterialButton reject;
    private static final String SHARED_FILE = "LAmiSportif.cart";

    public ConfirmFragment() {
        // Required empty public constructor
    }

    public static ConfirmFragment newInstance() {

        return new ConfirmFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_confirm, container, false);
        confirm = v.findViewById(R.id.button_ok);
        reject = v.findViewById(R.id.button_no);
        confirm.setOnClickListener(this);
        reject.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_ok:
                SharedPreferences sp = getContext().getSharedPreferences(SHARED_FILE,MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();
                dismiss();
                break;
            case R.id.button_no:
                dismiss();
                break;
        }

    }
}
