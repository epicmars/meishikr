package com.meishikr.app.domain.dto.req;

import com.meishikr.app.domain.dto.res.ResRegister;
import com.sin2pi.brick.common.http.BaseRequest;

/**
 * Created by yinhang on 16/3/11.
 */
public class ReqRegister extends BaseRequest<ResRegister> {

    /**
     * 手机号或者邮箱。
     */
    private String identity;
    /**
     * 密码（hash）。
     */
    private String secret;

    public ReqRegister(String identity, String secret) {
        this.identity = identity;
        this.secret = secret;
    }
}
