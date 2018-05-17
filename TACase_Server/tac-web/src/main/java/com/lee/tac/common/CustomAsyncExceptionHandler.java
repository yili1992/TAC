package com.lee.tac.common;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * program: tac-root
 * description: ${description}
 * author: zhaoli@leoao.com
 * create: 2018-04-26 14:24
 **/
public class CustomAsyncExceptionHandler
        implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(
            Throwable throwable, Method method, Object... obj) {

        System.out.println("Exception message - " + throwable.getStackTrace());
        System.out.println("Method name - " + method.getName());
        for (Object param : obj) {
            System.out.println("Parameter value - " + param);
        }
    }

}
