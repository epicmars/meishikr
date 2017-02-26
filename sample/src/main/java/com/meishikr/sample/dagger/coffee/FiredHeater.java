package com.meishikr.sample.dagger.coffee;

import javax.inject.Inject;

/**
 * Created by yinhang on 16/7/14.
 */
public class FiredHeater implements Heater {

    private boolean heating = false;

    @Inject
    public FiredHeater(){

    }

    @Override
    public void on() {
        System.out.println("power on");
        System.out.println("~ ~ ~ heating ~ ~ ~");
        this.heating = true;
    }

    @Override
    public void off() {
        this.heating = false;
    }

    @Override
    public boolean isHot() {
        return heating;
    }
}
