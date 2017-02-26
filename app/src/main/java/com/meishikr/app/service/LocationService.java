//package com.meishikr.app.service;
//
//import android.app.Notification;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Message;
//import android.support.v4.app.NotificationCompat;
//import android.widget.Toast;
//
//import com.meishikr.app.R;
//import com.meishikr.app.domain.entity.map.Location;
//import com.meishikr.app.domain.dao.map.LocationDao;
//import com.meishikr.app.utils.LocationMapper;
//import com.meishikr.app.view.activity.lbs.baidu.BdLocationClient;
//import com.meishikr.app.view.activity.lbs.LBSConsts;
//import com.meishikr.app.view.activity.lbs.baidu.LocationNotifier;
//import com.meishikr.app.view.activity.lbs.MapActivity;
//
//import java.util.LinkedList;
//
//import javax.inject.Inject;
//
//import timber.log.Timber;
//
//public class LocationService extends Service {
//
//    private static final int ONE_SECOND = 1000;
//    private static final int SCAN_SPAN_10s = 10 * ONE_SECOND;
//    private static final int SCAN_SPAN_3s = 3 * ONE_SECOND;
//    private static final int SCAN_SPAN_ONCE = 0;
//    private static final int REQUEST_CODE_TRACING = 1;
//    private static final int ID_NOTIFICATION_TRACING = 101;
//
//    private BdLocationClient locationClient;
//
//    private LocHandler locHander;
//    private SDKReceiver sdkReceiver;
//    private LinkedList<Location> locationList = new LinkedList<>(); // 存放历史定位结果的链表，最大存放当前结果的前5次定位结果
//
//    private SensorService sensorService;
//    private int bearing = -1;
//    private boolean startTracing = false;
//    private LocationNotifier locationNotifier;
//    private Thread worker;
//
//    //
//    @Inject
//    LocationDao locationDao;
//
//    private BDLocation bdLocation;
//    private Location myLocation;
//
//    public LocationService() {
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        myLocation = new Location();
//        // 定位服务初始化
//        locationClient = new BdLocationClient(this);
//        locationClient.setLocationOption(locationClient.getDefaultLocationClientOption());
//        locationClient.registerListener(bdLocationListener);
//        //
//        locationNotifier = new LocationNotifier(this);
//        locHander = new LocHandler();
//        sensorService = new SensorService(this, Sensor.TYPE_ORIENTATION);
//        // sdk服务是否正常
//        // 注册 SDK 广播监听者
//        IntentFilter iFilter = new IntentFilter();
//        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
//        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
//        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
//        sdkReceiver = new SDKReceiver();
//        registerReceiver(sdkReceiver, iFilter);
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        if(null == worker){
//            worker = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    while(true){
//                        try{
//                            Thread.sleep(10000);
//                        } catch (InterruptedException e){
//
//                        }
//                        if(!locationClient.isStarted())
//                            locationClient.start();
//                    }
//                }
//            });
//            worker.start();
//        }
//        return START_STICKY;
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        locationClient.start();
//        //
//        sensorService.registerListener(new SensorEventListener() {
//            @Override
//            public void onSensorChanged(SensorEvent event) {
//                bearing = (int) event.values[0];
//            }
//
//            @Override
//            public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//            }
//        });
//        return locationNotifier;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(sdkReceiver);
//        sensorService.unresigerListener();
//        locationClient.unregisterListener(bdLocationListener);
//        locationClient.stop();
//    }
//
//    /**
//     * 构造广播监听类，监听 SDK key 验证以及网络异常广播
//     */
//    public class SDKReceiver extends BroadcastReceiver {
//        public void onReceive(Context context, Intent intent) {
//            String s = intent.getAction();
//            Timber.d("action: " + s);
//            if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
//                Toast.makeText(LocationService.this, "key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置", Toast.LENGTH_LONG).show();
//            } else if (s
//                    .equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK)) {
//                Timber.d("key 验证成功! 功能可以正常使用");
//            } else if (s
//                    .equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
//                Toast.makeText(context, "请检查网络连接", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    public void startTracing(){
//        startTracing = true;
//        locationClient.request();
//        // 设置定位时间间隔为10秒
//        LocationClientOption option = locationClient.getOption();
//        option.setScanSpan(SCAN_SPAN_10s);
//        locationClient.setLocationOption(option);
//        locationClient.start();
//        //
//        Intent intent = new Intent(this, MapActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        Notification notification = new NotificationCompat.Builder(this).setContentIntent(pendingIntent)
//                .setSmallIcon(R.drawable.mode_following)
//                .setContentInfo("定位中")
//                .setContentText("定位中")
//                .setContentTitle("定位中").build();
//        startForeground(1, notification);
//    }
//
//    public void stopTracing(){
//        startTracing = false;
//        // 设置定位为一次
//        LocationClientOption option = locationClient.getOption();
//        option.setScanSpan(SCAN_SPAN_ONCE);
//        locationClient.setLocationOption(option);
//        locationClient.start();
//        locationClient.request();
//        //
//        stopForeground(true);
//    }
//
//    public void request(){
//        locationClient.request();
//    }
//
//    public void updateMapState(){
//        MapActivity.MapState mapState = new MapActivity.MapState();
//        mapState.isTracing = startTracing;
//        locationNotifier.onInitate(mapState);
//    }
//
//    public BDLocation myLocation(){
//        return bdLocation;
//    }
//
//    /**
//     * 保存地点到
//     * @param bdLocation
//     * @param event
//     */
//    private void saveLocation(BDLocation bdLocation, int event) {
//        final Location loc = LocationMapper.newLocation(bdLocation);
//        loc.setTimestamp(System.currentTimeMillis());
//        long locid = locationDao.save(loc);
//    }
//
//    /***
//     * 平滑策略代码实现方法，主要通过对新定位和历史定位结果进行速度评分，
//     * 来判断新定位结果的抖动幅度，如果超过经验值，则判定为过大抖动，进行平滑处理,若速度过快，
//     * 则推测有可能是由于运动速度本身造成的，则不进行低速平滑处理 ╭(●｀∀´●)╯
//     *
//     * @param location
//     * @return
//     */
//    private Bundle algorithm(BDLocation location) {
//        Bundle locData = new Bundle();
//        double curSpeed = 0;
//        if (locationList.isEmpty() || locationList.size() < 2) {
//            // TODO: 16/7/10
////            Location temp = new Location(location);
//            Location temp = new Location();
//            temp.setTimestamp(System.currentTimeMillis());
//            locData.putInt("iscalculate", 0);
//            locationList.add(temp);
//        } else {
//            if (locationList.size() > 5)
//                locationList.removeFirst();
//            double score = 0;
//            for (int i = 0; i < locationList.size(); ++i) {
//                LatLng lastPoint = new LatLng(locationList.get(i).getLatitude(),
//                        locationList.get(i).getLongitude());
//                LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());
//                double distance = DistanceUtil.getDistance(lastPoint, curPoint);
//                curSpeed = distance / (System.currentTimeMillis() - locationList.get(i).getTimestamp().getTime()) / 1000;
//                score += curSpeed * LBSConsts.EARTH_WEIGHT[i];
//            }
//            if (score > 0.00000999 && score < 0.00005) { // 经验值,开发者可根据业务自行调整，也可以不使用这种算法
//                location.setLongitude(
//                        (locationList.get(locationList.size() - 1).getLongitude() + location.getLongitude())
//                                / 2);
//                location.setLatitude(
//                        (locationList.get(locationList.size() - 1).getLatitude() + location.getLatitude())
//                                / 2);
//                locData.putInt("iscalculate", 1);
//            } else {
//                locData.putInt("iscalculate", 0);
//            }
//            // TODO: 16/7/10
////            Location newLocation = new Location(location);
//            Location newLocation = new Location();
//            newLocation.setTimestamp(System.currentTimeMillis());
//            locationList.add(newLocation);
//
//        }
//        return locData;
//    }
//
//    private BDLocationListener bdLocationListener = new BDLocationListener() {
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            // 显示定位点到地图
//            // map view 销毁后不在地图上处理新接收的位置
//            if (location == null || location.getLocType() == BDLocation.TypeServerError) {
//                return;
//            }
//            bdLocation = location;
//            // 跟踪
//            if(startTracing){
//                Message locMsg = locHander.obtainMessage();
//                Bundle data = algorithm(location);
//                if (data != null) {
//                    data.putParcelable("loc", location);
//                    locMsg.setData(data);
//                    locHander.sendMessage(locMsg);
//                }
//            }
//            locationNotifier.onMyLocation(location);
//        }
//    };
//
//    /***
//     * 接收定位结果消息，并显示在地图上
//     */
//    private class LocHandler extends Handler {
//
//        @Override
//        public void handleMessage(Message msg) {
//            BDLocation location = msg.getData().getParcelable("loc");
//            if (null == location) {
//                return;
//            }
//            int iscal = msg.getData().getInt("iscalculate");
//            if(startTracing){
//                locationNotifier.onTraceLocation(location);
//                saveLocation(bdLocation, Location.TRACE);
//            }
//        }
//    }
//
//}
