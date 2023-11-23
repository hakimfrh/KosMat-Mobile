package com.KKDev.kosmat;

public class Api {
    public static String server_ip = "192.168.1.12";
    public static final String urlUser = "http://"+server_ip+"/kosmat-api/user.php";
    public static final String urlOwnerWhatsapp = "http://"+server_ip+"/kosmat-api/user.php?method=getOwnerWhatsapp";
    public static final String urlKamar = "http://"+server_ip+"/kosmat-api/kamar.php";
    public static final String urlKamarTerisi = "http://"+server_ip+"/kosmat-api/kamar.php?method=GetKamarTerisi";
    public static final String urlKamarKosong = "http://"+server_ip+"/kosmat-api/kamar.php?method=GetKamarKosong";
    public static final String urlKepemilikan = "http://"+server_ip+"/kosmat-api/kepemilikan.php";
}
