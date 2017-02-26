package com.meishikr.app.domain.dto.req;

import com.meishikr.app.domain.dto.res.ResLogin;
import com.sin2pi.brick.common.http.BaseRequest;

/**
 * Created by yinhang on 16/3/11.
 */
public class ReqLogin extends BaseRequest<ResLogin> {

    public String identity;
    /**
     * Use hashed secret to login.
     */
    public String secret;

    public ReqLogin(String identity, String secret){
        this.identity = identity;
        this.secret = secret;
    }
}
