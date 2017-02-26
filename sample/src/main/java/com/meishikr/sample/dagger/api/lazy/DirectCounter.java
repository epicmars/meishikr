package com.meishikr.sample.dagger.api.lazy;

import javax.inject.Inject;

/**
 * Created by yinhang on 16/7/12.
 */
public class DirectCounter {

    @Inject
    Integer value;

    @Inject
    public DirectCounter(){

    }

    public void print() {
        // value is computed before it is required
        System.out.println("printing...");
        System.out.println(value);
        System.out.println(value);
        System.out.println(value);
    }
}