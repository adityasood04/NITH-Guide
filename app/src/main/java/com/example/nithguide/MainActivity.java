package com.example.nithguide;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
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
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private String currentLocation;
    private ProgressDialog progressDialog;

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private LocationCallback locationCallback;

    private String destinationCoor;

    ArrayList<String> locations = new ArrayList<>();
    HashMap<String , String> coordinatesMap = new HashMap<>();
    Snackbar snackbar;


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
        locations.add("Electrical Eng. Dept.");
        locations.add("Electronics and Communication Eng. Dept.");
        locations.add("Computer Science and Eng. Dept.");
        locations.add("Mechanical Eng. Dept.");
        locations.add("Civil Eng. Dept.");
        locations.add("Physics Dept.");
        locations.add("Chemical Eng. Dept");
        locations.add("Material Science Dept.");
        locations.add("Mathematics and Scientific Computing");
        locations.add("Architecture Department");
        locations.add("Kailash Boys Hostel");
        locations.add("Ambika Girls Hostel");
        locations.add("Students Park");
        locations.add("Open air theatre");
        locations.add("NITH Ground");


        coordinatesMap.put("Gate 1","31.7016995, 76.5229893");
        coordinatesMap.put("Gate 2","31.708900037447254, 76.52263773242339");
        coordinatesMap.put("Administration Block","31.708087726225166, 76.52810142058244");
        coordinatesMap.put("Auditorium","31.70696584790547, 76.52749762818713");
        coordinatesMap.put("Central Library","31.707149508347, 76.52685828667752");
        coordinatesMap.put("Central Block","31.707549772997243, 76.52801368892406");
        coordinatesMap.put("Health Center","31.70617193420401, 76.52771865049216");
        coordinatesMap.put("Electrical Eng. Dept.","31.708085400003576, 76.5271815531355");
        coordinatesMap.put("Electronics and Communication Eng. Dept.","31.70813262048218, 76.5265409887605");
        coordinatesMap.put("Computer Science and Eng. Dept.","31.70851407397333, 76.52701776228302");
        coordinatesMap.put("Mechanical Eng. Dept.","31.708903034644, 76.52670840431122");
        coordinatesMap.put("Civil Eng. Dept.","31.709144878905, 76.52730340702821");
        coordinatesMap.put("Physics Dept.","31.70797416477527, 76.52661849164565");
        coordinatesMap.put("Chemical Eng. Dept","31.708325106584287, 76.52611847303176");
        coordinatesMap.put("Material Science Dept.","31.707619790108954, 76.52792399973634");
        coordinatesMap.put("Mathematics and Scientific Computing","31.708443539991, 76.5276638254619");
        coordinatesMap.put("Architecture Department","31.709099884671755, 76.52625003184772");
        coordinatesMap.put("Kailash Boys Hostel","31.71059356898743, 76.52662886032971");
        coordinatesMap.put("Ambika Girls Hostel","31.703996709191138, 76.52536551973783");
        coordinatesMap.put("Students Park","31.707145160821206, 76.52849531025659");
        coordinatesMap.put("Open air theatre","31.70531806369203, 76.52518493127135");
        coordinatesMap.put("NITH Ground","31.706161666422613, 76.52479654402494");

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
                Log.i(TAG, "onLocationResult: location callback");
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    currentLocation = latitude + "," + longitude;
//                    Toast.makeText(MainActivity.this, "Current Location: " + latitude + ", " + longitude, Toast.LENGTH_SHORT).show();
                }
                if(progressDialog!= null){
                    snackbar = Snackbar.make(binding.getRoot(),"Location fetched successfully. Click on a destination.",Snackbar.LENGTH_SHORT).setAction("Okay", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(snackbar!= null)
                                snackbar.dismiss();
                        }
                    });
                    snackbar.setBackgroundTint(getColor(R.color.white));
                    snackbar.setTextColor(getColor(R.color.black));
                    snackbar.setActionTextColor(getColor(R.color.blue));
                    snackbar.show();
//                    Toast.makeText(MainActivity.this, "Location fetched successfully. Click on a destination.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    progressDialog = null;
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
String TAG = "main";
    private void getCurrentLocation() {
        Log.i(TAG, "getCurrentLocation: called");
        if (checkPermissions()) {
            Log.i(TAG, "checkpermission: passes");

            if (isLocationEnabled()) {
                Log.i(TAG, "getCurrentLocation: location is enabled");
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "getCurrentLocation: requesting permissions");
                    requestPermissions();
                    return;
                }

                if(currentLocation == null){
                    progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Fetching your location. Please wait...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    requestLocation();
                    return;
                }
                launchMaps(currentLocation, destinationCoor);


            }
            else {
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


    private LocationRequest createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000); // Update location every 10 seconds
        return locationRequest;
    }


    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            mFusedLocationProviderClient.requestLocationUpdates(createLocationRequest(), locationCallback, null);
        }
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



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
}