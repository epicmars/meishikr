package com.meishikr.app.domain.entity.lbs;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;


/**
 * 封装定位结果和时间的实体类
 * Created by yinhang on 16/2/21.
 */
@Entity
public class Location implements Parcelable{

    @Id(autoincrement = true)
    private Long id;

    private String name;

    private Date timestamp;

    @JSONField(name = "located_time")
    private long locatedTime;

    private double latitude;

    private double longitude;

    private float accuracy;

    private String description;

    private float bearing;

    // 使用GPS定位时
    private float speed;

    private double altitude;
    // 运营商
    private int operators;
    // 地址
    private String country;

    @JSONField(name = "country_code")
    private String countryCode;

    private String province;

    private String city;

    @JSONField(name = "city_code")
    private String cityCode;

    private String district;

    private String street;

    @JSONField(name = "street_number")
    private String streetNumber;

    private String address;

    public static final int DEFAULT = 0, POST = 1, TRACE = 2;
//    @IntDef({DEFAULT, POST, TRACE})
//    public @interface Event {}


    public boolean valid(){
        //纬度：[0° ,(+/–)90°]
        //经度：[0° ,(+/–)180°]
        if(Math.abs(latitude) <= 90 && Math.abs(longitude) <= 180){
            return true;
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeLong(this.timestamp != null ? this.timestamp.getTime() : -1);
        dest.writeLong(this.locatedTime);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeFloat(this.accuracy);
        dest.writeString(this.description);
        dest.writeFloat(this.bearing);
        dest.writeFloat(this.speed);
        dest.writeDouble(this.altitude);
        dest.writeInt(this.operators);
        dest.writeString(this.country);
        dest.writeString(this.countryCode);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.cityCode);
        dest.writeString(this.district);
        dest.writeString(this.street);
        dest.writeString(this.streetNumber);
        dest.writeString(this.address);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public long getLocatedTime() {
        return this.locatedTime;
    }

    public void setLocatedTime(long locatedTime) {
        this.locatedTime = locatedTime;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getAccuracy() {
        return this.accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getBearing() {
        return this.bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public double getAltitude() {
        return this.altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public int getOperators() {
        return this.operators;
    }

    public void setOperators(int operators) {
        this.operators = operators;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return this.cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return this.streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location() {
    }

    protected Location(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        long tmpTimestamp = in.readLong();
        this.timestamp = tmpTimestamp == -1 ? null : new Date(tmpTimestamp);
        this.locatedTime = in.readLong();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.accuracy = in.readFloat();
        this.description = in.readString();
        this.bearing = in.readFloat();
        this.speed = in.readFloat();
        this.altitude = in.readDouble();
        this.operators = in.readInt();
        this.country = in.readString();
        this.countryCode = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.cityCode = in.readString();
        this.district = in.readString();
        this.street = in.readString();
        this.streetNumber = in.readString();
        this.address = in.readString();
    }

    @Generated(hash = 634742736)
    public Location(Long id, String name, Date timestamp, long locatedTime,
            double latitude, double longitude, float accuracy, String description,
            float bearing, float speed, double altitude, int operators,
            String country, String countryCode, String province, String city,
            String cityCode, String district, String street, String streetNumber,
            String address) {
        this.id = id;
        this.name = name;
        this.timestamp = timestamp;
        this.locatedTime = locatedTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.accuracy = accuracy;
        this.description = description;
        this.bearing = bearing;
        this.speed = speed;
        this.altitude = altitude;
        this.operators = operators;
        this.country = country;
        this.countryCode = countryCode;
        this.province = province;
        this.city = city;
        this.cityCode = cityCode;
        this.district = district;
        this.street = street;
        this.streetNumber = streetNumber;
        this.address = address;
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
