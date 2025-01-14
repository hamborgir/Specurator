package com.example.specurator;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.specurator.adapter.PhoneAdapter;
import com.example.specurator.database.DBHelper;
import com.example.specurator.model.PhoneModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout mainDrawerLayout;
    NavigationView mainNavView;
    RecyclerView mainRVContainer;
    ImageButton mainNavButton, mainSearchButton, mainWishlistButton;

    String lastClicked = "All";
    String[] brands = {"All", "Samsung", "Xiaomi", "Asus", "Google", "OnePlus", "Infinix", "Oppo", "Vivo"};


    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private List<PhoneModel> phoneList;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

    private void callNotifService(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()){
                            Log.wtf("Firebase Messaging", "Fetching FCM registration token failed");
                            return;
                        }

                        String token = task.getResult();
                        Log.d("Firebase Messaging", "FCM Token: " + token);
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainDrawerLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mainRVContainer= findViewById(R.id.mainRVContainer);
        mainDrawerLayout = findViewById(R.id.mainDrawerLayout);
        mainNavView = findViewById(R.id.mainNavView);
        mainNavButton = findViewById(R.id.mainNavButton);
        mainSearchButton = findViewById(R.id.mainSearchButton);
        mainWishlistButton = findViewById(R.id.mainWishlistButton);

        Boolean deleted = this.deleteDatabase("specurator.db");
        Log.d("deleted", "onCreate: "+deleted);
        initDB();
        initPhoneList();
        fillRV(phoneList);



        mainNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainDrawerLayout.openDrawer(GravityCompat.START);

                mainNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        if (item.getItemId() == R.id.filter_all && lastClicked != "All") {
//                            Toast.makeText(HomeActivity.this, "halo!", Toast.LENGTH_SHORT).show();
                            lastClicked = "All";
                        }
                        else if (item.getItemId() == R.id.filter_samsung && lastClicked != "Samsung") {
//                            Toast.makeText(HomeActivity.this, "halo!", Toast.LENGTH_SHORT).show();
//                            phoneList = dbHelper.getPhones("Samsung");
//                            fillRV(phoneList);
                            lastClicked = "Samsung";
                        }
                        else if (item.getItemId() == R.id.filter_xiaomi && lastClicked != "Xiaomi") {
                            lastClicked = "Xiaomi";
                        }

                        else if (item.getItemId() == R.id.filter_asus && lastClicked != "Asus") {
                            lastClicked = "Asus";
                        }
                        else if (item.getItemId() == R.id.filter_google && lastClicked != "Google") {
                            lastClicked = "Google";
                        }
                        else if (item.getItemId() == R.id.filter_oneplus && lastClicked != "OnePlus") {
                            lastClicked = "OnePlus";
                        }
                        else if (item.getItemId() == R.id.filter_infinix && lastClicked != "Infinix") {
                            lastClicked = "Infinix";
                        } else if (item.getItemId() == R.id.filter_oppo && lastClicked != "Oppo") {
                            lastClicked = "Oppo";
                        } else {
                            lastClicked = "Vivo";
                        }

                        phoneList = dbHelper.getPhones(lastClicked);
                        fillRV(phoneList);


                        mainDrawerLayout.closeDrawer(GravityCompat.START);
                        return false;
                    }
                });
            }
        });

        mainSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveIntent = new Intent(HomeActivity.this, SearchActivity.class);
                moveIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(moveIntent);
            }
        });

        mainWishlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wishlistIntent = new Intent(HomeActivity.this, WishlistActivity.class);
                startActivity(wishlistIntent);
            }
        });


        callNotifService();
        askNotificationPermission();
    }

    private void fillRV(List<PhoneModel> phoneList) {
        PhoneAdapter adapter = new PhoneAdapter(phoneList);
        mainRVContainer.setLayoutManager(new LinearLayoutManager(this));
        mainRVContainer.setAdapter(adapter);
    }

    private void initDB() {
        // copy db ke internal storage
        dbHelper = DBHelper.getInstance(this);
        try {
            dbHelper.copyDatabase();
            database = dbHelper.openDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPhoneList() {
        phoneList = dbHelper.getPhones("All");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.closeDatabase();
    }
}