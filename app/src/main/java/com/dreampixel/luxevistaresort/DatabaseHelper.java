package com.dreampixel.luxevistaresort;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UsersDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FULL_NAME = "full_name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_CONTACT = "contact";
    private static final String COLUMN_DOB = "dob";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_PROFILE_IMAGE = "profile_image";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FULL_NAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT UNIQUE,"
                + COLUMN_CONTACT + " TEXT,"
                + COLUMN_DOB + " TEXT,"
                + COLUMN_GENDER + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_PROFILE_IMAGE + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public boolean registerUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_FULL_NAME, user.getFullName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_CONTACT, user.getContact());
        values.put(COLUMN_DOB, user.getDob());
        values.put(COLUMN_GENDER, user.getGender());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_PROFILE_IMAGE, user.getProfileImage());

        long result = db.insert(TABLE_USERS, null, values);
        db.close();

        return result != -1;
    }

    public boolean checkUserExists(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID}, COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);

        boolean isAuthenticated = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return isAuthenticated;
    }
}
