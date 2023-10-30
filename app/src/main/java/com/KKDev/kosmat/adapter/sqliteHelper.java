package com.KKDev.kosmat.adapter;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.KKDev.kosmat.R;

import java.io.ByteArrayOutputStream;

public class sqliteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "kosmat_mobile.db";
    private static final int DATABASE_VERSION = 1;

    // Table name and column names
    public static final String TABLE_NAME = "akun";
    public static final String COLUMN_NIK = "nik";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NAMA = "nama";
    public static final String COLUMN_NO_WHATSAPP = "no_whatsapp";
    public static final String COLUMN_PRIVILEGE = "privilege";
    public static final String COLUMN_TGL_LAHIR = "tgl_lahir";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_IMAGE = "image";


    private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_NIK + " CHAR(16) PRIMARY KEY, " +
            COLUMN_USERNAME + " CHAR(16) NOT NULL, " +
            COLUMN_PASSWORD + " CHAR(16) NOT NULL, " +
            COLUMN_NAMA + " VARCHAR(255) NOT NULL, " +
            COLUMN_NO_WHATSAPP + " CHAR(16) NOT NULL, " +
            COLUMN_PRIVILEGE + " TINYINT(1) NOT NULL DEFAULT 0, " +
            COLUMN_TGL_LAHIR + " DATE NOT NULL, " +
            COLUMN_GENDER + " TEXT NOT NULL, " +
            COLUMN_IMAGE + " BLOB);";



    public sqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists akun");
    }

    public boolean register(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nik", user.getNik());
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("nama", user.getNama());
        contentValues.put("no_whatsapp", user.getNoWhatsapp());
        contentValues.put("privilege", user.getPrivilege());
        contentValues.put("tgl_lahir", user.getTglLahir());
        contentValues.put("gender", user.getGender());
        contentValues.put("image", user.getImage());
        long result = sqLiteDatabase.insert("akun", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public User login(String inputUsername, String inputPassword) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM akun WHERE username = ? AND password = ?";
        String[] selectionArgs = {inputUsername, inputPassword};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        User result = null;

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String nik = cursor.getString(cursor.getColumnIndex("nik"));
                @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex("username"));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
                @SuppressLint("Range") String nama = cursor.getString(cursor.getColumnIndex("nama"));
                @SuppressLint("Range") String no_whatsapp = cursor.getString(cursor.getColumnIndex("no_whatsapp"));
                @SuppressLint("Range") String privilege = cursor.getString(cursor.getColumnIndex("privilege"));
                @SuppressLint("Range") String tgl_lahir = cursor.getString(cursor.getColumnIndex("tgl_lahir"));
                @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex("gender"));
                @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
                if ((inputUsername.equals(username) && (inputPassword.equals(password)))) {
                    User user = new User(nik, username, password, nama, no_whatsapp, privilege, tgl_lahir, gender, image);
                    result = user;
                }
            }
            cursor.close(); // Remember to close the cursor when you're done with it
        }

        db.close();
        return result;
    }

    public User[] getAllUser() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM akun ORDER BY nama ASC";
        Cursor cursor = db.rawQuery(query, null);
        User[] result = null;
        if (cursor != null) {
            int count = cursor.getCount();
            result = new User[count]; // Initialize the array with the count of users
            int i = 0;
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String nik = cursor.getString(cursor.getColumnIndex("nik"));
                @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex("username"));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
                @SuppressLint("Range") String nama = cursor.getString(cursor.getColumnIndex("nama"));
                @SuppressLint("Range") String no_whatsapp = cursor.getString(cursor.getColumnIndex("no_whatsapp"));
                @SuppressLint("Range") String privilege = cursor.getString(cursor.getColumnIndex("privilege"));
                @SuppressLint("Range") String tgl_lahir = cursor.getString(cursor.getColumnIndex("tgl_lahir"));
                @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex("gender"));
                @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));

                User user = new User(nik, username, password, nama, no_whatsapp, privilege, tgl_lahir, gender,image);
                result[i] = user;
                i++;
            }
            cursor.close(); // Remember to close the cursor when you're done with it
        }

        db.close();
        return result;
    }

}
