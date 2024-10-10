package com.rnjn.cidgenerate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
    // Insert Data
    private DatabaseHelper dbHelper;

    public DatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Insert Data
    public long insertData(String cid_no, String cid_permission_code) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cid_no", cid_no);
        contentValues.put("cid_permission_code", cid_permission_code);

        long id = db.insert("MyTable", null, contentValues); // Insert row into table
        db.close();
        return id; // Return newly inserted row id
    }

    // Update Data
    public int updateData(int id, String cid_no, String total_cid) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cid_no", cid_no);
        contentValues.put("total_cid", total_cid);
        //contentValues.put("cid_permission_code", cid_permission_code);

        // Updating row where id = ?
        int rowsAffected = db.update("MyTable", contentValues, "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected; // Number of rows updated
    }
    public int updateCIDPermissionData(int id, String cid_permission_code) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("cid_permission_code", cid_permission_code);

        // Updating row where id = ?
        int rowsAffected = db.update("MyTable", contentValues, "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected; // Number of rows updated
    }
    // Retrieve Data
    public Cursor getData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Query to select all data
        Cursor cursor = db.rawQuery("SELECT * FROM MyTable WHERE id = ?", new String[]{"1"});
        return cursor; // Return Cursor object
    }
}
