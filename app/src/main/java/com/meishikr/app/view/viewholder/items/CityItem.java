package com.meishikr.app.view.viewholder.items;

import com.meishikr.app.domain.entity.lbs.Location;

/**
 * Created by yinhang on 2016/12/16.
 */

public class CityItem {
    private Location location;
    private String city;

    public CityItem(Location location, String city) {
        this.location = location;
        this.city = city;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
