package com.example.specurator;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

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

public class SearchActivity extends AppCompatActivity {

    ImageButton searchBackButton, searchHomeButton, searchSearchButton;
    RecyclerView searchRVContainer;
    EditText searchBarET;

    DBHelper dbHelper = DBHelper.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setButtons();

        searchBarET.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    String searchString = searchBarET.getText().toString();
                    List<PhoneModel> phoneList = dbHelper.getPhonesByName(searchString);

                    fillRV(phoneList);


                    return true;
                }
                return false;
            }
        });
    }



    private void fillRV(List<PhoneModel> phoneList) {
        PhoneAdapter adapter = new PhoneAdapter(phoneList);
        searchRVContainer.setLayoutManager(new LinearLayoutManager(this));
        searchRVContainer.setAdapter(adapter);
    }
    private void setButtons() {
        searchBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(SearchActivity.this, HomeActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });
    }

    private void initViews() {
        searchBackButton = findViewById(R.id.searchBackButton);
        searchHomeButton = findViewById(R.id.searchHomeButton);
        searchSearchButton = findViewById(R.id.searchSearchButton);
        searchRVContainer = findViewById(R.id.searchRVContainer);
        searchBarET = findViewById(R.id.searchBarET);
    }
}