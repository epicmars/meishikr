package com.sin2pi.brick.components.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sin2pi.brick.common.Consts;


/**
 * Created by yinhang on 15/12/19.
 */
public abstract class AbstractActivity extends AppCompatActivity implements Consts{

    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    }
}
