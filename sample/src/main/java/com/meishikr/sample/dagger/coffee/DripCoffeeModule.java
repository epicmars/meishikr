package com.meishikr.sample.dagger.coffee;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = PumpModule.class)
class DripCoffeeModule {
    @Provides
    @Singleton
    @Named("electric")
    Heater electricHeater(ElectricHeater heater) {
        return heater;
    }

    @Provides
    @Singleton
    @Named("fired")
    Heater firedHeater(FiredHeater heater){
        return heater;
    }
}
