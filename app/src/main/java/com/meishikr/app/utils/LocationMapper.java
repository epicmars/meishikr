package com.meishikr.app.utils;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.PoiItem;
import com.meishikr.app.domain.entity.lbs.Location;

/**
 * Created by yinhang on 16/8/7.
 */
public class LocationMapper {

    public static Location newLocation(AMapLocation aMapLocation){
        Location location = new Location();
        location.setLocatedTime(aMapLocation.getTime());

        location.setLongitude(aMapLocation.getLongitude());
        location.setLatitude(aMapLocation.getLatitude());
        location.setAltitude(aMapLocation.getAltitude());
        location.setSpeed(aMapLocation.getSpeed());
        location.setAccuracy(aMapLocation.getAccuracy());
        location.setBearing(aMapLocation.getBearing());

        location.setCountry(aMapLocation.getCountry());
        location.setProvince(aMapLocation.getProvince());
        location.setCity(aMapLocation.getCity());
        location.setCityCode(aMapLocation.getCityCode());
        location.setDistrict(aMapLocation.getDistrict());
        location.setStreet(aMapLocation.getStreet());
        location.setStreetNumber(aMapLocation.getStreetNum());

        location.setDescription(aMapLocation.getLocationDetail());
        return location;
    }

    public static Location newLocation(PoiItem poiItem) {
        Location location = new Location();

        location.setName(poiItem.getTitle());
        location.setLongitude(poiItem.getLatLonPoint().getLongitude());
        location.setLatitude(poiItem.getLatLonPoint().getLatitude());

        location.setProvince(poiItem.getProvinceName());

        location.setCity(poiItem.getCityName());
        location.setCityCode(poiItem.getCityCode());

        return location;
    }

}
