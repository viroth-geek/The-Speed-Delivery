package com.planb.thespeed.service.base;

/**
 * @author yeakleang.ay on 9/5/18.
 */

public interface InvokeOnCompleteAsync<T> {
    void onComplete(T data);
    void onError(Throwable e);
}
