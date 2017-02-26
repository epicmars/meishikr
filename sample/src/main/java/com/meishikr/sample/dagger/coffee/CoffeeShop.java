package com.meishikr.sample.dagger.coffee;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yinhang on 16/7/5.
 */
@Singleton
@Component(modules = {DripCoffeeModule.class})
public interface CoffeeShop {
    CoffeeMaker maker();
}
