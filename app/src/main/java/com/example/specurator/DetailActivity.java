package com.example.specurator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.specurator.model.PhoneModel;

public class DetailActivity extends AppCompatActivity {

    ImageView imageIV;
    TextView nameTV, releaseDateTV, weightTV, osTV, storageTV, screenSizeTV, screenResolutionTV,
            ramTV, batteryTV, cameraTV, priceTV;

    ImageButton detailBackButton, detailHomeButton, detailSearchButton;

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        intent = getIntent();
        PhoneModel phone = (PhoneModel) intent.getSerializableExtra("phoneData");

        if (phone != null) {
            setViews(phone);
        }

        // TODOS:
        // CARI CARA HANDLE HOME BUTTON BIAR BISA MILIH ANTARA FINISH ATAU INTENT
        // YG INI NAMBAH STACK ACTIVITY MULU
        detailBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        detailHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(DetailActivity.this, HomeActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });

        detailSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(DetailActivity.this, SearchActivity.class);
                searchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(searchIntent);
            }
        });
    }

    private void setViews(PhoneModel phone) {
        // phone data related
        nameTV.setText(phone.getName());
        releaseDateTV.setText(phone.getRelease_date());
        osTV.setText(phone.getOs());
        weightTV.setText(Double.toString(phone.getWeight()));
        storageTV.setText(Integer.toString(phone.getStorage()));
        screenSizeTV.setText(Double.toString(phone.getScreen_size()));
        screenResolutionTV.setText(phone.getScreen_resolution());
        Log.d("dbg", "setViews: " + phone.getScreen_resolution());
        ramTV.setText(Double.toString(phone.getRam()));
        batteryTV.setText(Integer.toString(phone.getBattery()));
        cameraTV.setText(Double.toString(phone.getCamera()));
        priceTV.setText("IDR "+ Double.toString(phone.getPrice()));
        Glide.with(this).load(phone.getImage()).into(imageIV);

        // others
        detailBackButton = findViewById(R.id.detailBackButton);
        detailHomeButton = findViewById(R.id.detailHomeButton);
        detailSearchButton = findViewById(R.id.detailSearchButton);
    }

    private void initViews() {

        imageIV = findViewById(R.id.imageIV);
        nameTV = findViewById(R.id.nameTV);
        releaseDateTV = findViewById(R.id.releaseDateTV);
        weightTV = findViewById(R.id.weightTV);
        osTV = findViewById(R.id.osTV);
        storageTV = findViewById(R.id.storageTV);
        screenSizeTV = findViewById(R.id.screenSizeTV);
        screenResolutionTV = findViewById(R.id.screenResolutionTV);
        ramTV = findViewById(R.id.ramTV);
        batteryTV = findViewById(R.id.batteryTV);
        cameraTV = findViewById(R.id.cameraTV);
        priceTV = findViewById(R.id.priceTV);

    }
}