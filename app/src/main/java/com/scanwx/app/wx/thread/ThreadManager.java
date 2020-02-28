package com.scanwx.app.wx.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadManager {

    private  static ExecutorService pool ;

    /**
     * 单利线程池
     * blockSize 越大越好，不会阻塞运行
     * 获取线程池服务
     * @param size
     * @return
     */
    public static  synchronized ExecutorService getSingletonPool(int size,int blockSize,MonitorThread monitorThread){

        int  cpuCore = Runtime.getRuntime().availableProcessors();
        if (pool != null && !pool.isTerminated()){
            return pool;
        }
        //最好不要超过能超过cpu核数，会引起发热和 cpu交换。
        if (cpuCore!=0 && size>cpuCore){
            size = cpuCore;
        }

        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>(blockSize);


        //空线程保活 10s
        MyThreadPoolExecutor myThreadPoolExecutor =  new MyThreadPoolExecutor(1, size, 10000, TimeUnit.SECONDS, workQueue,
                new MyThreadFactory(), (r, executor) -> {
                    try {
                        //不抛弃，不放弃
                        workQueue.put(r);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
        pool = myThreadPoolExecutor;
        myThreadPoolExecutor.setMonitorThread(monitorThread);
        return  pool;

    }

    public static  synchronized ExecutorService getPool(){

        if (pool!=null){
            return pool;
        }

        return getSingletonPool(8, 16, new MonitorThread() {

        });

    }


    /**
     * 获取多例线程池 用完 shutdown
     * @param size
     * @param blockSize
     * @return
     */
    public static  synchronized ExecutorService getPrototypePool(int size,int blockSize,MonitorThread monitorThread){

        int  cpuCore = Runtime.getRuntime().availableProcessors();
        //最好不要超过能超过cpu核数，会引起发热和 cpu交换。
        if (cpuCore!=0 && size>cpuCore){
            size = cpuCore;
        }

        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>(blockSize);
        //空线程保活 10s
        MyThreadPoolExecutor prototype =  new MyThreadPoolExecutor(1, size, 10000, TimeUnit.SECONDS, workQueue,
                new MyThreadFactory(), (r, executor) -> {
            try {
                //不抛弃，不放弃
                workQueue.put(r);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        prototype.setMonitorThread(monitorThread);

        return  prototype;

    }
}
