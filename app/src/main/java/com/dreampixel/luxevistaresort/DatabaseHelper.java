package com.dreampixel.luxevistaresort;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LuxeVistaResort.db";
    private static final int DATABASE_VERSION = 1;

    // Users Table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "user_id";
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
    private static final String COLUMN_SERVICE_NAME = "service_name";
    private static final String COLUMN_SERVICE_DESC = "description";
    private static final String COLUMN_SERVICE_PRICE = "price";
    private static final String COLUMN_SERVICE_AVAILABILITY = "availability";
    private static final String COLUMN_SERVICE_IMAGE = "service_image";

    // Service Category Table
    private static final String TABLE_SERVICE_CATEGORIES = "service_categories";
    private static final String COLUMN_CATEGORY_ID = "category_id";
    private static final String COLUMN_CATEGORY_NAME = "category_name";
    private static final String COLUMN_CATEGORY_Desc = "category_description";

    // Rooms Table
    private static final String TABLE_ROOMS = "rooms";
    private static final String COLUMN_ROOM_ID = "room_id";
    private static final String COLUMN_ROOM_TYPE = "room_type";
    private static final String COLUMN_ROOM_DESCRIPTION = "description";
    private static final String COLUMN_ROOM_PRICE_PER_NIGHT = "price_per_night";
    private static final String COLUMN_ROOM_COUNT = "room_count";
    private static final String COLUMN_ROOM_IMAGE = "room_image";

    // Booking Table
    private static final String TABLE_BOOKINGS = "bookings";
    private static final String COLUMN_BOOKING_ID = "booking_id";
    private static final String COLUMN_CHECKIN_DATE = "checkin_date";
    private static final String COLUMN_CHECKOUT_DATE = "checkout_date";
    private static final String COLUMN_BOOKING_STATUS = "status";
    private static final String COLUMN_B_CURRENT_DATE = "currentDate_b";
    private static final String COLUMN_TOTAL_PRICE = "total_price";

    //Service Reservation Table
    public static final String TABLE_SERVICE_RESERVATION = "serviceReservation";
    public static final String COLUMN_RESERVATION_ID = "reservationID";
    public static final String COLUMN_R_CURRENT_DATE = "currentDate_r";
    public static final String COLUMN_RESERVATION_DATETIME = "reservationDateTime";

    //Foreign Keys

    private static final String COLUMN_USER_ID_FK = "user_id";
    public static final String COLUMN_SERVICE_ID_FK = "service_id";
    private static final String COLUMN_ROOM_ID_FK = "room_id";
    private static final String COLUMN_SERVICE_CATEGORY_ID_FK = "category_id";

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users Table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " ("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
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
                COLUMN_SERVICE_CATEGORY_ID_FK + " INTEGER, " +
                COLUMN_SERVICE_IMAGE + " BLOB, " +
                "FOREIGN KEY(" + COLUMN_SERVICE_CATEGORY_ID_FK + ") REFERENCES "+TABLE_SERVICE_CATEGORIES+"("+COLUMN_CATEGORY_ID+"))";
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
        String CREATE_BOOKINGS_TABLE = "CREATE TABLE " + TABLE_BOOKINGS + " (" +
                COLUMN_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID_FK + " INTEGER, " +
                COLUMN_ROOM_ID_FK + " INTEGER, " +
                COLUMN_B_CURRENT_DATE + " TEXT NOT NULL, " +
                COLUMN_CHECKIN_DATE + " TEXT, " +
                COLUMN_CHECKOUT_DATE + " TEXT, " +
                COLUMN_BOOKING_STATUS + " TEXT, " +
                COLUMN_TOTAL_PRICE + " REAL, " +
                "FOREIGN KEY(" + COLUMN_USER_ID_FK + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "), " +
                "FOREIGN KEY(" + COLUMN_ROOM_ID_FK + ") REFERENCES " + TABLE_ROOMS + "(" + COLUMN_ROOM_ID + "))";
        db.execSQL(CREATE_BOOKINGS_TABLE);


        //Create Service Reservation Table
        String CREATE_SERVICE_RESERVATION_TABLE = "CREATE TABLE " + TABLE_SERVICE_RESERVATION + " (" +
                COLUMN_RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SERVICE_ID_FK + " INTEGER, " +
                COLUMN_USER_ID_FK + " INTEGER, " +
                COLUMN_R_CURRENT_DATE + " TEXT NOT NULL, " +
                COLUMN_RESERVATION_DATETIME + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + COLUMN_SERVICE_ID_FK + ") REFERENCES " + TABLE_SERVICES + "(" + COLUMN_SERVICE_ID + "), " +
                "FOREIGN KEY(" + COLUMN_USER_ID_FK + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "))";
        db.execSQL(CREATE_SERVICE_RESERVATION_TABLE);


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
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_USER_ID}, COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);

        boolean isAuthenticated = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return isAuthenticated;
    }
    private void insertRooms(SQLiteDatabase db) {

        setRoom(db, "Deluxe Ocean View",
                "Spacious room with private balcony, traditional Sri Lankan décor, and stunning Indian Ocean views. Includes ceiling fan and A/C",
                9800,
                15,
                R.drawable.deluxe_1);

        setRoom(db, "Deluxe Ocean View",
                "Corner unit with expanded balcony, perfect for watching southern coast sunsets. Features local handicraft decorations",
                9800,
                12,
                R.drawable.deluxe_2);

        setRoom(db, "Superior Beach Front",
                "Direct beach access, outdoor shower, hammock, and private garden. Traditional Sri Lankan architecture with modern amenities",
                12500,
                8,
                R.drawable.superior_1);

        setRoom(db, "Superior Beach Front",
                "Ground floor unit with veranda, beach views, and tropical garden. Includes outdoor seating area perfect for morning tea",
                12500,
                10,
                R.drawable.superior_2);

        setRoom(db, "Family Suite",
                "Two-bedroom suite with living area, perfect for families. Features kitchenette and traditional Sri Lankan sitting area",
                15800,
                6,
                R.drawable.family_1);

        setRoom(db, "Family Suite",
                "Spacious suite with connecting rooms, outdoor dining area, and ocean views. Includes daybed and children's play area",
                15800,
                4,
                R.drawable.family_2);

        setRoom(db, "Luxury Villa",
                "Private villa with plunge pool, outdoor bathroom, and direct beach access. Features antique colonial furniture and artwork",
                22000,
                3,
                R.drawable.villa_1);

        setRoom(db, "Luxury Villa",
                "Beachfront villa with private garden, outdoor dining pavilion, and butler service. Includes traditional Sri Lankan décor",
                22000,
                2,
                R.drawable.villa_2);

        setRoom(db, "Deluxe Ocean View",
                "Upper floor room with panoramic ocean views, rain shower, and traditional carved wooden furniture",
                9800,
                10,
                R.drawable.deluxe_3);

        setRoom(db, "Superior Beach Front",
                "Beachside room with private terrace, outdoor dining area, and unobstructed ocean views. Features local artwork",
                12500,
                7,
                R.drawable.superior_3);

    }
    private void setRoom(SQLiteDatabase db, String type, String description, double pricePerNight,int roomCount, int imageResourceId) {
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
        setCategory(db, "adventure", "Guided scenic tours");
    }
    private void setCategory(SQLiteDatabase db, String name, String description) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_NAME, name);
        values.put(COLUMN_CATEGORY_Desc, description);
        db.insertWithOnConflict(TABLE_SERVICE_CATEGORIES, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }
    private void insertServices(SQLiteDatabase db) {
        int spaCategoryId = getCategoryId(db, "Spa");
        int diningCategoryId = getCategoryId(db, "Fine Dining");
        int poolCategoryId = getCategoryId(db, "Poolside Cabanas");
        int adventureCategoryId=getCategoryId(db, "adventure");

        setService(db, spaCategoryId, "Full Body Massage", "60-minute relaxation massage with essential oils", 10000.0, 1, R.drawable.spa_massage);
        setService(db, spaCategoryId, "Ayurvedic Treatment", "Traditional Sri Lankan herbal therapy", 15000.0, 1, R.drawable.spa_ayurveda);

        setService(db, diningCategoryId, "Romantic Dinner", "Private beachside dining with a gourmet 5-course meal", 20000.0, 0, R.drawable.dining_romantic);
        setService(db, diningCategoryId, "Sri Lankan Seafood Platter", "Fresh seafood caught daily, prepared with authentic spices", 18000.0, 1, R.drawable.dining_seafood);
        setService(db, diningCategoryId, "Sunset High Tea", "Tea experience with snacks while enjoying ocean views", 8000.0, 1, R.drawable.dining_high_tea);

        setService(db, poolCategoryId, "Cabana Rental", "Private poolside cabana for the day with refreshments", 8000.0, 0, R.drawable.pool_side_cabana_img);

        setService(db, adventureCategoryId, "Scuba Diving", "Guided dive with professional instructors", 30000.0, 1, R.drawable.adventure_diving);

    }
    private void setService(SQLiteDatabase db, int categoryId, String name, String description, double price,int availability, int imageResourceId) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SERVICE_CATEGORY_ID_FK, categoryId);
        values.put(COLUMN_SERVICE_NAME, name);
        values.put(COLUMN_SERVICE_DESC, description);
        values.put(COLUMN_SERVICE_PRICE, price);
        values.put(COLUMN_SERVICE_AVAILABILITY, availability);

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
    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_USER_ID, COLUMN_FULL_NAME, COLUMN_EMAIL, COLUMN_PROFILE_IMAGE},
                COLUMN_EMAIL + "=?", new String[]{email}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
            String fullName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FULL_NAME));
            String userEmail = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));
            byte[] profileImage = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_IMAGE));

            user = new User(userId, fullName, userEmail, null, null, null, null,profileImage); // Assuming User class has user_id parameter
            cursor.close();
        }

        db.close();
        return user;
    }
    public boolean updateUserDetails(String email, String fullName, String telephone, String password, byte[] profileImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FULL_NAME, fullName);
        values.put(COLUMN_CONTACT, telephone);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_PROFILE_IMAGE, profileImage);

        int rowsAffected = db.update(TABLE_USERS, values, COLUMN_EMAIL + " = ?", new String[]{email});
        db.close();

        return rowsAffected > 0;
    }
    public Room getRoomDetails(int roomId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Rooms", null, "room_id = ?", new String[]{String.valueOf(roomId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String roomType = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROOM_TYPE));
            String roomDesc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROOM_DESCRIPTION));
            double roomPricePerNight = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ROOM_PRICE_PER_NIGHT));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_ROOM_IMAGE));
            cursor.close();
            return new Room(roomId,roomType,roomDesc, roomPricePerNight, image);
        }
        return null;
    }
    public boolean insertRoomBooking(int userID, int roomID, String checkInDate, String checkOutDate, String status, double totalPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_ID_FK, userID);
        values.put(COLUMN_ROOM_ID_FK, roomID);
        values.put(COLUMN_CHECKIN_DATE, checkInDate);
        values.put(COLUMN_CHECKOUT_DATE, checkOutDate);
        values.put(COLUMN_BOOKING_STATUS, status);
        values.put(COLUMN_B_CURRENT_DATE, new Date().toString());
        values.put(COLUMN_TOTAL_PRICE, totalPrice);

        long result = db.insert(TABLE_BOOKINGS, null, values);
        db.close();

        return result != -1;
    }

    public List<Service> getAllServices() {
        List<Service> serviceList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SERVICES, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_DESC));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_PRICE));
                int availability = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_AVAILABILITY));
                int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_CATEGORY_ID_FK));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_IMAGE));

                Service service = new Service(id, name, description, price, availability, categoryId, image);
                serviceList.add(service);

            } while (cursor.moveToNext());
            cursor.close();
        }
        return serviceList;
    }

    public boolean reserveService(int serviceId, int userId, String reservationDateTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SERVICE_ID_FK, serviceId);
        values.put(COLUMN_USER_ID_FK, userId);
        values.put(COLUMN_R_CURRENT_DATE, new Date().toString());
        values.put(COLUMN_RESERVATION_DATETIME, reservationDateTime);

        long result = db.insert(TABLE_SERVICE_RESERVATION, null, values);
        return result != -1;
    }

    public List<BookingHistoryItem> getUserBookingHistory(int userId) {
        List<BookingHistoryItem> historyItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String queryRooms = "SELECT b." + COLUMN_BOOKING_ID + ", 'Room' AS type, b." + COLUMN_B_CURRENT_DATE +
                ", r." + COLUMN_ROOM_TYPE + ", b." + COLUMN_CHECKIN_DATE + ", b." + COLUMN_CHECKOUT_DATE +
                ", b." + COLUMN_TOTAL_PRICE + " FROM " + TABLE_BOOKINGS + " b " +
                " JOIN " + TABLE_ROOMS + " r ON b." + COLUMN_ROOM_ID_FK + " = r." + COLUMN_ROOM_ID +
                " WHERE b." + COLUMN_USER_ID_FK + " = ? ORDER BY b." + COLUMN_B_CURRENT_DATE + " DESC";

        Cursor cursor = db.rawQuery(queryRooms, new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            do {
                String currentDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_B_CURRENT_DATE));
                String roomType = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROOM_TYPE));
                String checkinDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CHECKIN_DATE));
                String checkoutDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CHECKOUT_DATE));
                double totalPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_PRICE));

                historyItems.add(new BookingHistoryItem("Room", currentDate, roomType, checkinDate, checkoutDate, totalPrice));
            } while (cursor.moveToNext());
        }
        cursor.close();

        String queryServices = "SELECT sr." + COLUMN_RESERVATION_ID + ", 'Service' AS type, sr." + COLUMN_R_CURRENT_DATE +
                ", s." + COLUMN_SERVICE_NAME + ", sr." + COLUMN_RESERVATION_DATETIME + ", s." + COLUMN_SERVICE_PRICE +
                " FROM " + TABLE_SERVICE_RESERVATION + " sr " +
                " JOIN " + TABLE_SERVICES + " s ON sr." + COLUMN_SERVICE_ID_FK + " = s." + COLUMN_SERVICE_ID +
                " WHERE sr." + COLUMN_USER_ID_FK + " = ? ORDER BY sr." + COLUMN_RESERVATION_DATETIME + " DESC";

        cursor = db.rawQuery(queryServices, new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            do {
                String currentDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_R_CURRENT_DATE));
                String serviceName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_NAME));
                String reservationDateTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESERVATION_DATETIME));
                double servicePrice = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_PRICE));

                historyItems.add(new BookingHistoryItem("Service", currentDate, serviceName, reservationDateTime, null, servicePrice));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return historyItems;
    }

    public ServiceReservation getLatestServiceReservation(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ServiceReservation latestReservation = null;

        String query = "SELECT * FROM " + TABLE_SERVICE_RESERVATION +
                " WHERE " + COLUMN_USER_ID_FK + " = ? " +
                " ORDER BY " + COLUMN_RESERVATION_DATETIME + " DESC LIMIT 1";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            latestReservation = new ServiceReservation(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RESERVATION_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_ID_FK)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID_FK)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_R_CURRENT_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESERVATION_DATETIME))
            );
        }

        cursor.close();
        db.close();
        return latestReservation;
    }


}
