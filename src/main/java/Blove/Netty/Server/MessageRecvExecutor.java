package Blove.Netty.Server;

import Blove.Core.Blogger;
import Blove.Core.RPCLayerThreadFactory;
import Blove.Core.RPCThreadPool;
import Blove.Model.MsgMapping;
import Blove.Model.MsgRequest;
import Blove.Netty.Server.Simulate.Interface.Calc;
import Blove.Netty.Server.Simulate.Service.RPCProxy;
import Blove.Util.RPCSystemConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/*
 * @Time    : 2019/7/23 5:33 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MessageRecvExecutor.java
 * @Software: IntelliJ IDEA
 */
public class MessageRecvExecutor {

    private static Map<String, List<String>> container = new HashMap<>(); //模拟Spring配置接口与实现类关系映射

    private static Map<String, Class<?>> map = new ConcurrentHashMap<>();
    private volatile static ExecutorService threadPool = RPCThreadPool.getExecutor(
            RPCSystemConfig.THREAD_POOL_THREAD_COUNT,
            RPCSystemConfig.THREAD_POOL_QUEUE_ELEMENT
    );

    private volatile static MessageRecvExecutor executor;

    static {
        container.put("Blove.Netty.Server.Simulate.Interface.Calc", Arrays.asList("Blove.Netty.Server.Simulate.Service.RPCMath"));
        try {
            init();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static MessageRecvExecutor getExecutor(){
        if (executor==null){
            synchronized (MessageRecvExecutor.class){
                if (executor==null){
                    executor = new MessageRecvExecutor();
                }
            }
        }
        return executor;
    }
    /**
     * @return void
     * @Author myoueva@gmail.com
     * @Description //使用反射模拟spring容器将类加载到容器过程
     * @Date 10:30 AM 2019/7/24
     * @Param []
     **/
    public static void init() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Iterator<Map.Entry<String, List<String>>> iterator = container.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> next = iterator.next();
            for (String string : next.getValue()) {
                Class<?> object = Class.forName(string);
                map.put(next.getKey(), object);
            }
        }
    }

    /**
     * @return java.lang.Object
     * @Author myoueva@gmail.com
     * @Description //模拟反射调用容器中已加载实例
     * @Date 2:34 PM 2019/7/24
     * @Param [request]
     **/
    public Object reflect(MsgRequest request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        String className = request.getClassName();
        String methodName = request.getMethodName();
        Object[] valParameters = request.getValParameters();
        Class<?>[] typeParamaters = request.getTypeParamaters();
        Class<?> instance = MessageRecvExecutor.map.get(className);
        Method method = instance.getMethod(methodName, typeParamaters);
        Object object = method.invoke(instance.newInstance(), valParameters);
        return object;
    }

    public void submit(Runnable task) {
        threadPool.submit(task);
    }

    public void start() throws InterruptedException {
        Blogger.loggerFactory().info("开始启动");
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new MessageRecvChannelInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind("localhost", 8086).sync();
            channelFuture.channel().closeFuture().sync();
            Blogger.loggerFactory().info("Netty-Server-Start");
        }finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }
}
