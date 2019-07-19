package Blove.Core;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * @Time    : 2019/7/1 11:36 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : RPCLayerThreadFactory.java
 * @Software: IntelliJ IDEA
 */
public class RPCLayerThreadFactory implements ThreadFactory {
    /**
     * @Author myou<myoueva@gmail.com>
     * @Description 构建业务处理线程池工厂 用来防止业务逻辑处理阻塞netty的channel线程。
     * 借鉴NioEventLoop自定义Nio线程
     * 工厂生产定制型Thread(名称、优先级、是否为守护)
     * @Date 11:38 AM 2019/7/1
     * @Param [r]
     * @return java.lang.Thread
     **/
    private static final AtomicInteger threadNumber = new AtomicInteger(1);
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final String prefix;
    private final boolean isDaemon;
    private final ThreadGroup threadGroup;

    public RPCLayerThreadFactory() {
        this("rpc-pool-"+poolNumber.getAndIncrement(), false);
    }

    public RPCLayerThreadFactory(String prefix) {
        this(prefix, false);
    }

    public RPCLayerThreadFactory(String prefix, boolean isDaemon) {
        this.prefix = prefix + "-thread-";
        this.isDaemon = isDaemon;
        threadGroup = (System.getSecurityManager() == null) ? Thread.currentThread().getThreadGroup() : System.getSecurityManager().getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        // 定制化一个线程实例
        Thread thread = new Thread(threadGroup, r, prefix + threadNumber.getAndIncrement(), 0);
        thread.setDaemon(isDaemon);
        return thread;
    }
}
