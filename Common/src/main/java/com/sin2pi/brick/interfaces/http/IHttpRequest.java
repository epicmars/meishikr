package com.sin2pi.brick.interfaces.http;

/**
 * Http request interface, implement this interface to private Http request functions.
 *
 * Created by christopher on 15-10-29.
 */
public interface IHttpRequest {

    /**
     * HTTP request method.
     *
     * @return Http response
     */
    IResponse request(IRequest request);
}
