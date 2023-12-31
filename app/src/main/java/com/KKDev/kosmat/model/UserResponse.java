package com.KKDev.kosmat.model;

import com.KKDev.kosmat.model.User;
import java.util.List;

public class UserResponse {
    private int code;
    private String status;
    private List<User> user_list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<User> getUserlist() {
        return user_list;
    }

    public void setUser_list(List<User> user_list) {
        this.user_list = user_list;
    }
}
