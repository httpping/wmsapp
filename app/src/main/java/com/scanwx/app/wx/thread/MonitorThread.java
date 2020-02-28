package com.scanwx.app.wx.thread;

/**
 * Created by tanping on 2020/2/25.
 */
public interface MonitorThread {

    /**
     * @param t
     * @param r
     */
    default void beforeExecute(MyThreadPoolExecutor executor, Thread t, Runnable r) {
    }

    default void afterExecute(MyThreadPoolExecutor executor, Runnable r, Throwable t) {
    }

    default void terminated(MyThreadPoolExecutor executor) {
        System.out.println("terminated getCorePoolSize:" + executor.getCorePoolSize() + "；getPoolSize:" + executor.getPoolSize() + "；getTaskCount:" + executor.getTaskCount() + "；getCompletedTaskCount:"
                + executor.getCompletedTaskCount() + "；getLargestPoolSize:" + executor.getLargestPoolSize() + "；getActiveCount:" + executor.getActiveCount());
    }

}
