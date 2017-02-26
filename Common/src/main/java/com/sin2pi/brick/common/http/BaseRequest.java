package com.sin2pi.brick.common.http;

import com.sin2pi.brick.interfaces.http.IResponse;

/**
 * Created by yinhang on 16/6/14.
 */
public class BaseRequest<S extends BaseResponse> extends Request {

    @Override
    public IResponse request() {
        return null;
    }

    @Override
    public String url() {
        return null;
    }

    @Override
    public String getData() {
        return null;
    }
}
