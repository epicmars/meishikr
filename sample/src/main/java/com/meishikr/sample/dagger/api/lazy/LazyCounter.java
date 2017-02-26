package com.meishikr.sample.dagger.api.lazy;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Created by yinhang on 16/7/12.
 */
public class LazyCounter {

    @Inject
    Lazy<Integer> lazy;

    @Inject
    public LazyCounter(){

    }

    public void print() {
        // a new value is computed immediately before it is needed.
        // The same value is returned for all subsequent uses
        System.out.println("printing...");
        System.out.println(lazy.get());
        System.out.println(lazy.get());
        System.out.println(lazy.get());
    }
}
