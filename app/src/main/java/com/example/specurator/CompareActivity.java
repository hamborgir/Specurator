package com.example.specurator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.specurator.model.PhoneModel;

public class CompareActivity extends AppCompatActivity {
    ImageView imageIV1, imageIV2;
    TextView
            nameTV1, releaseDateTV1, weightTV1, osTV1, storageTV1, screenSizeTV1, screenResolutionTV1, ramTV1, batteryTV1, cameraTV1, priceTV1,
            nameTV2, releaseDateTV2, weightTV2, osTV2, storageTV2, screenSizeTV2, screenResolutionTV2, ramTV2, batteryTV2, cameraTV2, priceTV2;

    ImageButton compareBackButton;

    PhoneModel phone1, phone2;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_compare);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        intent = getIntent();

        initViews();

        phone1 = (PhoneModel) intent.getSerializableExtra("phoneInDetailData");
        phone2 = (PhoneModel) intent.getSerializableExtra("phoneData");

        setViews(phone1, phone2);

        compareBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViews() {

        // phone 1
        imageIV1 = findViewById(R.id.imageIV1);
        nameTV1 = findViewById(R.id.nameTV1);
        releaseDateTV1 = findViewById(R.id.releaseDateTV1);
        weightTV1 = findViewById(R.id.weightTV1);
        osTV1 = findViewById(R.id.osTV1);
        storageTV1 = findViewById(R.id.storageTV1);
        screenSizeTV1 = findViewById(R.id.screenSizeTV1);
        screenResolutionTV1 = findViewById(R.id.screenResolutionTV1);
        ramTV1 = findViewById(R.id.ramTV1);
        batteryTV1 = findViewById(R.id.batteryTV1);
        cameraTV1 = findViewById(R.id.cameraTV1);
        priceTV1 = findViewById(R.id.priceTV1);

        // phone 2
        imageIV2 = findViewById(R.id.imageIV2);
        nameTV2 = findViewById(R.id.nameTV2);
        releaseDateTV2 = findViewById(R.id.releaseDateTV2);
        weightTV2 = findViewById(R.id.weightTV2);
        osTV2 = findViewById(R.id.osTV2);
        storageTV2 = findViewById(R.id.storageTV2);
        screenSizeTV2 = findViewById(R.id.screenSizeTV2);
        screenResolutionTV2 = findViewById(R.id.screenResolutionTV2);
        ramTV2 = findViewById(R.id.ramTV2);
        batteryTV2 = findViewById(R.id.batteryTV2);
        cameraTV2 = findViewById(R.id.cameraTV2);
        priceTV2 = findViewById(R.id.priceTV2);

    }

    private void setViews(PhoneModel phone1, PhoneModel phone2) {
        // phone data 1
        nameTV1.setText(phone1.getName());
        releaseDateTV1.setText(phone1.getRelease_date());
        osTV1.setText(phone1.getOs());
        weightTV1.setText(Double.toString(phone1.getWeight()));
        storageTV1.setText(Integer.toString(phone1.getStorage()));
        screenSizeTV1.setText(Double.toString(phone1.getScreen_size()));
        screenResolutionTV1.setText(phone1.getScreen_resolution());
        ramTV1.setText(Double.toString(phone1.getRam()));
        batteryTV1.setText(Integer.toString(phone1.getBattery()));
        cameraTV1.setText(Double.toString(phone1.getCamera()));
        priceTV1.setText("IDR "+ Double.toString(phone1.getPrice()));
        Glide.with(this).load(phone1.getImage()).into(imageIV1);

        // phone data 1
        nameTV2.setText(phone2.getName());
        releaseDateTV2.setText(phone2.getRelease_date());
        osTV2.setText(phone2.getOs());
        weightTV2.setText(Double.toString(phone2.getWeight()));
        storageTV2.setText(Integer.toString(phone2.getStorage()));
        screenSizeTV2.setText(Double.toString(phone2.getScreen_size()));
        screenResolutionTV2.setText(phone2.getScreen_resolution());
        ramTV2.setText(Double.toString(phone2.getRam()));
        batteryTV2.setText(Integer.toString(phone2.getBattery()));
        cameraTV2.setText(Double.toString(phone2.getCamera()));
        priceTV2.setText("IDR "+ Double.toString(phone2.getPrice()));
        Glide.with(this).load(phone2.getImage()).into(imageIV2);

        // buttons
        compareBackButton = findViewById(R.id.compareBackButton);
    }
}