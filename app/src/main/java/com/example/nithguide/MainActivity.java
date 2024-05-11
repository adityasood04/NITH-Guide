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

import com.example.nithguide.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private String currentLocation;

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private LocationCallback locationCallback;

    //gates
    private String gate1coor = "31.7016995, 76.5229893";
    private String gate2coor = "31.708900037447254, 76.52263773242339";



    //departments
    private String eedCoor = "31.708085400003576, 76.5271815531355";
    private String ecedCoor = "31.708109772830227, 76.52647808576009";
    private String cedCoor = "31.709144878905, 76.52730340702821";
    private String archdCoor = "31.709099884671755, 76.52625003184772";
    private String medCoor = "31.708903034644, 76.52670840431122";
    private String csedCoor = "31.70851407397333, 76.52701776228302";
    private String chemdCoor = "31.708085400003576, 76.5271815531355";
    private String phydCoor = "31.707984917458457, 76.52663048685703";

    // other buildings
    private String audiCoor = "31.70696584790547, 76.52749762818713";
    private String oatCoor = "31.70696584790547, 76.52749762818713";
    private String libraryCoor = "31.707149508347, 76.52685828667752";
    private String healthCenterCoor = "31.70620363308952, 76.52772360079611";
    private String adminCoor  = "31.708087726225166, 76.52810142058244";

    //parks and open areas
    private String SPcoor = "31.707121474493064, 76.52844713483385";

    //hostels
    private String KBHcoor = "31.710606994978843, 76.52660411380484";
    private String AGHcoor = "31.703996709191138, 76.52536551973783";

    private String destinationCoor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


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


        binding.btnAudi.setOnClickListener(view -> {
            destinationCoor = audiCoor;
            getCurrentLocation();
        });
        binding.btnKBH.setOnClickListener(view -> {
            destinationCoor = KBHcoor;
            getCurrentLocation();
        });
        binding.btnGate1.setOnClickListener(view -> {
            destinationCoor = gate1coor;
            getCurrentLocation();
        });
        binding.btnSP.setOnClickListener(view -> {
            destinationCoor = SPcoor;
            getCurrentLocation();
        });
        binding.btnGate2.setOnClickListener(view -> {
            destinationCoor = gate2coor;
            getCurrentLocation();
        });

    }


    private void launchMaps(String currentCoordinates, String destinationCoordinates) {
        Toast.makeText(this, "Launching maps", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,ActivityMapView.class);
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