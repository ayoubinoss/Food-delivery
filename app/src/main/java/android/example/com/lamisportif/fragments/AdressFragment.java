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

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public class AdressFragment extends DialogFragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private AddressAdapter addressAdapter;
    private Button mAddButton;
    private Button mNextButton;

    private static final String TAG = "AdressFragment";
    private static final String SHARED_FILE = "locations";
    private static final String FIELD = "my_locations";

    LinkedList<Location> myLocations = new LinkedList<>();

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

        //recycler view configuration
        recyclerView = view.findViewById(R.id.address_list);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        addressAdapter = new AddressAdapter(myLocations,getContext());
        recyclerView.setAdapter(addressAdapter);

        //handle buttons event
        mAddButton = view.findViewById(R.id.button_add);
        mAddButton.setOnClickListener(this);

        mNextButton = view.findViewById(R.id.button_next);
        mNextButton.setOnClickListener(this);
    }
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

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_add:
                startActivity(new Intent(getContext(), MapsActivity.class));
                break;
            case R.id.button_next:
                dismiss();
                startActivity(new Intent(getContext(), OrderDetailsActivity.class));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllLocations();
        Log.d(TAG, "locations = " + myLocations.toString());
        addressAdapter.notifyDataSetChanged();
    }
}
