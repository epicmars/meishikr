package com.sin2pi.brick.components.log;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by yinhang on 16/3/5.
 */
public class AppCrashHandler {

    private static Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private static Thread.UncaughtExceptionHandler defaultHandler;

    private static Context context;

    public static void initialize(Context ctx){
        context = ctx;
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        uncaughtExceptionHandler = new CrashHandler();
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
    }

    private static class CrashHandler implements Thread.UncaughtExceptionHandler{

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            File file = new File(context.getExternalFilesDir("log"), "log.txt");
            PrintWriter bw = null;
            try{
                bw = new PrintWriter(new PrintWriter(file));
                ex.printStackTrace(bw);
            } catch (IOException e){
                e.printStackTrace();
            } finally {
                if(null != bw)
                    bw.close();
            }
            defaultHandler.uncaughtException(thread, ex);
//            android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(10);
        }
    }
}
