package com.itheima.reggie.common;

/**
 * @author Diyang Li
 * @create 2022-07-07 12:00 PM
 */

public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
