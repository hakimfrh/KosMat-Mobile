package com.KKDev.kosmat.model;

public class Kamar {
    String id_kamar;
    String nama_kamar;
    String harga_kamar;
    String deskripsi;
    String photo;

    public Kamar(String id_kamar, String nama_kamar, String harga_kamar, String deskripsi, String photo) {
        this.id_kamar = id_kamar;
        this.nama_kamar = nama_kamar;
        this.harga_kamar = harga_kamar;
        this.deskripsi = deskripsi;
        this.photo = photo;
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
}
