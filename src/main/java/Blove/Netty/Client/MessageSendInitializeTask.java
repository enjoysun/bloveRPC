package Blove.Netty.Client;

import Blove.Netty.Channel.MessageSendInitialize;
import Blove.Netty.Handler.MessageSendHandler;
import Blove.Netty.RPCServerLoader;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/*
 * @Time    : 2019/7/2 11:35 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MessageSendInitializeTask.java
 * @Software: IntelliJ IDEA
 */
public class MessageSendInitializeTask implements Runnable {
    private EventLoopGroup eventLoopGroup;
    private InetSocketAddress inetSocketAddress;
    private RPCServerLoader serverLoader;

    public MessageSendInitializeTask(EventLoopGroup eventLoopGroup, InetSocketAddress inetSocketAddress, RPCServerLoader serverLoader) {
        this.eventLoopGroup = eventLoopGroup;
        this.inetSocketAddress = inetSocketAddress;
        this.serverLoader = serverLoader;
    }

    @Override
    public void run() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new MessageSendInitialize());

        ChannelFuture channelFuture=bootstrap.connect(inetSocketAddress);
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()){
                    // 客户端连接成功
                    MessageSendHandler handler= channelFuture.channel().pipeline().get(MessageSendHandler.class);
                    RPCServerLoader.getInstance().setMessageSendHandler(handler);
                }
            }
        });
    }
}
