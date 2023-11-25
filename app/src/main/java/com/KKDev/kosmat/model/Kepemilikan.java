package com.KKDev.kosmat.model;

public class Kepemilikan {
    User user;
    Kamar kamar;
    String id_kepemilikan;
    String tgl_masuk;

    public Kepemilikan(User user, Kamar kamar, String id_kepemilikan, String tgl_masuk) {
        this.user = user;
        this.kamar = kamar;
        this.id_kepemilikan = id_kepemilikan;
        this.tgl_masuk = tgl_masuk;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Kamar getKamar() {
        return kamar;
    }

    public void setKamar(Kamar kamar) {
        this.kamar = kamar;
    }

    public String getId_kepemilikan() {
        return id_kepemilikan;
    }

    public void setId_kepemilikan(String id_kepemilikan) {
        this.id_kepemilikan = id_kepemilikan;
    }

    public String getTgl_masuk() {
        return tgl_masuk;
    }

    public void setTgl_masuk(String tgl_masuk) {
        this.tgl_masuk = tgl_masuk;
    }
}

