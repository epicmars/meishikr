package com.meishikr.app.base;

import android.app.Activity;

/**
 * Created by yinhang on 2016/12/26.
 */

public abstract class FragmentViewModel extends BaseViewModel {


    private Activity activity;

    public FragmentViewModel(Activity context) {
        super(context);
        this.activity = context;
    }

    public void onAttach() {}

    public void onCreate() {}

    public void onCreateView() {}

    public void onActivityCreated() {}

    public void onStart() {}

    public void onResume() {}

    public void onPause() {}

    public void onStop() {}

    public void onDestoryView() {}

    public void onDestory() {}

    public void onDetach() {}

    public Activity getActivity() {
        return activity;
    }
}
