package Blove.Codecs.Marshalling.CodeDecoder;

import Blove.Codecs.Marshalling.MarshallingFactory;
import Blove.Model.MsgHeader;
import Blove.Model.MsgRequest;
import Blove.Model.MsgTail;
import Blove.Model.impl.StructImpl;
import Blove.Packet.Enums.RpcFpsType;
import Blove.Packet.model.PacketRequestModel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.UUID;

/*
 * @Time    : 2019/7/7 4:31 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MarshallingEncoder.java
 * @Software: IntelliJ IDEA
 */
public class MarshallingEncoder {
    public static void main(String[] args) {
        new Client().connect("localhost", 8083);
    }
}

class Client {
    public void connect(String host, int port) {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(
                        MarshallingFactory.marshallingDecoder());
                ch.pipeline().addLast(
                        MarshallingFactory.marshallingEncoder());
                ch.pipeline().addLast(new ClientHandler());
            }
        });

        // 发起异步链接操作
        ChannelFuture future;
        try {
            future = bootstrap.connect(host, port).sync();
            // 等待客户端链路关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
        }
    }
}

class ClientHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println(cause.getMessage());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        MsgRequest request = new MsgRequest();
        request.setClassName("MyMath");
        request.setMessageId(UUID.randomUUID().toString());
        request.setMethodName("add");
        request.setTypeParamaters(new Class[]{Object.class});
        request.setValParameters(new Object[]{new Integer(1)});
        MsgHeader header = new MsgHeader();
        header.setAcquireCode(1234);
        header.setFrameType(RpcFpsType.NORMAL_TYPE.getCode());
        header.setPacketLength(100);
        MsgTail tail = new MsgTail();
        tail.setCrc("2233".getBytes());
        PacketRequestModel model = new PacketRequestModel();
        model.setHeader(header);
        model.setBody(request);
        model.setTail(tail);
        ctx.writeAndFlush(model);
    }
}
