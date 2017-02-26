package com.meishikr.app.domain.dto.res;


import com.alibaba.fastjson.annotation.JSONField;
import com.sin2pi.brick.common.http.BaseResponse;

/**
 * Created by yinhang on 16/3/11.
 */
public class ResRegister extends BaseResponse {

    @JSONField(name = "refresh_token")
    public String refreshToken;
    @JSONField(name = "access_token")
    public String accessToken;

    public long expiration;
}
