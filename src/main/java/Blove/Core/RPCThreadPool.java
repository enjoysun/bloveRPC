package Blove.Core;

import Blove.Util.RPCSystemConfig;

import java.util.concurrent.*;

/*
 * @Time    : 2019/7/1 1:08 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : RPCThreadPool.java
 * @Software: IntelliJ IDEA
 */
public class RPCThreadPool {
    /**
     * @Author myou<myoueva@gmail.com>
     * @Description 自定义业务处理线程池，防止netty的handler处理复杂业务逻辑阻塞影响io返回
     * @Date 1:08 PM 2019/7/1
     * @Param
     * @return
     **/
    public static ExecutorService getExecutor(int threads, int queues){
        return new ThreadPoolExecutor(
                threads,
                RPCSystemConfig.SYSTEM_KERNEL,
                RPCSystemConfig.KEEP_ALIVE_TIME,
                TimeUnit.MILLISECONDS,
                queues==0?new SynchronousQueue<Runnable>():(queues<0?new LinkedBlockingQueue<Runnable>():new LinkedBlockingQueue<Runnable>(queues)),
                new RPCLayerThreadFactory("RPCPool", true),
                new RPCRejectedExecution()
                );
    }
}
