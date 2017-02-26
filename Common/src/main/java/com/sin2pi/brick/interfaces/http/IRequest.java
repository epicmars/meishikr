package com.sin2pi.brick.interfaces.http;

/**
 * Interface of request entity.
 *
 * Created by christopher on 15-10-29.
 */
public interface IRequest {

    IResponse request();

    String url();

    String getData();
}
