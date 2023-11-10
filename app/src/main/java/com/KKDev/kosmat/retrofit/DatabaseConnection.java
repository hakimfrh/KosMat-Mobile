package com.KKDev.kosmat.retrofit;

import com.KKDev.kosmat.Api;
import com.KKDev.kosmat.model.User;
import com.KKDev.kosmat.model.UserResponse;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class DatabaseConnection {
    private String server = Api.server_ip;
    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://" +server +"/").addConverterFactory(GsonConverterFactory.create()).build();
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
        services.getAllUser("getAllUser").enqueue(new Callback<UserResponse>() {
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
    public void registerUser(User user, DatabaseCallback<UserResponse> callback) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("method", "register");
        jsonObject.put("nik", user.getNik());
        jsonObject.put("username", user.getUsername());
        jsonObject.put("password", user.getPassword());
        jsonObject.put("nama", user.getNama());
        jsonObject.put("no_whatsapp", user.getNo_whatsapp());
        jsonObject.put("no_whatsapp_wali", user.getNo_whatsapp_wali());
        jsonObject.put("privilege", user.getPrivilege());
        jsonObject.put("tgl_lahir", user.getTgl_lahir());
        jsonObject.put("gender", user.getGender());
        jsonObject.put("image", user.getImage());

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        services.registerUser(requestBody).enqueue(new Callback<UserResponse>() {
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
    }public void updateUser(String nik,User user, DatabaseCallback<UserResponse> callback) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("method", "update");
        jsonObject.put("user_nik", nik);
        jsonObject.put("nik", user.getNik());
        jsonObject.put("username", user.getUsername());
        jsonObject.put("password", user.getPassword());
        jsonObject.put("nama", user.getNama());
        jsonObject.put("no_whatsapp", user.getNo_whatsapp());
        jsonObject.put("no_whatsapp_wali", user.getNo_whatsapp_wali());
        jsonObject.put("privilege", user.getPrivilege());
        jsonObject.put("tgl_lahir", user.getTgl_lahir());
        jsonObject.put("gender", user.getGender());
        jsonObject.put("image", user.getImage());

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        services.updateUser(requestBody).enqueue(new Callback<UserResponse>() {
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
