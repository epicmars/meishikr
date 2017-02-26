package com.meishikr.sample.dagger.api.lazy;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * Created by yinhang on 16/7/12.
 */
public class ProviderCounter {

    @Inject
    Provider<Integer> provider;

    @Inject
    public ProviderCounter(){

    }

    public void print() {
        // a new value is computed each time Provider.get() is used:
        System.out.println("printing...");
        System.out.println(provider.get());
        System.out.println(provider.get());
        System.out.println(provider.get());
    }
}
