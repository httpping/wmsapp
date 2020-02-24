package tanping.com.myapplication.wx.core;

import java.util.concurrent.ThreadFactory;

/**
 * 自定义线程工厂
 */
public class MyThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}
