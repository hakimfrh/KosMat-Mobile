package com.KKDev.kosmat.retrofit;

import com.KKDev.kosmat.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("kosmat-api/user.php")
    Call<UserResponse> getUser(
            @Query("method") String method,
            @Query("username") String username,
            @Query("password") String password
    );
    @POST("kosmat-api/user.php")
    Call<UserResponse> registerUser(
            @Query("username") String method,
            @Query("username") String nik,
            @Query("username") String username,
            @Query("password") String password,
            @Query("password") String nama,
            @Query("password") String no_whatsapp,
            @Query("password") String no_whatsapp_wali,
            @Query("password") String privilege,
            @Query("password") String tgl_lahir,
            @Query("password") String gender,
            @Query("password") String image
    );
}
