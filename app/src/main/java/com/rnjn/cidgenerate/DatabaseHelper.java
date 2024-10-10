package com.rnjn.cidgenerate;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Name and Version
    private static final String DATABASE_NAME = "MyDatabase.db";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    private static final String TABLE_NAME = "MyTable";

    // Columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CID_NO = "cid_no";
    private static final String COLUMN_TOTAL_CID = "total_cid";
    private static final String COLUMN_PERMISSION_CODE = "cid_permission_code";

    // Create Table SQL
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_CID_NO + " TEXT, " +
            COLUMN_TOTAL_CID + " TEXT, " +
            COLUMN_PERMISSION_CODE + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE); // Create the table
        // Insert default row data
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CID_NO, "");
        contentValues.put(COLUMN_TOTAL_CID, "");
        contentValues.put(COLUMN_PERMISSION_CODE, 1827); // Example default values
        db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db); // Recreate the table after dropping it
    }
}
