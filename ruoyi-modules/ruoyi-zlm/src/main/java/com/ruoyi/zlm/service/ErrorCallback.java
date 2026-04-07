package com.ruoyi.zlm.service;

public interface ErrorCallback<T> {

    void run(int code, String msg, T data);
}
