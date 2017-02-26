package com.meishikr.sample.dagger.api.lazy;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yinhang on 16/7/12.
 */
@Module
public class CounterModule {

    int next = 100;

    @Provides
    Integer provideInteger() {
        System.out.println("computing...");
        return next++;
    }
}
