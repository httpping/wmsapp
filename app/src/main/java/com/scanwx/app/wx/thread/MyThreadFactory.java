package com.scanwx.app.wx.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程工厂
 */
public class MyThreadFactory implements ThreadFactory {

    AtomicInteger count = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}
