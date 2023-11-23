package com.KKDev.kosmat.model;

import java.io.Serializable;

public class Kamar implements Serializable {
    String id_kamar;
    String nama_kamar;
    String harga_kamar;
    String deskripsi;
    String photo;
    String id_kepemilikan;
    String nik;
    String tgl_masuk;
    String nama;

    public Kamar(String id_kamar, String nama_kamar, String harga_kamar, String deskripsi, String photo) {
        this.id_kamar = id_kamar;
        this.nama_kamar = nama_kamar;
        this.harga_kamar = harga_kamar;
        this.deskripsi = deskripsi;
        this.photo = photo;
    }

    public Kamar(String id_kamar, String nama_kamar, String harga_kamar, String deskripsi, String photo, String id_kepemilikan, String nik, String tgl_masuk, String nama) {
        this.id_kamar = id_kamar;
        this.nama_kamar = nama_kamar;
        this.harga_kamar = harga_kamar;
        this.deskripsi = deskripsi;
        this.photo = photo;
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
