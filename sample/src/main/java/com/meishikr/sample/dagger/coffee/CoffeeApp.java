package com.meishikr.sample.dagger.coffee;

public class CoffeeApp {

    public static void main(String[] args) {
        CoffeeShop coffeeShop = DaggerCoffeeShop.create();
        coffeeShop.maker().brew();
    }
}
