package com.example.nithguide;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.nithguide.adapters.LocationsAdapter;
import com.example.nithguide.databinding.ActivityMainBinding;
import com.example.nithguide.helper.SpacesItemDecoration;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    ActivityMainBinding binding;
    ArrayList<Pair<String, Integer>> locations = new ArrayList<>();
    HashMap<String, String> coordinatesMap = new HashMap<>();
    Snackbar snackbar;
    String TAG = "main";
    private String currentLocation;
    private ProgressDialog progressDialog;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationCallback locationCallback;
    private String destinationCoor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvHeadMain.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(MainActivity.this, "Developed with ❤️ by Aditya Sood.", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        //locations
        locations.add(new Pair<>("NIT Gate 1", 0));
        locations.add(new Pair<>("NIT Gate 2", 0));
        locations.add(new Pair<>("Health Center", -2));
        locations.add(new Pair<>("Auditorium", 2));
        locations.add(new Pair<>("Admin Block", 1));
        locations.add(new Pair<>("Central Block", 1));
        locations.add(new Pair<>("Central Library", -1));
        locations.add(new Pair<>("Electrical Eng. Dept.", 56));
        locations.add(new Pair<>("Mechanical Eng. Dept.", 56));
        locations.add(new Pair<>("Computer Science and Eng. Dept.", 56));
        locations.add(new Pair<>("Electronics and Communication Eng. Dept.", 56));
        locations.add(new Pair<>("Mathematics and Scientific Computing", 56));
        locations.add(new Pair<>("Material Science Dept.", 56));
        locations.add(new Pair<>("Chemical Eng. Dept", 56));
        locations.add(new Pair<>("Architecture Department", 56));
        locations.add(new Pair<>("Civil Eng. Dept.", 56));
        locations.add(new Pair<>("Physics Dept.", 56));
        locations.add(new Pair<>("Kailash Boys Hostel", 100));
        locations.add(new Pair<>("Ambika Girls Hostel", 100));
        locations.add(new Pair<>("Students Park", 200));
        locations.add(new Pair<>("Open air theatre", 200));
        locations.add(new Pair<>("NITH Ground", 300));
        locations.add(new Pair<>("4H Food Court", 400));
        locations.add(new Pair<>("Verka Cafeteria", 400));
        locations.add(new Pair<>("Nescafe Cafeteria", 400));
        locations.add(new Pair<>("Food Court (Gate 2)", 400));


        coordinatesMap.put("NIT Gate 1", "31.7016995, 76.5229893");
        coordinatesMap.put("NIT Gate 2", "31.708900037447254, 76.52263773242339");
        coordinatesMap.put("Admin Block", "31.708087726225166, 76.52810142058244");
        coordinatesMap.put("Auditorium", "31.70696584790547, 76.52749762818713");
        coordinatesMap.put("Central Library", "31.707149508347, 76.52685828667752");
        coordinatesMap.put("Central Block", "31.707549772997243, 76.52801368892406");
        coordinatesMap.put("Health Center", "31.706186475059756, 76.52781465493845");
        coordinatesMap.put("Electrical Eng. Dept.", "31.708085400003576, 76.5271815531355");
        coordinatesMap.put("Electronics and Communication Eng. Dept.", "31.70813333570435, 76.52656497473657");
        coordinatesMap.put("Computer Science and Eng. Dept.", "31.70851407397333, 76.52701776228302");
        coordinatesMap.put("Mechanical Eng. Dept.", "31.708903034644, 76.52670840431122");
        coordinatesMap.put("Civil Eng. Dept.", "31.709144878905, 76.52730340702821");
        coordinatesMap.put("Physics Dept.", "31.707968227576515, 76.52665329122661");
        coordinatesMap.put("Chemical Eng. Dept", "31.708325106584287, 76.52611847303176");
        coordinatesMap.put("Material Science Dept.", "31.708005307747786, 76.52656544888137");
        coordinatesMap.put("Mathematics and Scientific Computing", "31.708428387399962, 76.52770993405153");
        coordinatesMap.put("Architecture Department", "31.709099884671755, 76.52625003184772");
        coordinatesMap.put("Kailash Boys Hostel", "31.710591505577213, 76.52675214258389");
        coordinatesMap.put("Ambika Girls Hostel", "31.703996709191138, 76.52536551973783");
        coordinatesMap.put("Students Park", "31.707112051883847, 76.52850730442567");
        coordinatesMap.put("Open air theatre", "31.70531806369203, 76.52518493127135");
        coordinatesMap.put("NITH Ground", "31.706161666422613, 76.52479654402494");

        coordinatesMap.put("4H Food Court", "31.710761393826147, 76.52696231292518");
        coordinatesMap.put("Nescafe Cafeteria", "31.7074170529067, 76.52825056015737");
        coordinatesMap.put("Food Court (Gate 2)", "31.70854909523703, 76.52276750882743");
        coordinatesMap.put("Verka Cafeteria", "31.706833626062885, 76.529061436059");
        coordinatesMap.put("Himgiri Boys Hostel", "31.70842276912479, 76.52389584343568");

        coordinatesMap.put("Himadri Boys Hostel", "31.708978893917582, 76.5239415378644");
        coordinatesMap.put("Neelkanth Boys Hostel", "31.710776488957514, 76.52381088420432");
        coordinatesMap.put("Udaygiri Boys Hostel", "31.70756046113581, 76.52385450835133");
        coordinatesMap.put("Dhauladhar Boys Hostel", "31.711615744695976, 76.52477451659834");
        coordinatesMap.put("Parvati Girls Hostel", "31.703643416450344, 76.52622916787968");
        coordinatesMap.put("Manimahesh Girls Hostel", "31.712199075829492, 76.52572448565554");

        LocationsAdapter adapter = new LocationsAdapter(MainActivity.this, locations, new LocationsAdapter.Listener() {
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


        // location callback
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
                if (progressDialog != null) {
                    progressDialog.dismiss();
                    if(destinationCoor != null) {
                        launchMaps(currentLocation,destinationCoor);
                    }
                    progressDialog = null;
                }

            }
        };
        int spanCount = 3; // 3 columns
        int spacing = 12;
        boolean includeEdge = true;
        binding.rcvLocations.addItemDecoration(new SpacesItemDecoration(spanCount, spacing, includeEdge));

        binding.rcvLocations.setLayoutManager(new GridLayoutManager(this, 3));

        binding.rcvLocations.setAdapter(adapter);


        binding.btnMap.setOnClickListener(view -> {
            startActivity(new Intent(this, ActivityMap.class));
        });


    }

    private void launchMaps(String currentCoordinates, String destinationCoordinates) {
        Toast.makeText(this, "Launching maps", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this, ActivityMapView.class);
        intent.putExtra("SOURCE", currentCoordinates);
        intent.putExtra("DESTINATION", destinationCoordinates);

        startActivity(intent);
        destinationCoor = null;
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

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

                if (currentLocation == null) {
                    progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Fetching your location. Please wait...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    requestLocation();
                    return;
                }
                launchMaps(currentLocation, destinationCoor);


            } else {
                Toast.makeText(this, "Please give location permissions", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(i);
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 100);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {


            if (isLocationEnabled()) {
                Log.i(TAG, "onActivityResult: location yes");
                if (currentLocation == null) {
                    progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Fetching your location. Please wait...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    requestLocation();
                    return;
                }
                launchMaps(currentLocation, destinationCoor);

                Toast.makeText(this, "GPS enabled", Toast.LENGTH_SHORT).show();
            } else {
                // GPS is still not enabled, show a message or take appropriate action
                Toast.makeText(this, "GPS is not enabled. Please enable GPS to proceed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
}