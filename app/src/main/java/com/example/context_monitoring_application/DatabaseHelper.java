package com.example.context_monitoring_application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "CONTEXT_MONITORING.DB";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_TABLE = "SIGNS_SYMPTOMS";

    static final String ID = "id";
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

    private static final String CREATE_DB_QUERY = "CREATE TABLE "+DATABASE_TABLE + " ( " + ID + "INTEGER PRIMARY KEY, " + HEART_RATE + " REAL, " + RESP_RATE + " REAL, " + COUGH + " REAL, " + DIARRHEA + " REAL, " + FEELING_TIRED + " REAL, " + FEVER + " REAL, " + HEADACHE + " REAL, " + LOSS_OF_SMELL_OR_TASTE + " REAL, " + MUSCLE_ACHE + " REAL, " + NAUSEA + " REAL, " + SHORTNESS_OF_BREATH + " REAL, " + SORE_THROAT + " REAL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DB_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
    }
}
