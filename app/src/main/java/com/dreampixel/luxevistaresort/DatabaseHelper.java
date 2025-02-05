package com.dreampixel.luxevistaresort;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LuxeVistaResort.db";
    private static final int DATABASE_VERSION = 1;

    // Users Table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "user_id";
    private static final String COLUMN_FULL_NAME = "full_name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_CONTACT = "contact";
    private static final String COLUMN_DOB = "dob";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_PROFILE_IMAGE = "profile_image";

    // Services Table
    private static final String TABLE_SERVICES = "services";
    private static final String COLUMN_SERVICE_ID = "service_id";
    private static final String COLUMN_SERVICE_NAME = "name";
    private static final String COLUMN_SERVICE_DESC = "description";
    private static final String COLUMN_SERVICE_PRICE = "price";
    private static final String COLUMN_SERVICE_AVAILABILITY = "availability";
    private static final String COLUMN_SERVICE_CATEGORY_ID = "category_id";

    // Service Category Table
    private static final String TABLE_SERVICE_CATEGORIES = "service_categories";
    private static final String COLUMN_CATEGORY_ID = "category_id";
    private static final String COLUMN_CATEGORY_NAME = "category_name";

    // Rooms Table
    private static final String TABLE_ROOMS = "rooms";

    private static final String COLUMN_ROOM_ID = "room_id";
    private static final String COLUMN_ROOM_TYPE = "type";
    private static final String COLUMN_ROOM_DESCRIPTION = "description";
    private static final String COLUMN_ROOM_MAX_OCCUPANCY = "max_occupancy";
    private static final String COLUMN_ROOM_PRICE_PER_NIGHT = "price_per_night";
    private static final String COLUMN_ROOM_COUNT = "room_count";
    private static final String COLUMN_ROOM_IMAGE = "room_image";

    // Booking Table
    private static final String TABLE_BOOKINGS = "bookings";
    private static final String COLUMN_BOOKING_ID = "booking_id";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_ROOM_ID_FK = "room_id";
    private static final String COLUMN_CHECKIN_DATE = "checkin_date";
    private static final String COLUMN_CHECKOUT_DATE = "checkout_date";
    private static final String COLUMN_BOOKING_STATUS = "status";
    private static final String COLUMN_TOTAL_PRICE = "total_price";


    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users Table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FULL_NAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT UNIQUE,"
                + COLUMN_CONTACT + " TEXT,"
                + COLUMN_DOB + " TEXT,"
                + COLUMN_GENDER + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_PROFILE_IMAGE + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);

        // Create Service Categories Table
        String CREATE_SERVICE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_SERVICE_CATEGORIES + " ("
                + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CATEGORY_NAME + " TEXT UNIQUE)";
        db.execSQL(CREATE_SERVICE_CATEGORIES_TABLE);

        // Create Services Table
        String CREATE_SERVICES_TABLE = "CREATE TABLE " + TABLE_SERVICES + " ("
                + COLUMN_SERVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SERVICE_NAME + " TEXT,"
                + COLUMN_SERVICE_DESC + " TEXT,"
                + COLUMN_SERVICE_PRICE + " REAL,"
                + COLUMN_SERVICE_AVAILABILITY + " TEXT,"
                + COLUMN_SERVICE_CATEGORY_ID + " INTEGER,"
                + " FOREIGN KEY(" + COLUMN_SERVICE_CATEGORY_ID + ") REFERENCES " + TABLE_SERVICE_CATEGORIES + "(" + COLUMN_CATEGORY_ID + "))";
        db.execSQL(CREATE_SERVICES_TABLE);

        // Create Rooms Table
        String CREATE_ROOMS_TABLE = "CREATE TABLE " + TABLE_ROOMS + "("
                + COLUMN_ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ROOM_TYPE + " TEXT, "
                + COLUMN_ROOM_DESCRIPTION + " TEXT, "
                + COLUMN_ROOM_MAX_OCCUPANCY + " INTEGER, "
                + COLUMN_ROOM_PRICE_PER_NIGHT + " REAL, "
                + COLUMN_ROOM_COUNT + " INTEGER, "
                + COLUMN_ROOM_IMAGE + " BLOB)";
        db.execSQL(CREATE_ROOMS_TABLE);

        // Create Bookings Table
        String CREATE_BOOKINGS_TABLE = "CREATE TABLE " + TABLE_BOOKINGS + " ("
                + COLUMN_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_ID + " INTEGER,"
                + COLUMN_ROOM_ID_FK + " INTEGER,"
                + COLUMN_CHECKIN_DATE + " TEXT,"
                + COLUMN_CHECKOUT_DATE + " TEXT,"
                + COLUMN_BOOKING_STATUS + " TEXT,"
                + COLUMN_TOTAL_PRICE + " REAL,"
                + " FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + "),"
                + " FOREIGN KEY(" + COLUMN_ROOM_ID_FK + ") REFERENCES " + TABLE_ROOMS + "(" + COLUMN_ROOM_ID + "))";
        db.execSQL(CREATE_BOOKINGS_TABLE);

        // Insert default rooms
        insertDefaultRooms(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
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

    private byte[] getImageBytesFromDrawable(int drawableId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void insertDefaultRooms(SQLiteDatabase db) {  // Accept db instance
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_TYPE, "Deluxe Room");
        values.put(COLUMN_ROOM_DESCRIPTION, "A luxurious deluxe room with sea view.");
        values.put(COLUMN_ROOM_MAX_OCCUPANCY, 2);
        values.put(COLUMN_ROOM_PRICE_PER_NIGHT, 150.0);
        values.put(COLUMN_ROOM_COUNT, 10);
        values.put(COLUMN_ROOM_IMAGE, getImageBytesFromDrawable(R.drawable.slider_image_1));
        db.insert(TABLE_ROOMS, null, values);  // Use the existing db instance
    }


//    public boolean insertRoom(String type, String description, int maxOccupancy, double pricePerNight, int roomCount, int drawableId) {
//
//
//
//        SQLiteDatabase  db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put(COLUMN_ROOM_ID, 0);
//        values.put(COLUMN_ROOM_TYPE, type);
//        values.put(COLUMN_ROOM_DESCRIPTION, description);
//        values.put(COLUMN_ROOM_MAX_OCCUPANCY, maxOccupancy);
//        values.put(COLUMN_ROOM_PRICE_PER_NIGHT, pricePerNight);
//        values.put(COLUMN_ROOM_COUNT, roomCount);
//        values.put(COLUMN_ROOM_IMAGE, getImageBytesFromDrawable(drawableId));
//
//        long result = db.insert(TABLE_ROOMS, null, values);
//        db.close();
//
//        return result != -1;
//    }
//    public void insertDefaultRooms() {
//        insertRoom("Deluxe Suite", "Spacious room with ocean view", 4, 250.0, 10, R.drawable.slider_image_1);
//        insertRoom("Executive Room", "Luxury room with city view", 3, 180.0, 8, R.drawable.slider_image_1);
//        insertRoom("Standard Room", "Cozy room for two guests", 2, 120.0, 15, R.drawable.slider_image_1);
//        insertRoom("Family Suite", "Large suite with two bedrooms", 6, 300.0, 5, R.drawable.slider_image_1);
//        insertRoom("Penthouse", "Premium suite with VIP services", 4, 500.0, 2, R.drawable.slider_image_1);
//    }

}
