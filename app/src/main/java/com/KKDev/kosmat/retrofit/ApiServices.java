package com.KKDev.kosmat.retrofit;

import com.KKDev.kosmat.model.User;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
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
    @GET("kosmat-api/user.php")
    Call<UserResponse> getAllUser(
            @Query("method") String method
    );
    @POST("kosmat-api/user.php")
    Call<UserResponse> registerUser(
            @Body RequestBody requestBody
    );
}
