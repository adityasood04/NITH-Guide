package com.example.nithguide;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.nithguide.adapters.LocationsAdapter;
import com.example.nithguide.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private String currentLocation;

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private LocationCallback locationCallback;

    private String destinationCoor;

    ArrayList<String> locations = new ArrayList<>();
    HashMap<String , String> coordinatesMap = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        //locations
        locations.add("Gate 1");
        locations.add("Gate 2");
        locations.add("Administration Block");
        locations.add("Auditorium");
        locations.add("Central Library");
        locations.add("Central Block");
        locations.add("Health Center");
        locations.add("EED");
        locations.add("ECED");
        locations.add("CSED");
        locations.add("MED");
        locations.add("CED");
        locations.add("PHYD");
        locations.add("CHEMD");
        locations.add("MSD");
        locations.add("MNCD");
        locations.add("Architecture Department");
        locations.add("KBH");
        locations.add("AGH");
        locations.add("SP");
        locations.add("OAT");
        locations.add("NITH Ground");


        coordinatesMap.put("Gate 1","31.7016995, 76.5229893");
        coordinatesMap.put("Gate 2","31.708900037447254, 76.52263773242339");
        coordinatesMap.put("Administration Block","31.708087726225166, 76.52810142058244");
        coordinatesMap.put("Auditorium","31.70696584790547, 76.52749762818713");
        coordinatesMap.put("Central Library","31.707149508347, 76.52685828667752");
        coordinatesMap.put("Central Block","31.707549772997243, 76.52801368892406");
        coordinatesMap.put("Health Center","31.70617193420401, 76.52771865049216");
        coordinatesMap.put("EED","31.708085400003576, 76.5271815531355");
        coordinatesMap.put("ECED","31.708109772830227, 76.52647808576009");
        coordinatesMap.put("CSED","31.70851407397333, 76.52701776228302");
        coordinatesMap.put("MED","31.708903034644, 76.52670840431122");
        coordinatesMap.put("CED","31.709144878905, 76.52730340702821");
        coordinatesMap.put("PHYD","31.707984917458457, 76.52663048685703");
        coordinatesMap.put("CHEMD","31.708085400003576, 76.5271815531355");
        coordinatesMap.put("MSD","31.708032556621895, 76.52652131865193");
        coordinatesMap.put("MNCD","31.708425511985663, 76.5276463632699");
        coordinatesMap.put("Architecture Department","31.709099884671755, 76.52625003184772");
        coordinatesMap.put("KBH","31.710606994978843, 76.52660411380484");
        coordinatesMap.put("AGH","31.703996709191138, 76.52536551973783");
        coordinatesMap.put("SP","31.707121474493064, 76.52844713483385");
        coordinatesMap.put("OAT","31.70696584790547, 76.52749762818713");
        coordinatesMap.put("NITH Ground","31.70621256652986, 76.52479377889479");

        LocationsAdapter adapter =  new LocationsAdapter(MainActivity.this, locations, new LocationsAdapter.Listener() {
            @Override
            public void onLocationClicked(String locationName, int position) {
                destinationCoor = coordinatesMap.get(locationName);
                getCurrentLocation();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Define location callback
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    currentLocation = latitude + "," + longitude;
                    Toast.makeText(MainActivity.this, "Current Location: " + latitude + ", " + longitude, Toast.LENGTH_SHORT).show();
                }
            }
        };


        binding.rcvLocations.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        binding.rcvLocations.setAdapter(adapter);


        binding.btnMap.setOnClickListener(view -> {
            startActivity(new Intent(this, ActivityMap.class));
        });


    }


    private void launchMaps(String currentCoordinates, String destinationCoordinates) {
        Toast.makeText(this, "Launching maps", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this,ActivityMapView.class);
        intent.putExtra("SOURCE", currentCoordinates);
        intent.putExtra("DESTINATION", destinationCoordinates);

        startActivity(intent);
    }


    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void getCurrentLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions();
                    return;
                }
                mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();

                    if (location != null) {
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        try {
                            List<Address> list = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            currentLocation = list.get(0).getLatitude()+","+list.get(0).getLongitude();
                            launchMaps(currentLocation,destinationCoor);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please give location permissions", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        } else {
            requestPermissions();
        }
    }
    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                1
        );
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                requestPermissions();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}