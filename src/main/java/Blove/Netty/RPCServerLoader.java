package Blove.Netty;

import Blove.Core.RPCThreadPool;
import Blove.Netty.Channel.MessageSendHandler;
import Blove.Netty.Client.MessageSendInitializeTask;
import Blove.Packet.Enums.RPCSerializerProtocol;
import Blove.Util.RPCSystemConfig;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * @Time    : 2019/7/2 9:30 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : RPCServerLoader.java
 * @Software: IntelliJ IDEA
 */
public class RPCServerLoader {
    /**
     * @Author myou<myoueva@gmail.com>
     * @Description //rpc的服务端处理类(加载反射代理)
     * @Date 9:40 AM 2019/7/2
     * @Param
     * @return
     **/
    private static int threadNum = RPCSystemConfig.THREAD_POOL_THREAD_COUNT;
    private static int queueNum = RPCSystemConfig.THREAD_POOL_QUEUE_ELEMENT;
    // 客户端消息处理池实例EventLoop
    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup(RPCSystemConfig.KEEP_ALIVE_TIME);
    private static Lock lock = new ReentrantLock();
    private Condition handlerStatus = lock.newCondition();
    private RPCSerializerProtocol serializerProtocol = RPCSerializerProtocol.JDK_SERIALIZER;
    private static final ExecutorService threadpool = RPCThreadPool.getExecutor(threadNum, queueNum);
    private MessageSendHandler messageSendHandler;

    public RPCServerLoader() {
    }

    public static RPCServerLoader getInstance(){
        return RPCServerLoaderFactory.getRpcServerLoader();
    }

    /**
     * @Author myou<myoueva@gmail.com>
     * @Description //DCL延迟初始化替代双锁模式并发(DCL适用于静态字段，双锁检测可以初始化实例字段)
     * 内部静态内声明静态变量
     * 在静态类中发布静态已赋值的变量
     * @Date 10:46 AM 2019/7/2
     * @Param
     * @return
     **/
    private static class RPCServerLoaderFactory{
        private static volatile RPCServerLoader rpcServerLoader=new RPCServerLoader();
        public static RPCServerLoader getRpcServerLoader(){
            return RPCServerLoaderFactory.rpcServerLoader;
        }
    }

    public void load(String serverAddress, RPCSerializerProtocol serializerProtocol){
        String[] address = serverAddress.split(RPCSystemConfig.DELMITER);
        if (address.length==2){
            String host = address[0];
            int port = Integer.valueOf(address[1]);
            final InetSocketAddress remoteAddr = new InetSocketAddress(host, port);
            threadpool.submit(new MessageSendInitializeTask(eventLoopGroup, remoteAddr, this));
        }
    }

    public MessageSendHandler getMessageSendHandler() throws InterruptedException {
        try {
            lock.lock();
            while (messageSendHandler == null){
                handlerStatus.await();
            }
            return messageSendHandler;
        }finally {
            lock.unlock();
        }
    }

    public void setMessageSendHandler(MessageSendHandler messageSendHandler){
        try {
            lock.lock();
            this.messageSendHandler = messageSendHandler;
            handlerStatus.signal();
        }finally {
            lock.unlock();
        }
    }

    public void unLoad(){
        messageSendHandler.close();
        threadpool.shutdown();
        eventLoopGroup.shutdownGracefully();
    }

    public void setSerializerProtocol(RPCSerializerProtocol serializerProtocol){
        this.serializerProtocol = serializerProtocol;
    }
}
