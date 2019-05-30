package android.example.com.lamisportif;

import android.content.SharedPreferences;
import android.example.com.lamisportif.models.Location;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView mLocation;
    private Button mAddButton;
    private Location myLocation;

    private static final String TAG = "MapsActivity";
    private static final String SHARED_FILE = "locations";
    private static final String FIELD = "my_locations";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mLocation = findViewById(R.id.location);
        mAddButton = findViewById(R.id.button_add);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mLocation.getText())) {
                    //todo save the location to a shared preferences
                    SharedPreferences sp = getSharedPreferences(SHARED_FILE, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    Gson gson = new Gson();
                    String jsonAddress = gson.toJson(myLocation);
                    //get the set of addresses
                    Set<String> mySet = sp.getStringSet(FIELD, new HashSet<String>());
                    Log.d(TAG, "set before = " + mySet.toString());

                    mySet.add(jsonAddress);

                    Log.d(TAG, "set after = " + mySet.toString());
                    editor.putStringSet("my_locations",mySet);

                    editor.apply();
                    Log.d(TAG, "mySet" + mySet.toString());
                    finish();
                }
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap; //initialize the map
        float zoom = 15.0f; //define a default zoom : 15.0 to see streets
        Geocoder geocoder;//Geocoder class to get city, state, country, postal code, known name.
        List<Address> addresses =null;
        geocoder = new Geocoder(this, Locale.getDefault());
        LatLng home = new LatLng(33.567815, -7.589085);//default location to show for the user

        try {
            addresses = geocoder.getFromLocation(home.latitude, home.longitude, 1);
            // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0);
        // If any additional address line present than only, check with max available
        // address lines by getMaxAddressLineIndex().

        mLocation.setText(address);//set the location in the field.

        // Add a marker at home or the last position and move the camera
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.flag));
        mMap.addMarker(markerOptions.position(home).title("Marker at home"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home,zoom));

        //create an object Location
        myLocation = new Location(home.longitude, home.latitude, address);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.clear();//clear all the marker from the map
                String snippet = String.format(Locale.getDefault(),
                        "Lat: %1$.5f, Long: %2$.5f",
                        latLng.latitude,
                        latLng.longitude);
                Geocoder Igeocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = Igeocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String address = addresses.get(0).getAddressLine(0);
                myLocation = new Location(latLng.longitude, latLng.latitude, address);
                mLocation.setText(address);
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(getString(R.string.dropped_pin))
                        .snippet(snippet).icon(BitmapDescriptorFactory.fromResource(R.drawable.flag)));
            }
        });

    }

}
