package com.meishikr.app;

import android.app.Service;
import android.telephony.TelephonyManager;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Created by yinhang on 16/7/13.
 */
public class DeviceInfoTest extends AndroidTestCase {

    private static final String TAG = DeviceInfoTest.class.getSimpleName();

    public void testDeviceid(){
        TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(Service.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        String imsi = telephonyManager.getSubscriberId();
        Log.i(TAG, imei + "/" + imsi);
    }
}
