package com.dreampixel.luxevistaresort;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

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
    private static final String COLUMN_SERVICE_IMAGE = "image";
    // Service Category Table
    private static final String TABLE_SERVICE_CATEGORIES = "service_categories";
    private static final String COLUMN_CATEGORY_ID = "category_id";
    private static final String COLUMN_CATEGORY_NAME = "category_name";
    private static final String COLUMN_CATEGORY_Desc = "category_description";
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
                + COLUMN_CATEGORY_NAME + " TEXT UNIQUE,"
                + COLUMN_CATEGORY_Desc + " TEXT)";
        db.execSQL(CREATE_SERVICE_CATEGORIES_TABLE);

        // Create Services Table
        String CREATE_SERVICES_TABLE = "CREATE TABLE " + TABLE_SERVICES + " (" +
                COLUMN_SERVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SERVICE_NAME + " TEXT NOT NULL, " +
                COLUMN_SERVICE_DESC + " TEXT, " +
                COLUMN_SERVICE_PRICE + " REAL, " +
                COLUMN_SERVICE_AVAILABILITY + " INTEGER DEFAULT 1, " +
                COLUMN_SERVICE_CATEGORY_ID + " INTEGER, " +
                COLUMN_SERVICE_IMAGE + " BLOB, " +
                "FOREIGN KEY(" + COLUMN_SERVICE_CATEGORY_ID + ") REFERENCES service_category(category_id))";
        db.execSQL(CREATE_SERVICES_TABLE);

        // Create Rooms Table
        String CREATE_ROOMS_TABLE = "CREATE TABLE " + TABLE_ROOMS + "("
                + COLUMN_ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ROOM_TYPE + " TEXT, "
                + COLUMN_ROOM_DESCRIPTION + " TEXT, "
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

        insertRooms(db);
        insertCategories(db);
        insertServices(db);
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
    private void insertRooms(SQLiteDatabase db) {

        setRoom(db, "Oceanfront Suite",
                "Luxurious room with breathtaking ocean views",
                5,
                12,
                R.drawable.slider_image_1);

        setRoom(db, "City View Room",
                "Modern room with stunning city skyline views",
                4,
                10,
                R.drawable.slider_image_2);

        setRoom(db, "Garden View Room",
                "Peaceful room with a beautiful garden view",
                3,
                18,
                R.drawable.slider_image_3);

        setRoom(db, "Penthouse Suite",
                "Exclusive top-floor suite with panoramic views",
                6,
                5,
                R.drawable.slider_image_1);

        setRoom(db, "Mountain View Room",
                "Cozy room with a stunning mountain view",
                4,
                20,
                R.drawable.slider_image_1);

        setRoom(db, "Royal Suite",
                "Elegant and spacious suite with a private balcony",
                5,
                8,
                R.drawable.slider_image_1);

        setRoom(db, "Executive Deluxe Room",
                "Luxury room with top-tier amenities and views",
                4,
                10,
                R.drawable.slider_image_1);

        setRoom(db, "Poolside Room",
                "Relaxing room with a view of the pool area",
                3,
                25,
                R.drawable.slider_image_1);

        setRoom(db, "Family Suite",
                "Large suite perfect for families, with separate living areas",
                6,
                6,
                R.drawable.slider_image_1);

        setRoom(db, "Budget Room",
                "Affordable room with all the essentials for a comfortable stay",
                2,
                30,
                R.drawable.slider_image_1);

    }
    private void setRoom(SQLiteDatabase db, String type, String description, double pricePerNight,
                         int roomCount, int imageResourceId) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_ROOM_TYPE, type);
        values.put(COLUMN_ROOM_DESCRIPTION, description);
        values.put(COLUMN_ROOM_PRICE_PER_NIGHT, pricePerNight);
        values.put(COLUMN_ROOM_COUNT, roomCount);

        byte[] imageBytes = getImageBytesFromDrawable(imageResourceId);
        if (imageBytes != null) {
            values.put(COLUMN_ROOM_IMAGE, imageBytes);
        }

        db.insert(TABLE_ROOMS, null, values);
    }
    private void insertCategories(SQLiteDatabase db) {
        setCategory(db, "Spa", "Relaxing spa treatments");
        setCategory(db, "Fine Dining", "Luxury gourmet experiences");
        setCategory(db, "Poolside Cabanas", "Exclusive poolside retreats");
        setCategory(db, "Beach Tours", "Guided scenic tours");
        setCategory(db, "Water Sports", "Thrilling water adventures");
    }
    private void setCategory(SQLiteDatabase db, String name, String description) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_NAME, name);
        values.put(COLUMN_CATEGORY_Desc, description);

        // Use insertWithOnConflict to avoid duplicate errors
        db.insertWithOnConflict(TABLE_SERVICE_CATEGORIES, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }
    private void insertServices(SQLiteDatabase db) {
        int spaCategoryId = getCategoryId(db, "Spa");
        int diningCategoryId = getCategoryId(db, "Fine Dining");
        int poolCategoryId = getCategoryId(db, "Poolside Cabanas");

        setService(db, spaCategoryId, "Full Body Massage", "60-minute relaxation massage", 100.0, R.drawable.slider_image_1);
        setService(db, spaCategoryId, "Hot Stone Therapy", "Luxury heated stone therapy", 120.0, R.drawable.slider_image_2);

        setService(db, diningCategoryId, "Romantic Dinner", "Private beachside dining", 200.0, R.drawable.slider_image_2);
        setService(db, diningCategoryId, "Wine Tasting", "Exclusive wine tasting session", 150.0, R.drawable.slider_image_2);

        setService(db, poolCategoryId, "Cabana Rental", "Private poolside cabana for the day", 80.0, R.drawable.slider_image_1);
    }
    private void setService(SQLiteDatabase db, int categoryId, String name, String description, double price, int imageResourceId) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SERVICE_CATEGORY_ID, categoryId);
        values.put(COLUMN_SERVICE_NAME, name);
        values.put(COLUMN_SERVICE_DESC, description);
        values.put(COLUMN_SERVICE_PRICE, price);
        values.put(COLUMN_SERVICE_AVAILABILITY, 1);

        byte[] imageBytes = getImageBytesFromDrawable(imageResourceId);
        if (imageBytes != null) {
            values.put(COLUMN_SERVICE_IMAGE, imageBytes);
        }

        db.insert(TABLE_SERVICES, null, values);
    }
    private int getCategoryId(SQLiteDatabase db, String categoryName) {
        int categoryId = -1;

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_CATEGORY_ID + " FROM " + TABLE_SERVICE_CATEGORIES + " WHERE " + COLUMN_CATEGORY_NAME + "=?",
                new String[]{categoryName});

        if (cursor != null) {
            if (cursor.moveToFirst())
                categoryId = cursor.getInt(0);
        }
        return categoryId;

    }
    private byte[] getImageBytesFromDrawable(int drawableId) {
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
            if (bitmap != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream); // 75% quality for optimal balance
                return stream.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Room> getLatestRooms(int query) {
        List<Room> roomList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            if(query==0)
                cursor = db.rawQuery("SELECT * FROM " + TABLE_ROOMS, null);
            else if(query==1)
                cursor = db.rawQuery("SELECT * FROM " + TABLE_ROOMS + " ORDER BY " + COLUMN_ROOM_ID + " DESC LIMIT 3", null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int roomId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ROOM_ID));
                    String roomType = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROOM_TYPE));
                    String roomDescription = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROOM_DESCRIPTION));
                    double pricePerNight = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ROOM_PRICE_PER_NIGHT));
                    byte[] roomImage = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_ROOM_IMAGE));

                    Room room = new Room(roomId, roomType, roomDescription, pricePerNight, roomImage);
                    roomList.add(room);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            if (db != null && db.isOpen()) db.close();
        }
        return roomList;
    }
}
