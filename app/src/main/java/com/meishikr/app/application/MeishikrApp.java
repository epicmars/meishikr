package com.meishikr.app.application;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.meishikr.app.BuildConfig;
import com.meishikr.app.base.utils.AppUtils;
import com.meishikr.app.di.component.AppComponent;
import com.meishikr.app.di.component.DaggerAppComponent;
import com.meishikr.app.di.module.AppModule;
import com.meishikr.app.view.activity.home.HomeActivity;
import com.sin2pi.brick.components.log.AppCrashHandler;

import timber.log.Timber;

/**
 * Created by yinhang on 16/2/20.
 */
public class MeishikrApp extends MultiDexApplication {

    protected AppComponent appComponent;

    public static MeishikrApp get(Context context) {
        return (MeishikrApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // sdk
        AppCrashHandler.initialize(this);
        Fresco.initialize(this);
        Timber.plant(new Timber.DebugTree());

//        Stetho.initializeWithDefaults(this);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());

        // debug
        if(BuildConfig.DEBUG){
            enableStrictMode();
        }
        // dependency injection
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void enableStrictMode() {
        if (AppUtils.hasGingerbread()) {
            StrictMode.ThreadPolicy.Builder threadPolicyBuilder = new StrictMode.ThreadPolicy.Builder().detectAll()
                    .penaltyLog();
            StrictMode.VmPolicy.Builder vmPolicyBuilder = new StrictMode.VmPolicy.Builder().detectAll().penaltyLog();

            if (AppUtils.hasHoneycomb()) {
                threadPolicyBuilder.penaltyFlashScreen();
                vmPolicyBuilder.setClassInstanceLimit(HomeActivity.class, 1);
            }
            StrictMode.setThreadPolicy(threadPolicyBuilder.build());
            StrictMode.setVmPolicy(vmPolicyBuilder.build());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }



    public AppComponent getAppComponent(){
        return appComponent;
    }
}
