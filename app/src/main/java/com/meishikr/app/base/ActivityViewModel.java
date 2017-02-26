package com.meishikr.app.base;

import android.app.Activity;

/**
 * Created by yinhang on 2016/12/26.
 */

public abstract class ActivityViewModel extends BaseViewModel {


    private Activity activity;

    public ActivityViewModel(Activity context) {
        super(context);
        this.activity = context;
    }

    public void onCreate() {}

    public void onStart() {}

    public void onResume() {}

    public void onPause() {}

    public void onStop() {}

    public void onRestart() {}

    public void onDestory() {}

    public Activity getActivity() {
        return activity;
    }
}
