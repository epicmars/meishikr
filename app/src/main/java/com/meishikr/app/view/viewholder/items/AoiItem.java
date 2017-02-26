package com.meishikr.app.view.viewholder.items;

import com.meishikr.app.domain.entity.lbs.Location;

/**
 * Created by yinhang on 2016/12/16.
 */

public class AoiItem {

    private Location location;
    private String name;
    private String address;

    public AoiItem(Location location, String name, String address) {
        this.location = location;
        this.name = name;
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
