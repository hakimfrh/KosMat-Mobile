package com.KKDev.kosmat.model;

public class transaksi {
    private String tanggal;
    private String tipe;
    private String nama;
    private String total;

    public transaksi(String tanggal, String tipe, String nama, String total) {
        this.tanggal = tanggal;
        this.tipe = tipe;
        this.nama = nama;
        this.total = total;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
