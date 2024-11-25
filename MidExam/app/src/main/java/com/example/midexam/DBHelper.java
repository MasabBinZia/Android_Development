package com.example.midexam;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "StreetLightDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ADMIN = "Admin";
    private static final String TABLE_FIELD_OPERATORS = "FieldOperators";
    private static final String TABLE_STREET_LIGHTS = "StreetLights";

    private static final String COLUMN_ID = "id";

    private static final String COLUMN_ADMIN_USERNAME = "username";
    private static final String COLUMN_ADMIN_PASSWORD = "password";

    private static final String COLUMN_OPERATOR_USERNAME = "username";
    private static final String COLUMN_OPERATOR_PASSWORD = "password";

    private static final String COLUMN_LIGHT_LOCATION = "location";
    private static final String COLUMN_LIGHT_STATUS = "status";
    private static final String COLUMN_OPERATOR_ASSIGNED = "operatorId";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ADMIN_TABLE = "CREATE TABLE " + TABLE_ADMIN + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ADMIN_USERNAME + " TEXT UNIQUE, "
                + COLUMN_ADMIN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_ADMIN_TABLE);

        String CREATE_FIELD_OPERATORS_TABLE = "CREATE TABLE " + TABLE_FIELD_OPERATORS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_OPERATOR_USERNAME + " TEXT UNIQUE, "
                + COLUMN_OPERATOR_PASSWORD + " TEXT)";
        db.execSQL(CREATE_FIELD_OPERATORS_TABLE);

        String CREATE_STREET_LIGHTS_TABLE = "CREATE TABLE " + TABLE_STREET_LIGHTS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_LIGHT_LOCATION + " TEXT, "
                + COLUMN_LIGHT_STATUS + " TEXT, "
                + COLUMN_OPERATOR_ASSIGNED + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_OPERATOR_ASSIGNED + ") REFERENCES " + TABLE_FIELD_OPERATORS + "(" + COLUMN_ID + "))";
        db.execSQL(CREATE_STREET_LIGHTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FIELD_OPERATORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STREET_LIGHTS);
        onCreate(db);
    }

    public boolean addAdmin(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADMIN_USERNAME, username);
        values.put(COLUMN_ADMIN_PASSWORD, password);

        long result = db.insert(TABLE_ADMIN, null, values);
        db.close();
        return result != -1;
    }

    public boolean validateAdmin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ADMIN + " WHERE " + COLUMN_ADMIN_USERNAME + "=? AND " + COLUMN_ADMIN_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean isValid = cursor.moveToFirst();
        cursor.close();
        db.close();
        return isValid;
    }

    public boolean addFieldOperator(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_OPERATOR_USERNAME, username);
        values.put(COLUMN_OPERATOR_PASSWORD, password);

        long result = db.insert(TABLE_FIELD_OPERATORS, null, values);
        db.close();
        return result != -1;
    }

    public boolean validateFieldOperator(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_FIELD_OPERATORS + " WHERE " + COLUMN_OPERATOR_USERNAME + "=? AND " + COLUMN_OPERATOR_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean isValid = cursor.moveToFirst();
        cursor.close();
        db.close();
        return isValid;
    }

    public boolean addStreetLight(String location, String status, int operatorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LIGHT_LOCATION, location);
        values.put(COLUMN_LIGHT_STATUS, status);
        values.put(COLUMN_OPERATOR_ASSIGNED, operatorId);

        long result = db.insert(TABLE_STREET_LIGHTS, null, values);
        db.close();
        return result != -1;
    }

    public boolean updateStreetLight(int id, String newLocation, int operatorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LIGHT_LOCATION, newLocation);
        values.put(COLUMN_OPERATOR_ASSIGNED, operatorId);

        int result = db.update(TABLE_STREET_LIGHTS, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    public boolean deleteStreetLight(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_STREET_LIGHTS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    public List<String> getAllStreetLights() {
        List<String> streetLights = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STREET_LIGHTS, null);

        if (cursor.moveToFirst()) {
            do {
                String location = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LIGHT_LOCATION));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LIGHT_STATUS));
                streetLights.add(location + " - " + status);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return streetLights;
    }

    public boolean toggleStreetLightStatus(int lightId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LIGHT_STATUS, newStatus);

        int result = db.update(TABLE_STREET_LIGHTS, values, COLUMN_ID + "=?", new String[]{String.valueOf(lightId)});
        db.close();
        return result > 0;
    }


    public List<String> getAllFieldOperators() {
        List<String> operators = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM FieldOperators", null);

        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));            operators.add(username);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return operators;
    }


    public List<String> getAssignedStreetLights(String operatorUsername) {
        List<String> assignedStreetLights = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String operatorQuery = "SELECT " + COLUMN_ID + " FROM " + TABLE_FIELD_OPERATORS
                + " WHERE " + COLUMN_OPERATOR_USERNAME + "=?";
        Cursor cursor = db.rawQuery(operatorQuery, new String[]{operatorUsername});

        if (cursor.moveToFirst()) {
            int operatorId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String streetLightQuery = "SELECT * FROM " + TABLE_STREET_LIGHTS
                    + " WHERE " + COLUMN_OPERATOR_ASSIGNED + "=?";
            Cursor streetLightCursor = db.rawQuery(streetLightQuery, new String[]{String.valueOf(operatorId)});

            if (streetLightCursor.moveToFirst()) {
                do {
                    String location = streetLightCursor.getString(streetLightCursor.getColumnIndexOrThrow(COLUMN_LIGHT_LOCATION));
                    String status = streetLightCursor.getString(streetLightCursor.getColumnIndexOrThrow(COLUMN_LIGHT_STATUS));
                    assignedStreetLights.add(location + " - " + status);
                } while (streetLightCursor.moveToNext());
            }

            streetLightCursor.close();
        }

        cursor.close();
        db.close();

        return assignedStreetLights;
    }


}


