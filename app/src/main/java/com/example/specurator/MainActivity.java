package com.example.specurator;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView mainRVContainer;

    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private List<PhoneModel> phoneList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // copy db ke internal storage
//        dbHelper = DBHelper.getInstance(this);
//        try {
//            dbHelper.copyDatabase();
//            database = dbHelper.openDatabase();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Log.d("main", "onCreate: db copied");
//        initPhoneList();
//        Log.d("phoneList", "onCreate: " + phoneList.size());
//
//        PhoneAdapter adapter = new PhoneAdapter(phoneList);
//
//        mainRVContainer= findViewById(R.id.mainRVContainer);
//        mainRVContainer.setLayoutManager(new LinearLayoutManager(this));
//        mainRVContainer.setAdapter(adapter);


    }

//    private void initPhoneList() {
//        phoneList = dbHelper.getPhones(null);
//
//    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        dbHelper.closeDatabase();
//    }
}