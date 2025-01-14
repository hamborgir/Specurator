package com.example.specurator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.specurator.model.PhoneModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "specurator.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_SPECS = "phone_specs";
    public static final String TABLE_WISHLIST = "wishlist";


    // public db info
    public static final String FIELD_SPECS_ID = "id";
    public static final String FIELD_SPECS_IMAGE = "image";
    public static final String FIELD_SPECS_BRAND = "brand";
    public static final String FIELD_SPECS_NAME = "name";
    public static final String FIELD_SPECS_RELEASE_DATE = "release_date";
    public static final String FIELD_SPECS_WEIGHT = "weight";
    public static final String FIELD_SPECS_OS = "os";
    public static final String FIELD_SPECS_STORAGE = "storage";
    public static final String FIELD_SPECS_SCREEN_SIZE = "screen_size";
    public static final String FIELD_SPECS_SCREEN_RESOLUTION = "screen_resolution";
    public static final String FIELD_SPECS_RAM = "ram";
    public static final String FIELD_SPECS_BATTERY = "battery";
    public static final String FIELD_SPECS_CAMERA = "camera";
    public static final String FIELD_SPECS_PRICE = "price";
    public static final String FIELD_WISHLIST_ID = "id";
    public static final String FIELD_WISHLIST_SPECS_ID = "phone_id";

    private static DBHelper instance;
    private static SQLiteDatabase database;

    Context context;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create DB and table, but we dont need it
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // check current DB ver. compared to latest ver.


    }

    public static synchronized DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    public SQLiteDatabase openDatabase() {
        if (database == null || !database.isOpen()) {
            database = SQLiteDatabase.openDatabase(context.getDatabasePath(DB_NAME).getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        }
        return database;
    }

    public void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    public void copyDatabase() throws IOException {
        File dbFile = context.getDatabasePath(DB_NAME);
        if (dbFile.exists()) {
            return;
        }


        File dir = dbFile.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Open the input stream for the database file in assets
        InputStream inputStream = context.getAssets().open(DB_NAME);
        // Open the output stream to write to the internal storage
        OutputStream outputStream = new FileOutputStream(dbFile);

        // Create a buffer to hold the data being transferred
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }


        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public List<PhoneModel> getPhones(String brand) {
        List<PhoneModel> phoneList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = null;
        String[] selectionArgs = null;

        if (brand != "All") {
            selection = FIELD_SPECS_BRAND + " = ?";
            selectionArgs = new String[]{brand};
        }

        Cursor cursor = db.query(
                TABLE_SPECS,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        phoneList = extractPhoneData(cursor);

//        db.close();
        return phoneList;
    }

    public List<PhoneModel> getPhonesByName(String searchString) {
        List<PhoneModel> phoneList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = FIELD_SPECS_NAME + " LIKE ?";
        String[] selectionArgs = new String[]{"%" + searchString + "%"}; // Add wildcards

        Cursor cursor = db.query(
                TABLE_SPECS,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

//        if (cursor.moveToFirst()) {
//            do {
//                int id = cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_SPECS_ID));
//                String image = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_SPECS_IMAGE));
//                String brand = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_SPECS_BRAND));
//                String name = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_SPECS_NAME));
//                String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_SPECS_RELEASE_DATE));
//                double weight = cursor.getDouble(cursor.getColumnIndexOrThrow(FIELD_SPECS_WEIGHT));
//                String os = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_SPECS_OS));
//                int storage = cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_SPECS_STORAGE));
//                double screenSize = cursor.getDouble(cursor.getColumnIndexOrThrow(FIELD_SPECS_SCREEN_SIZE));
//                String screenResolution = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_SPECS_SCREEN_RESOLUTION));
//                double ram = cursor.getDouble(cursor.getColumnIndexOrThrow(FIELD_SPECS_RAM));
//                int battery = cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_SPECS_BATTERY));
//                double camera = cursor.getDouble(cursor.getColumnIndexOrThrow(FIELD_SPECS_CAMERA));
//                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(FIELD_SPECS_PRICE));
//
//                PhoneModel phone = new PhoneModel(id, image, brand, name, releaseDate, weight, os, storage, screenSize, screenResolution, ram, battery, camera, price);
//                phoneList.add(phone);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();

        phoneList = extractPhoneData(cursor);

//        db.close();
        return phoneList;
    }

    public List<PhoneModel> getWishlistPhones() {
        List<PhoneModel> wishlistPhone = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the JOIN query
        String query = "SELECT "+ TABLE_SPECS + ".* FROM " + TABLE_SPECS +
                " INNER JOIN " + TABLE_WISHLIST + " ON " +
                TABLE_SPECS + "." + FIELD_SPECS_ID + " = " + TABLE_WISHLIST + "." + FIELD_WISHLIST_SPECS_ID;

        Cursor cursor = db.rawQuery(query, null);
        wishlistPhone = extractPhoneData(cursor);

//        db.close();
        return wishlistPhone;
    }

    public void addToWishlist(int phoneId) {
        SQLiteDatabase db = this.openDatabase();

        // Check if the phoneId already exists in the wishlist
        String query = "SELECT 1 FROM " + TABLE_WISHLIST + " WHERE " + FIELD_WISHLIST_SPECS_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(phoneId)});

        if (cursor.getCount() == 0) { // phoneId does not exist in wishlist
            ContentValues values = new ContentValues();
            values.put(FIELD_WISHLIST_SPECS_ID, phoneId);
            db.insert(TABLE_WISHLIST, null, values);
        }

        Log.d("DBHelper:AddToWishlist", "addToWishlist: "+phoneId);
        cursor.close();

    }

    public void removeFromWishlist(int phoneId) {
        SQLiteDatabase db = this.openDatabase();
        int rowsCount = db.delete(TABLE_WISHLIST, FIELD_WISHLIST_SPECS_ID + " = ?", new String[]{String.valueOf(phoneId)});
        Log.d("DBHelper:RemoveWishlist", "removeFromWishlist: "+phoneId+" cols: "+ rowsCount);
    }

    public boolean isPhoneInWishlist(int phoneId) {
        SQLiteDatabase db = this.openDatabase();
        String query = "SELECT 1 FROM " + TABLE_WISHLIST + " WHERE " + FIELD_WISHLIST_SPECS_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(phoneId)});
        boolean exists = cursor.getCount() > 0;
        cursor.close();

        Log.d("DBHelper:IsPhoneInWishlist", "isPhoneInWishlist: "+exists+" rows: "+cursor.getCount()+" phoneId: "+phoneId);
        Log.d("DBHelper:IsPhoneInWishlist", "query: "+ query);

        return exists;
    }

    List<PhoneModel> extractPhoneData(@NonNull Cursor cursor) {
        List<PhoneModel> phoneList = new ArrayList<PhoneModel>();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_SPECS_ID));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_SPECS_IMAGE));
                String brand = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_SPECS_BRAND));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_SPECS_NAME));
                String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_SPECS_RELEASE_DATE));
                double weight = cursor.getDouble(cursor.getColumnIndexOrThrow(FIELD_SPECS_WEIGHT));
                String os = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_SPECS_OS));
                int storage = cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_SPECS_STORAGE));
                double screenSize = cursor.getDouble(cursor.getColumnIndexOrThrow(FIELD_SPECS_SCREEN_SIZE));
                String screenResolution = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_SPECS_SCREEN_RESOLUTION));
                double ram = cursor.getDouble(cursor.getColumnIndexOrThrow(FIELD_SPECS_RAM));
                int battery = cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_SPECS_BATTERY));
                double camera = cursor.getDouble(cursor.getColumnIndexOrThrow(FIELD_SPECS_CAMERA));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(FIELD_SPECS_PRICE));

                PhoneModel phone = new PhoneModel(id, image, brand, name, releaseDate, weight, os, storage, screenSize, screenResolution, ram, battery, camera, price);
                phoneList.add(phone);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return phoneList;
    }

}
