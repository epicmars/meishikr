package com.meishikr.sample.dagger.api.lazy;


import static com.meishikr.sample.dagger.api.lazy.DaggerCounterComponent.*;

/**
 * Created by yinhang on 16/7/12.
 */
public class CounterSample {

    public static void main(String[] args) {
        CounterComponent component = DaggerCounterComponent.create();
//        component.directCounter().print();
//        component.directCounter().print();

        component.providerCounter().print();
        component.providerCounter().print();

//        component.lazyCounter().print();
//        component.lazyCounter().print();
    }

}
