package com.KKDev.kosmat.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Base64;

public class User implements Serializable {
    private String nik;
    private String username;
    private String password;
    private String nama;
    private String no_whatsapp;
    private String no_whatsapp_wali;
    private String privilege;
    private String tgl_lahir;
    private String gender;
    private String image;
    private String image_data;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public User(String nik, String username, String password, String nama, String no_whatsapp, String no_whatsapp_wali, String privilege, String tgl_lahir, String gender, byte[] image_data) {
        this.nik = nik;
        this.username = username;
        this.password = password;
        this.nama = nama;
        this.no_whatsapp = no_whatsapp;
        this.no_whatsapp_wali = no_whatsapp_wali;
        this.privilege = privilege;
        this.tgl_lahir = tgl_lahir;
        this.gender = gender;
        this.image_data = Base64.getEncoder().encodeToString(image_data);
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_whatsapp() {
        return no_whatsapp;
    }

    public void setNo_whatsapp(String no_whatsapp) {
        this.no_whatsapp = no_whatsapp;
    }

    public String getNo_whatsapp_wali() {
        return no_whatsapp_wali;
    }

    public void setNo_whatsapp_wali(String no_whatsapp_wali) {
        this.no_whatsapp_wali = no_whatsapp_wali;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_data() {
        return image_data;
    }

    public void setImage_data(String image_data) {
        this.image_data = image_data;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public byte[] getImageByte() {
        return Base64.getDecoder().decode(image_data);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setImageByte(byte[] imageData) {
        this.image_data = Base64.getEncoder().encodeToString(imageData);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Bitmap getImageBitmap(){
        byte[] byteArray = getImageByte();
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }
}
