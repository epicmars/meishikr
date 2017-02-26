package com.meishikr.app.domain.repository;

/**
 * Created by yinhang on 16/11/6.
 */

public interface CurrentUserRepo {

    boolean isLogin();

    long getUserId();
}
