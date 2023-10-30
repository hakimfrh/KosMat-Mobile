package com.KKDev.kosmat.adapter;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.io.Serializable;

public class User implements Serializable {
    private String nik;
    private String username;
    private String password;
    private String nama;
    private String noWhatsapp;
    private String privilege;
    private String tglLahir;
    private String gender;
    private byte[] image;

    public User(String nik, String username, String password, String nama, String noWhatsapp, String privilege, String tglLahir, String gender, byte[] image) {
        this.nik = nik;
        this.username = username;
        this.password = password;
        this.nama = nama;
        this.noWhatsapp = noWhatsapp;
        this.privilege = privilege;
        this.tglLahir = tglLahir;
        this.gender = gender;
        this.image = image;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }
    public String getNik() {
        return nik;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNama() {
        return nama;
    }

    public String getNoWhatsapp() {
        return noWhatsapp;
    }

    public String getPrivilege() {
        return privilege;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public String getGender() {
        return gender;
    }
    public byte[] getImage() {
        return image;
    }
}
