package android.example.com.lamisportif.fragments;

import android.content.Intent;
import android.example.com.lamisportif.OrdersActivity;
import android.example.com.lamisportif.ProfileActivity;
import android.example.com.lamisportif.R;
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

public class BottomMenuFragment extends BottomSheetDialogFragment {

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
