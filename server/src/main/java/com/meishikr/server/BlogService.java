package com.meishikr.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.IOException;

public class BlogService extends Service {
    private BlogHttpServer httpServer;

    public BlogService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        httpServer = new BlogHttpServer(5000);
        httpServer.addMappings();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(httpServer.isAlive()){
            httpServer.stop();
        }
        try{
            httpServer.start();
        } catch (IOException e){

        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        httpServer.stop();
    }
}
