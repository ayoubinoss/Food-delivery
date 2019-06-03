package android.example.com.lamisportif.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.example.com.lamisportif.FirstActivity;
import android.example.com.lamisportif.OrdersActivity;
import android.example.com.lamisportif.ProfileActivity;
import android.example.com.lamisportif.R;
import android.example.com.lamisportif.helpful.NavigationHost;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class BottomMenuFragment extends BottomSheetDialogFragment {

    private static final String SHARED_FILE = "LAmiSportif.cart";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_bottom_menu, container,false);

        NavigationView navigationView = v.findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.commandes:
                        Toast.makeText(getActivity(),"commandes", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(), OrdersActivity.class));
                        break;
                    case R.id.profile:
                        Toast.makeText(getActivity(),"profile", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(), ProfileActivity.class));
                        break;
                    case R.id.deconnexion:
                        SharedPreferences sp = getContext().getSharedPreferences(SHARED_FILE,getContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear();
                        editor.apply();
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getActivity(),FirstActivity.class));
                        Toast.makeText(getActivity(),"deconnexion", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }


}
