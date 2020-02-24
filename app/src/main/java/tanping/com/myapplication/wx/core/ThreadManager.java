package tanping.com.myapplication.wx.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadManager {

    private  static ExecutorService pool ;

    /**
     *
     * blockSize 越大越好，不会阻塞运行
     * 获取线程池服务
     * @param size
     * @return
     */
    public static  synchronized ExecutorService getPool(int size,int blockSize){

        int  cpuCore = Runtime.getRuntime().availableProcessors();
        if (pool != null){
            return pool;
        }
        //最好不要超过能超过cpu核数，会引起发热和 cpu交换。
        if (cpuCore!=0 && size>cpuCore){
            size = cpuCore;
        }

        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>(blockSize);
        //空线程保活 10s
        ExecutorService pool =  new ThreadPoolExecutor(1, size, 10000, TimeUnit.SECONDS, workQueue,
                new MyThreadFactory(), (r, executor) -> {
                    try {
                        //不抛弃，不放弃
                        workQueue.put(r);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });


        return  pool;

    }

}
