package com.meishikr.sample.dagger.api.lazy;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yinhang on 16/7/12.
 */
@Singleton
@Component(modules = {CounterModule.class})
public interface CounterComponent {

    DirectCounter directCounter();

    ProviderCounter providerCounter();

    LazyCounter lazyCounter();

}
