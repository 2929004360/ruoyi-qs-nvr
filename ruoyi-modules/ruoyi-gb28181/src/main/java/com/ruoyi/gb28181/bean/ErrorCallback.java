package com.ruoyi.gb28181.bean;

public interface ErrorCallback<T> {

    void run(int code, String msg, T data);
}
