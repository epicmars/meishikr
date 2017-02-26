package com.meishikr.app.di.module;

import android.app.Application;

import com.meishikr.app.domain.entity.comment.DaoMaster;
import com.meishikr.app.domain.entity.comment.DaoSession;

import org.greenrobot.greendao.database.Database;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yinhang on 16/11/4.
 */

@Module(includes = AppModule.class)
public class DaoModule {

    @Singleton
    @Provides
    public DaoMaster provideDaoMaster(Application context) {
        // green dao
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, com.meishikr.app.model.BuildConfig.DATABASE_NAME, null);
        Database db = helper.getWritableDb();
        DaoMaster master = new DaoMaster(db);
        return master;
    }

    @Singleton
    @Provides
    public DaoSession provideDaoSession(DaoMaster daoMaster) {
        return daoMaster.newSession();
    }

}
