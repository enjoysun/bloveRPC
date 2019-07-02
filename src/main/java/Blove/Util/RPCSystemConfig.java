package Blove.Util;

/*
 * @Time    : 2019/7/1 10:59 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : RPCSystemConfig.java
 * @Software: IntelliJ IDEA
 */
public class RPCSystemConfig {
    /**
     * @Author myou<myoueva@gmail.com>
     * @Description //RPC项目公共配置参数模块类
     * @Date 10:59 PM 2019/7/1
     * @Param
     * @return
     **/
    public static final String DELMITER = ":";
    // 核心数量
    public static final int SYSTEM_KERNEL = Math.max(4, Runtime.getRuntime().availableProcessors() * 2);
    // 活性时间(最大值建议配置文件中产生)
    public static final int KEEP_ALIVE_TIME = Math.max(0, 2);
    // 默认线程池数量
    public static final int THREAD_POOL_THREAD_COUNT = Integer.getInteger("thread.pool.default.thread.capacity", SYSTEM_KERNEL);
    // 默认线程池任务队列可容纳元素(为0时：SynchronousQueue类型队列 小于0时: LinkedBlockingQueue)
    public static final int THREAD_POOL_QUEUE_ELEMENT = Integer.getInteger("thread.pool.default.queue.element", -1);
    // 全局业务计算等待时间
    public static final int AWAIT_TIME = 10*1000;
}
