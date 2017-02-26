package com.meishikr.app.viewmodel.event;

/**
 * Created by yinhang on 16/7/7.
 */
public class EventLogin {

    public enum Result {
        SUCCEED,
        FAILED,
        ERROR,
        COMPLETED,
        ACCOUNT_OR_PASSWORD_INCORRECT,
    }

    public Result result;

    public EventLogin(Result result){
        this.result = result;
    }

}
