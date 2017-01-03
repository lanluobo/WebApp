package com.jia.common;

/**
 * Created by jiaxl on 2016/10/14.
 */
public class ServiceException extends RuntimeException{

    public ServiceException(String desc,Exception e){
        super(desc,e);
    }
}
