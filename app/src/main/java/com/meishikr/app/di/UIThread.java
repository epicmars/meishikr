package com.meishikr.app.di;


import com.meishikr.app.domain.executor.PostExecutionThread;

import javax.inject.Inject;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * MainThread (UI Thread) implementation based on a {@link rx.Scheduler}
 * which will execute actions on the Android UI thread
 */
//@Singleton
public class UIThread implements PostExecutionThread {

  @Inject
  public UIThread() {}

  @Override
  public Scheduler getScheduler() {
    return AndroidSchedulers.mainThread();
  }
}
