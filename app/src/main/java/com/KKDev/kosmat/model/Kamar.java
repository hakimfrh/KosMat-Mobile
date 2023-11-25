package com.KKDev.kosmat.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Base64;

public class Kamar implements Serializable {
    String id_kamar;
    String nama_kamar;
    String harga_kamar;
    String deskripsi;
    String photo;
    String image_data;
    String id_kepemilikan;
    String nik;
    String tgl_masuk;
    String nama;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Kamar(String id_kamar, String nama_kamar, String harga_kamar, String deskripsi, String photo, byte[] image_data) {
        this.id_kamar = id_kamar;
        this.nama_kamar = nama_kamar;
        this.harga_kamar = harga_kamar;
        this.deskripsi = deskripsi;
        this.photo = photo;
        this.image_data = Base64.getEncoder().encodeToString(image_data);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Kamar(String id_kamar, String nama_kamar, String harga_kamar, String deskripsi, String photo, byte[] image_data, String id_kepemilikan, String nik, String tgl_masuk, String nama) {
        this.id_kamar = id_kamar;
        this.nama_kamar = nama_kamar;
        this.harga_kamar = harga_kamar;
        this.deskripsi = deskripsi;
        this.photo = photo;
        this.image_data = Base64.getEncoder().encodeToString(image_data);
        this.id_kepemilikan = id_kepemilikan;
        this.nik = nik;
        this.tgl_masuk = tgl_masuk;
        this.nama = nama;
    }

    public String getId_kamar() {
        return id_kamar;
    }

    public void setId_kamar(String id_kamar) {
        this.id_kamar = id_kamar;
    }

    public String getNama_kamar() {
        return nama_kamar;
    }

    public void setNama_kamar(String nama_kamar) {
        this.nama_kamar = nama_kamar;
    }

    public String getHarga_kamar() {
        return harga_kamar;
    }

    public void setHarga_kamar(String harga_kamar) {
        this.harga_kamar = harga_kamar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public String getId_kepemilikan() {
        return id_kepemilikan;
    }

    public void setId_kepemilikan(String id_kepemilikan) {
        this.id_kepemilikan = id_kepemilikan;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getTgl_masuk() {
        return tgl_masuk;
    }

    public void setTgl_masuk(String tgl_masuk) {
        this.tgl_masuk = tgl_masuk;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

}
