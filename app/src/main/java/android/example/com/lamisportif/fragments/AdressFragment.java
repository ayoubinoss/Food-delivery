package android.example.com.lamisportif.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.example.com.lamisportif.MapsActivity;
import android.example.com.lamisportif.OrderDetailsActivity;
import android.example.com.lamisportif.R;
import android.example.com.lamisportif.helpful.AddressAdapter;
import android.example.com.lamisportif.helpful.MealAdapter;
import android.example.com.lamisportif.models.Location;
import android.example.com.lamisportif.models.Meal;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.prefs.AbstractPreferences;

public class AdressFragment extends DialogFragment implements View.OnClickListener {

    private Button mAddButton;
    private Button mNextButton;

    private RadioGroup mRadioGroup;
    private static final String TAG = "AdressFragment";
    private static final String SHARED_FILE = "locations";
    private static final String FIELD = "my_locations";

    LinkedList<Location> myLocations = new LinkedList<>();
    private Map<Integer,Location> mapButtons =  new HashMap<>();
    LinkedList<RadioButton> mRadioButtons = new LinkedList<>();
    private boolean mButtonChecked;

    private int idRadioButton = 1000;

    public AdressFragment() {

    }

    public static AdressFragment newInstance(String title) {
        AdressFragment frag = new AdressFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Choose a location");
        getDialog().setTitle(title);

        mRadioGroup = view.findViewById(R.id.radio_group);
        mButtonChecked = false;
        //handle buttons event
        mAddButton = view.findViewById(R.id.button_add);
        mAddButton.setOnClickListener(this);

        mNextButton = view.findViewById(R.id.button_next);
        mNextButton.setOnClickListener(this);


        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mButtonChecked = true;
                mNextButton.setEnabled(true);
            }
        });
        getAllLocations();
        for(Location location : myLocations) {
            createRadioButtons(location);
        }
    }

    /**
     * a function to get all location from a Shared file
     */
    public void getAllLocations() {
        myLocations.clear();
        SharedPreferences sp = getActivity().getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Set<String> mySet = sp.getStringSet(FIELD, new HashSet<String>());
        Log.d(TAG, "Set in fragment = " + mySet.toString());
        for(String address: mySet) {
            Location location = gson.fromJson(address, Location.class);
            myLocations.add(location);
        }

    }

    /**
     * create a radio button inside the mRadioGroup field
     * @param location an address to select
     */
    private void createRadioButtons(Location location) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setText(location.getAddress());
        radioButton.setTextSize(18);
        mapButtons.put(idRadioButton,location);
        radioButton.setId(idRadioButton++);
        setRadioButtonAttribute(radioButton);
        mRadioGroup.addView(radioButton);
        mRadioButtons.add(radioButton);
    }

    /**
     * set parameter for the radioButton created
     * @param radioButton a radio button to set it's attributes
     */
    private void setRadioButtonAttribute(RadioButton radioButton) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        radioButton.setLayoutParams(params);
    }

    /**
     * an ovveride methode to handle click events
     * @param v the view clicked
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_add:
                startActivity(new Intent(getContext(), MapsActivity.class));
                break;
            case R.id.button_next:
                if(isFormValid()) {
                    //todo store the value of Location value to a shared preferences
                    mNextButton.setEnabled(true);
                    Log.d(TAG, "location is here here = " + mapButtons.get(getAnswerId()).toString());
                    SharedPreferences sp = getActivity().getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(mapButtons.get(getAnswerId()));
                    Log.d(TAG,"json : " + json);
                    editor.putString("chosenLocation",json);
                    Log.d(TAG,"json 2 " + sp.getString("chosenLocation","")); // Check the log here
                    editor.apply();
                    dismiss();
                   // startActivity(new Intent(getContext(), OrderDetailsActivity.class));
                } else {
                    Toast.makeText(getContext()," you should select an address",Toast.LENGTH_SHORT).show();
                }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllLocations();
        updateRadioButtons();
        mNextButton.setEnabled(false);
        Log.d(TAG, "locations = " + myLocations.toString());
    }

    private void updateRadioButtons() {

    }

    public boolean isFormValid() {
        return mButtonChecked;
    }

    public int getAnswerId() {
        for(RadioButton r : mRadioButtons) {
            if(r.isChecked()) {
                return r.getId();
            }
        }
        return 0;
    }
}
