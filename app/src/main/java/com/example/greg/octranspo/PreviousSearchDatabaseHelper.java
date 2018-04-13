package com.example.greg.octranspo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jason on 21/03/18.
 */

public class PreviousSearchDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "OCTranspoSchedule";
    public static final int VERSION_NUM = 1;

    public static final String TABLE_NAME = "PreviousSearches";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";

    public PreviousSearchDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        this.onCreate(db);
    }

    public void onOpen(SQLiteDatabase db) {

    }

}
