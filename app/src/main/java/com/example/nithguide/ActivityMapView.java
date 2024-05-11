package com.example.nithguide;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.nithguide.databinding.ActivityMapViewBinding;

public class ActivityMapView extends AppCompatActivity {

    private ActivityMapViewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMapViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String source = getIntent().getStringExtra("SOURCE");
        String destination = getIntent().getStringExtra("DESTINATION");


        binding.webView.getSettings().setJavaScriptEnabled(true);
        if (source == null || destination == null) return;
        String url = "https://www.google.com/maps/dir/" + source + "/" + destination;
        binding.webView.loadUrl(url);


        binding.btnBack.setOnClickListener(view -> finish());
    }
}