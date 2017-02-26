package com.meishikr.app.domain.dto.res;

import com.sin2pi.brick.common.http.BaseResponse;

/**
 * Created by yinhang on 16/3/13.
 */
public class ResAccessToken extends BaseResponse {
    public long expiration;
    public String accessToken;
}
