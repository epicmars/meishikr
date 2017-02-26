package com.sin2pi.brick.interfaces.dao;

/**
 * Created by yinhang on 16/1/26.
 */
public interface Savable<T> {
    void save(T obj);
    T load();
}
