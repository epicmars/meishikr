package com.meishikr.app.view.activity.lbs.amap;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.LocationSource;

/**
 * Created by yinhang on 2016/12/15.
 */

public class LocationClient implements LocationSource {

    private Context context;

    private OnLocationChangedListener locationChangedListener;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;

    private AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (locationChangedListener != null && aMapLocation != null) {
                if (aMapLocation != null
                        && aMapLocation.getErrorCode() == 0) {
                    locationChangedListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                } else {
                    String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                    Log.e("AmapErr", errText);
                }

            }
        }
    };

    public LocationClient(Context context, boolean isOnceLocation) {
        this.context = context;
        // 初始化定位参数
        aMapLocationClientOption = new AMapLocationClientOption();
        if (isOnceLocation) {
            setOnceLocation();
        }
    }

    public void setOnceLocation() {
        // 获取一次定位结果：
        // 该方法默认为false。
        aMapLocationClientOption.setOnceLocation(true);

        // 获取最近3s内精度最高的一次定位结果：
        // 设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
        // 如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        aMapLocationClientOption.setOnceLocationLatest(true);
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        locationChangedListener = listener;
        if (aMapLocationClient == null) {
            //初始化定位
            aMapLocationClient = new AMapLocationClient(context);
            //设置定位回调监听
            aMapLocationClient.setLocationListener(locationListener);
            //设置为高精度定位模式
            aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            aMapLocationClient.setLocationOption(aMapLocationClientOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            aMapLocationClient.startLocation();//启动定位
        }
    }

    /**
     * 销毁定位客户端。
     */
    @Override
    public void deactivate() {
        locationChangedListener = null;
        if (aMapLocationClient != null) {
            aMapLocationClient.stopLocation();
            aMapLocationClient.onDestroy();
        }
        aMapLocationClient = null;
    }

    public void startLocation() {
        if (null == aMapLocationClient || aMapLocationClient.isStarted())
            return;
        aMapLocationClient.startLocation();
    }

    public void stopLocation() {
        if (null == aMapLocationClient || !aMapLocationClient.isStarted())
            return;
        aMapLocationClient.stopLocation();
    }

}
