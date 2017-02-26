package com.sin2pi.brick.common.http.annotation;

import com.sin2pi.brick.common.http.Consts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yinhang on 16/1/21.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Req {
    String url();
    ReqMethod method() default ReqMethod.GET;
    String contentType() default Consts.ContentType.X_WWW_FORM_URLENCODED;
}
