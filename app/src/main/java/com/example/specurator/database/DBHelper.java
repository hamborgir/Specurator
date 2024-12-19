package com.example.specurator.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    public static final String TABLE_NAME = "phone_specs";

    // public db info
    public static final String FIELD_ID = "id";
    public static final String FIELD_IMAGE = "image";
    public static final String FIELD_BRAND = "brand";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_RELEASE_DATE = "release_date";
    public static final String FIELD_WEIGHT = "weight";
    public static final String FIELD_OS = "os";
    public static final String FIELD_STORAGE = "storage";
    public static final String FIELD_SCREEN_SIZE = "screen_size";
    public static final String FIELD_SCREEN_RESOLUTION = "screen_resolution";
    public static final String FIELD_RAM = "ram";
    public static final String FIELD_BATTERY = "battery";
    public static final String FIELD_CAMERA = "camera";
    public static final String FIELD_PRICE = "price";

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

    public SQLiteDatabase openDatabase() throws SQLException {
        if (database == null || !database.isOpen()) {
            database = SQLiteDatabase.openDatabase(context.getDatabasePath(DB_NAME).getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
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
            selection = FIELD_BRAND + " = ?";
            selectionArgs = new String[]{brand};
        }

        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_ID));
                String phoneBrand = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_BRAND));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_NAME));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_IMAGE));
                String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_RELEASE_DATE));
                double weight = cursor.getDouble(cursor.getColumnIndexOrThrow(FIELD_WEIGHT));
                String os = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_OS));
                int storage = cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_STORAGE));
                double screenSize = cursor.getDouble(cursor.getColumnIndexOrThrow(FIELD_SCREEN_SIZE));
                String screenResolution = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_SCREEN_RESOLUTION));
                double ram = cursor.getDouble(cursor.getColumnIndexOrThrow(FIELD_RAM));
                int battery = cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_BATTERY));
                double camera = cursor.getDouble(cursor.getColumnIndexOrThrow(FIELD_CAMERA));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(FIELD_PRICE));

                PhoneModel phone = new PhoneModel(id,image, phoneBrand, name,  releaseDate, weight, os, storage, screenSize, screenResolution, ram, battery, camera, price);
                phoneList.add(phone);
            } while (cursor.moveToNext());
        }



        cursor.close();
        db.close();
        return phoneList;
    }

    public List<PhoneModel> getPhonesByName(String searchString) {
        List<PhoneModel> phoneList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = FIELD_NAME + " LIKE ?";
        String[] selectionArgs = new String[]{"%" + searchString + "%"}; // Add wildcards

        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_ID));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_IMAGE));
                String brand = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_BRAND));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_NAME));
                String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_RELEASE_DATE));
                double weight = cursor.getDouble(cursor.getColumnIndexOrThrow(FIELD_WEIGHT));
                String os = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_OS));
                int storage = cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_STORAGE));
                double screenSize = cursor.getDouble(cursor.getColumnIndexOrThrow(FIELD_SCREEN_SIZE));
                String screenResolution = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_SCREEN_RESOLUTION));
                double ram = cursor.getDouble(cursor.getColumnIndexOrThrow(FIELD_RAM));
                int battery = cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_BATTERY));
                double camera = cursor.getDouble(cursor.getColumnIndexOrThrow(FIELD_CAMERA));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(FIELD_PRICE));

                PhoneModel phone = new PhoneModel(id, image, brand, name, releaseDate, weight, os, storage, screenSize, screenResolution, ram, battery, camera, price);
                phoneList.add(phone);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return phoneList;
    }


}
