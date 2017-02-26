package com.meishikr.app.di.component;


import com.meishikr.app.di.module.AppModule;
import com.meishikr.app.di.module.DaoModule;
import com.meishikr.app.di.module.RepoModule;
import com.meishikr.app.domain.executor.PostExecutionThread;
import com.meishikr.app.domain.executor.ThreadExecutor;
import com.meishikr.app.domain.repository.AuthRepo;
import com.meishikr.app.domain.repository.BlogRepo;
import com.meishikr.app.domain.repository.CurrentUserRepo;
import com.meishikr.app.domain.repository.LampRepo;
import com.meishikr.app.domain.repository.PhotoRepo;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yinhang on 16/7/5.
 */
@Singleton
@Component(modules = {AppModule.class, RepoModule.class, DaoModule.class})
public interface AppComponent {

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutorThread();

    AuthRepo authRepo();

    BlogRepo blogRepo();

    LampRepo lampRepo();

    PhotoRepo photoRepo();

    CurrentUserRepo currentUserRepo();
}
