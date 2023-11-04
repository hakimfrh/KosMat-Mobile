package com.KKDev.kosmat.retrofit;

public interface DatabaseCallback<T> {
    void onSuccess(T data);

    void onError(Throwable t);
}
