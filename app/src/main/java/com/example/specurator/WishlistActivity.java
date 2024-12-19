package com.example.specurator;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
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

    RecyclerView wlRVContainer;

    public DBHelper dbHelper = DBHelper.getInstance(this);

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

        List<PhoneModel> phoneList = dbHelper.getWishlistPhones();

        wlRVContainer = findViewById(R.id.wlRVContainer);
        fillRV(phoneList);
    }

    private void fillRV(List<PhoneModel> phoneList) {
        PhoneAdapter adapter = new PhoneAdapter(phoneList);
        wlRVContainer.setLayoutManager(new LinearLayoutManager(this));
        wlRVContainer.setAdapter(adapter);
    }
}