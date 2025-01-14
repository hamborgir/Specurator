package com.example.specurator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.specurator.adapter.PhoneAdapter;
import com.example.specurator.database.DBHelper;
import com.example.specurator.model.PhoneModel;

import java.util.List;

public class WishlistActivity extends AppCompatActivity {
    ImageButton wlBackButton, wlHomeButton, wlSearchButton, wlWishlistButton;

    RecyclerView wlRVContainer;

    public DBHelper dbHelper = DBHelper.getInstance(this);

    PhoneAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wishlist);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setButtons();

        List<PhoneModel> phoneList = dbHelper.getWishlistPhones();

        wlRVContainer = findViewById(R.id.wlRVContainer);

        fillRV(phoneList);
    }

    private void setButtons() {
        wlBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        wlHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(WishlistActivity.this, HomeActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });

        wlSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(WishlistActivity.this, SearchActivity.class);
                searchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(searchIntent);
            }
        });
    }

    private void initViews() {
        wlBackButton = findViewById(R.id.wlBackButton);
        wlHomeButton = findViewById(R.id.wlHomeButton);
        wlSearchButton = findViewById(R.id.wlSearchButton);
        wlWishlistButton = findViewById(R.id.wlWishlistButton);
    }

    private void fillRV(List<PhoneModel> phoneList) {
        adapter = new PhoneAdapter(phoneList);
        wlRVContainer.setLayoutManager(new LinearLayoutManager(this));
        wlRVContainer.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshWishlist();
    }

    private void refreshWishlist() {
        List<PhoneModel> updatedWishlist = dbHelper.getWishlistPhones();
        adapter.setPhoneList(updatedWishlist);
        adapter.notifyDataSetChanged();
    }

}