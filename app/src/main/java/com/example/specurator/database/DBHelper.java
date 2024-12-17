package com.example.specurator.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

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


}