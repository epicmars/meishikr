package com.meishikr.app.di.module;

import android.app.Application;

import com.meishikr.app.di.UIThread;
import com.meishikr.app.domain.executor.PostExecutionThread;
import com.meishikr.app.domain.executor.ThreadExecutor;
import com.meishikr.app.model.executor.JobExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yinhang on 16/7/5.
 */
@Module
public class AppModule {

    public final Application app;

    public AppModule(Application app){
        this.app = app;
    }

    @Provides
    @Singleton
    public Application provideApplicationContext() {
        return app;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }
}
