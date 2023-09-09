package com.example.context_monitoring_application;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "contextMonitoring.db";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_TABLE = "healthDetails";

    static final String ID = "unique_id";
    static final String HEART_RATE = "heart_rate";
    static final String RESP_RATE = "resp_rate";
    static final String COUGH = "cough";
    static final String DIARRHEA = "diarrhea";
    static final String FEELING_TIRED = "feeling_tired";
    static final String FEVER = "fever";
    static final String HEADACHE = "headache";
    static final String LOSS_OF_SMELL_OR_TASTE = "loss_of_smell_or_taste";
    static final String MUSCLE_ACHE = "muscle_ache";
    static final String NAUSEA = "nausea";
    static final String SHORTNESS_OF_BREATH = "shortness_of_breath";
    static final String SORE_THROAT = "sore_throat";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_DB_QUERY = "CREATE TABLE " + DATABASE_TABLE + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + HEART_RATE + " REAL, " + RESP_RATE + " REAL, " + COUGH + " REAL, " + DIARRHEA + " REAL, " + FEELING_TIRED + " REAL, " + FEVER + " REAL, " + HEADACHE + " REAL, " + LOSS_OF_SMELL_OR_TASTE + " REAL, " + MUSCLE_ACHE + " REAL, " + NAUSEA + " REAL, " + SHORTNESS_OF_BREATH + " REAL, " + SORE_THROAT + " REAL);";
        sqLiteDatabase.execSQL(CREATE_DB_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void insert(float heart_rate, float resp_rate, float cough, float diarrhea, float feeling_tired, float fever, float headache, float loss_of_smell_or_taste, float muscle_ache, float nausea, float shortness_of_breath, float sore_throat) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(HEART_RATE, heart_rate);
            contentValues.put(RESP_RATE, resp_rate);
            contentValues.put(COUGH, cough);
            contentValues.put(DIARRHEA, diarrhea);
            contentValues.put(FEELING_TIRED, feeling_tired);
            contentValues.put(FEVER, fever);
            contentValues.put(HEADACHE, headache);
            contentValues.put(LOSS_OF_SMELL_OR_TASTE, loss_of_smell_or_taste);
            contentValues.put(MUSCLE_ACHE, muscle_ache);
            contentValues.put(NAUSEA, nausea);
            contentValues.put(SHORTNESS_OF_BREATH, shortness_of_breath);
            contentValues.put(SORE_THROAT, sore_throat);
            db.insert(DATABASE_TABLE, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteStatement stmt = db.compileStatement("SELECT last_insert_rowid()");
        return (int) stmt.simpleQueryForLong();
    }

    public void delete(int unique_id) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(DATABASE_TABLE, "unique_id = ?", new String[]{String.valueOf(unique_id)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(int unique_id, float cough, float diarrhea, float feeling_tired, float fever, float headache, float loss_of_smell_or_taste, float muscle_ache, float nausea, float shortness_of_breath, float sore_throat) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COUGH, cough);
            contentValues.put(DIARRHEA, diarrhea);
            contentValues.put(FEELING_TIRED, feeling_tired);
            contentValues.put(FEVER, fever);
            contentValues.put(HEADACHE, headache);
            contentValues.put(LOSS_OF_SMELL_OR_TASTE, loss_of_smell_or_taste);
            contentValues.put(MUSCLE_ACHE, muscle_ache);
            contentValues.put(NAUSEA, nausea);
            contentValues.put(SHORTNESS_OF_BREATH, shortness_of_breath);
            contentValues.put(SORE_THROAT, sore_throat);
            db.update(DATABASE_TABLE, contentValues, "unique_id = ?", new String[]{String.valueOf(unique_id)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}