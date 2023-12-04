package com.KKDev.kosmat;

public class Api {
    public static String server_ip = "https://kosmat.tifb.myhost.id/kosmat";
//    public static String server_ip = "http://192.168.1.17";

    public static final String urlUser = server_ip+"/kosmat-api/user.php";
    public static final String urlOwnerWhatsapp = server_ip+"/kosmat-api/user.php?method=getOwnerWhatsapp";
    public static final String urlKamar = server_ip+"/kosmat-api/kamar.php";
    public static final String urlKamarTerisi = server_ip+"/kosmat-api/kamar.php?method=GetKamarTerisi";
    public static final String urlKamarKosong = server_ip+"/kosmat-api/kamar.php?method=GetKamarKosong";
    public static final String urlKepemilikan = server_ip+"/kosmat-api/kepemilikan.php";
    public static final String urlTranskasi = server_ip+"/kosmat-api/transaksi.php";
    public static final String urlKeluhan = server_ip+"/kosmat-api/keluhan.php";
    public static final String urlImage = server_ip+"/kosmat-api/image.php";
}
