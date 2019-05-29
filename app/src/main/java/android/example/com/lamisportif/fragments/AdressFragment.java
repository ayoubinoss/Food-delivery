package android.example.com.lamisportif.fragments;

import android.example.com.lamisportif.R;
import android.example.com.lamisportif.helpful.AddressAdapter;
import android.example.com.lamisportif.helpful.MealAdapter;
import android.example.com.lamisportif.models.Meal;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.LinkedList;

public class AdressFragment extends DialogFragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private AddressAdapter addressAdapter;
    private Button mAddButton;

    LinkedList<String> myLocations = new LinkedList<>();

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

        //handle button event
        mAddButton = view.findViewById(R.id.button_add);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo add location
            }
        });

        myLocations.add("Boulevard Mohammed VI, Casablanca 20250");
        myLocations.add("Rue Sidi Oqba, Casablanca 20250");
        addressAdapter.notifyDataSetChanged();
    }
}
