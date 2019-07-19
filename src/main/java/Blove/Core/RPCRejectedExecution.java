package Blove.Core;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/*
 * @Time    : 2019/7/1 1:30 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : RPCRejectedExecution.java
 * @Software: IntelliJ IDEA
 */
public class RPCRejectedExecution implements RejectedExecutionHandler {

    /**
     * @Author myou<myoueva@gmail.com>
     * @Description 自定义rpc线程池饱和策略
     * @Date 1:30 PM 2019/7/1
     * @Param
     * @return
     **/
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("线程池"+executor.toString()+"满载处理-----");
        /**
         * @Author myoueva@gmail.com
         * @Description //TODO
         * 打算后期进行线程满载后处理当前线程任务到队列记录用于后续重新处理
         * @Date 2:38 PM 2019/7/19
         * @Param [r, executor]
         * @return void
         **/
        new ThreadPoolExecutor.DiscardOldestPolicy().rejectedExecution(r, executor);
    }
}
