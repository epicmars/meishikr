package com.meishikr.app.domain.dto.req;

import com.alibaba.fastjson.annotation.JSONField;
import com.meishikr.app.domain.dto.res.ResAccessToken;
import com.sin2pi.brick.common.http.BaseRequest;

/**
 * Created by yinhang on 16/3/13.
 */
public class ReqAccessToken extends BaseRequest<ResAccessToken> {

    @JSONField(name = "refresh_token")
    private String refreshToken;

    public ReqAccessToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
