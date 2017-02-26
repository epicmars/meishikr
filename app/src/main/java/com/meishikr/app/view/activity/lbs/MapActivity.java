package com.meishikr.app.view.activity.lbs;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.amap.api.maps.AMap;
import com.meishikr.app.R;
import com.meishikr.app.base.annotation.BindLayout;
import com.meishikr.app.databinding.ActivityMapBinding;
import com.meishikr.app.domain.repository.CurrentUserRepo;
import com.meishikr.app.base.BaseActivity;
import com.meishikr.app.view.activity.auth.LoginActivity;
import com.meishikr.app.view.activity.lbs.amap.LocationClient;

import javax.inject.Inject;

@BindLayout(R.layout.activity_map)
public class MapActivity extends BaseActivity<ActivityMapBinding> implements View.OnClickListener {

    private AMap map;
    private boolean shouldAnimate = true; // 是否首次定位

    private int markX = -1;
    private int markY = -1;

    private LocationClient locationClient;

//    @Inject
//    LocationDao locationDao;

    // dagger2 中MembersInjector中对实体域直接引用，不能声明为private的
    @Inject
    CurrentUserRepo currentUserRepo;

    public static void launch(Context context) {
        Intent intent = new Intent(context, MapActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);

        component.inject(this);
//        getAppComponent().inject(this);

        //
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            verifyStoragePermissions(this);
            verifyLocationPermissions(this);
        }

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        binding.content.mapView.onCreate(savedInstanceState);
        if (map == null) {
            map = binding.content.mapView.getMap();
        }

        locationClient = new LocationClient(this.getApplicationContext(), false);
        // 设置定位监听
        map.setLocationSource(locationClient);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        map.setMyLocationEnabled(true);
        // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        map.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

        binding.content.btnLocate.setOnClickListener(this);
        binding.content.btnPostMark.setOnClickListener(this);
        binding.content.btnEagleEye.setOnClickListener(this);
        binding.content.cbTraceSwitch.setOnClickListener(this);
        binding.content.cbTraceMark.setOnClickListener(this);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        // TODO layoutParams.topMargin初始为负值
//        // 设置topMargin还是直接设置top
//        if (hasFocus && markX < 0 && markY < 0) {
//            float x = (binding.content.mapView.getWidth() - binding.content.btnPostMark.getWidth()) / 2.0f;
//            float y = binding.content.mapView.getHeight() / 2.0f - binding.content.btnPostMark.getHeight();
//            // 此处假设了x,y均大于0
//            markX = binding.content.mapView.getWidth() / 2;
//            markY = binding.content.mapView.getHeight() / 2;
//            // 设置post标记下沿居中
////            markTop = ivPostMark.getTop() -ivPostMark.getHeight() / 2;
//            binding.content.btnPostMark.setX(x);
//            binding.content.btnPostMark.setY(y);
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationClient.startLocation();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        binding.content.mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        binding.content.mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        binding.content.mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationClient.stopLocation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        map.setMyLocationEnabled(false); // 此处会调用locationClient.deactivate();
        binding.content.mapView.onDestroy();
        map = null;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_locate:
                //
//                switch (locConfig.locationMode) {
//                    case NORMAL:
//                        binding.content.btnLocate.setImageResource(R.drawable.mode_following);
//                        setMapLocConfig(MyLocationConfiguration.LocationMode.FOLLOWING);
//                        break;
//                    case FOLLOWING:
//                        binding.content.btnLocate.setImageResource(R.drawable.mode_compass);
//                        setMapLocConfig(MyLocationConfiguration.LocationMode.COMPASS);
//                        break;
//                    case COMPASS:
//                        binding.content.btnLocate.setImageResource(R.drawable.mode_normal);
//                        setMapLocConfig(MyLocationConfiguration.LocationMode.NORMAL);
//                        break;
//                    default:
//                        break;
//                }
                //
                if (!shouldAnimate)
                    shouldAnimate = true;
                break;
            case R.id.cb_trace_mark:
                if (binding.content.cbTraceMark.isChecked()) {
                    // TODO 显示界面内所有轨迹
//                    List<LocationModel> locations = locationDao.getTraceLocation();
//                    if (null == locations)
//                        // TODO 提示
//                        return;
//                    for (LocationModel location : locations) {
//                        addMark(location, R.drawable.mark_shoe);
//                    }
                } else {
                    // TODO 仅移除轨迹Mark
                    map.clear();
                }
                break;

            case R.id.cb_trace_switch:
                if (binding.content.cbTraceSwitch.isChecked()) {
//                    locationNotifier.startTracing();
                } else {
//                    locationNotifier.stopTracing();
                }
                break;

            case R.id.btn_post_mark:
                // todo 动画效果，点击定位标志弹出位置选项。
                //
                //
                if (!currentUserRepo.isLogin()) {
                    LoginActivity.launch(this);
                    break;
                }
//                // TODO: 16/7/11
//                BDLocation bdLocation = locationNotifier.myLocation();
//                if (null == bdLocation)
//                    return;
////                // TODO 传递标记位置
//                final Location loc = LocationMapper.newLocation(bdLocation);
//                loc.setTimestamp(System.currentTimeMillis());
////                long locid = locationDao.save(loc);
//                saveLocation(bdLocation, LBSConsts.LocEvent.BLOG);
//
//                Intent intent = new Intent(context, BlogEditActivity.class);
//                intent.putExtra(LBSConsts.Extras.MARK_POS, loc);
//                startActivity(intent);
                break;
            default:
                break;
        }
    }

//    private void setMapLocConfig(MyLocationConfiguration.LocationMode locationMode) {
//        locConfig = new MyLocationConfiguration(
//                locationMode, true, null);
//        locConfig.accuracyCircleStrokeColor = Color.TRANSPARENT;
//        map.setMyLocationConfigeration(locConfig);
//    }
//
//    private void addMark(Location location, int markId) {
//        addMark(location.getLatitude(), location.getLongitude(), location.getBearing(), markId);
//    }
//
//    private void addMark(BDLocation location, int markId) {
//        addMark(location.getLatitude(), location.getLongitude(), location.getBearing(), markId);
//    }
//
//    private void addMark(double latitude, double longitude, float bearing, int markId) {
//        if (isDestroyed() || null == map || null == binding.content.mapView)
//            return;
//        LatLng point = new LatLng(latitude, longitude);
//        // 构建Marker图标
//        BitmapDescriptor bitmap = null;
////        if (iscal == 0) {
////            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.mark_shoe); // 非推算结果
////        } else {
////            bitmap = BitmapDescriptorFactory.fromResource(R.drawable.mark_shoe); // 推算结果
////        }
//        bitmap = BitmapDescriptorFactory.fromResource(markId); // 推算结果
//        // 构建MarkerOption，用于在地图上添加Marker
//        // TODO 旋转角度
//        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap).rotate(bearing);
//        // 在地图上添加Marker，并显示
//        map.addOverlay(option);
//        map.setMapStatus(MapStatusUpdateFactory.newLatLng(point));
//    }
//
//    private void saveLocation(BDLocation bdLocation, int event) {
//        // TODO: 16/7/11
//        final Location loc = LocationMapper.newLocation(bdLocation);
//        loc.setTimestamp(System.currentTimeMillis());
////        long locid = locationDao.save(loc);
//    }
//
//    private ServiceConnection connection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            locationNotifier = (LocationNotifier) service;
//            locationNotifier.registerLocationListener(locationListener);
//            locationNotifier.request();
//            locationNotifier.updateMapState();
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            locationNotifier.unregisterLocationListener();
//        }
//    };
//
//    private LocationListener locationListener = new LocationListener() {
//
//        @Override
//        public void onInitate(MapState status) {
//            if (status.isTracing) {
//                binding.content.cbTraceSwitch.setChecked(true);
//            }
//        }
//
//        @Override
//        public void onMyLocation(BDLocation bdLocation) {
//            if (null != map) {
//                MyLocationData locData = new MyLocationData.Builder()
//                        .accuracy(bdLocation.getAccuracy())
//                        // 此处设置开发者获取到的方向信息，顺时针0-360
//                        .bearing(bdLocation.getBearing()).latitude(bdLocation.getLatitude())
//                        .longitude(bdLocation.getLongitude()).build();
//                map.setMyLocationData(locData);
//            }
//            //
//            if (shouldAnimate) {
//                shouldAnimate = false;
//                LatLng ll = new LatLng(bdLocation.getLatitude(),
//                        bdLocation.getLongitude());
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(ll).zoom(18.0f);
//                map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//            }
//            // 日志
//            StringBuffer sb = new StringBuffer(256);
//            sb.append("timestamp : ");
//            /**
//             * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
//             * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
//             */
//            sb.append(bdLocation.getTime());
//            sb.append("\nerror code : ");
//            sb.append(bdLocation.getLocType());
//            sb.append("\nlatitude : ");
//            sb.append(bdLocation.getLatitude());
//            sb.append("\nlontitude : ");
//            sb.append(bdLocation.getLongitude());
//            sb.append("\nradius : ");
//            sb.append(bdLocation.getAccuracy());
//            sb.append("\nCountryCode : ");
//            sb.append(bdLocation.getCountryCode());
//            sb.append("\nCountry : ");
//            sb.append(bdLocation.getCountry());
//            sb.append("\ncitycode : ");
//            sb.append(bdLocation.getCityCode());
//            sb.append("\ncity : ");
//            sb.append(bdLocation.getCity());
//            sb.append("\nDistrict : ");
//            sb.append(bdLocation.getDistrict());
//            sb.append("\nStreet : ");
//            sb.append(bdLocation.getStreet());
//            sb.append("\naddr : ");
//            sb.append(bdLocation.getAddrStr());
//            sb.append("\nDescribe: ");
//            sb.append(bdLocation.getLocationDescribe());
//            sb.append("\nDirection(not all devices have value): ");
//            sb.append(bdLocation.getBearing());
//            sb.append("\nPoi: ");
//            if (bdLocation.getPoiList() != null && !bdLocation.getPoiList().isEmpty()) {
//                for (int i = 0; i < bdLocation.getPoiList().size(); i++) {
//                    Poi poi = bdLocation.getPoiList().get(i);
//                    sb.append(poi.getName() + ";");
//                }
//            }
//            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
//                sb.append("\nspeed : ");
//                sb.append(bdLocation.getSpeed());// 单位：km/h
//                sb.append("\nsatellite : ");
//                sb.append(bdLocation.getSatelliteNumber());
//                sb.append("\nheight : ");
//                sb.append(bdLocation.getAltitude());// 单位：米
//                sb.append("\ndescribe : ");
//                sb.append("gps定位成功");
//            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
//                // 运营商信息
//                sb.append("\noperationers : ");
//                sb.append(bdLocation.getOperators());
//                sb.append("\ndescribe : ");
//                sb.append("网络定位成功");
//            } else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
//                sb.append("\ndescribe : ");
//                sb.append("离线定位成功，离线定位结果也是有效的");
//            } else if (bdLocation.getLocType() == BDLocation.TypeServerError) {
//                sb.append("\ndescribe : ");
//                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
//            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
//                sb.append("\ndescribe : ");
//                sb.append("网络不同导致定位失败，请检查网络是否通畅");
//            } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
//                sb.append("\ndescribe : ");
//                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
//            }
//            Timber.d(sb.toString());
//        }
//
//        @Override
//        public void onTraceLocation(BDLocation bdLocation) {
//            addMark(bdLocation, R.drawable.mode_following);
//        }
//    };

    public static class MapState {
        public boolean isTracing;
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_ACCESS_LOCATION = 2;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    private static String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    /**
     * Checks if the app has permission to write to device storage
     * <p>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public static void verifyLocationPermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_LOCATION,
                    REQUEST_ACCESS_LOCATION
            );
        }
    }

}
