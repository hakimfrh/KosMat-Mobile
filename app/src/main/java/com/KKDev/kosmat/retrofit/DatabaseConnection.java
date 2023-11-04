package com.KKDev.kosmat.retrofit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class DatabaseConnection {
    private String server = "http://192.168.1.4/";
    Retrofit retrofit = new Retrofit.Builder().baseUrl(server).addConverterFactory(GsonConverterFactory.create()).build();
    ApiServices services = retrofit.create(ApiServices.class);
    public void login(String username, String password, DatabaseCallback<UserResponse> callback) {
        services.getUser("getUser", username, password).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error in network request"));
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
    public void getAllUser(DatabaseCallback<UserResponse> callback) {
        services.getUser("getAllUser", "", "").enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Exception("Error in network request"));
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
}
