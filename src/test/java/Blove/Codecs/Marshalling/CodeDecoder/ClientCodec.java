package Blove.Codecs.Marshalling.CodeDecoder;

import Blove.Codecs.Marshalling.MarshallingFactory;
import Blove.Codecs.MsgToMsg.MsgDecoder;
import Blove.Codecs.MsgToMsg.MsgEncoder;
import Blove.Model.MsgHeader;
import Blove.Model.MsgRequest;
import Blove.Model.MsgTail;
import Blove.Packet.Enums.RpcFpsType;
import Blove.Packet.model.PacketRequestModel;
import Blove.Packet.model.PacketResponseModel;
import Blove.Util.CRCUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.util.UUID;

/*
 * @Time    : 2019/7/24 5:25 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : ClientCodec.java
 * @Software: IntelliJ IDEA
 */
public class ClientCodec {
    public static void main(String[] args) {
        new A().start("localhost", 8086);
    }
}

class A {
    public void start(String address, int port) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(
                               1024<<1,
                               0,
                               4,
                               0,
                               4
                            ));
                            socketChannel.pipeline().addLast(new LengthFieldPrepender(4));
                            socketChannel.pipeline().addLast(MarshallingFactory.buildMarshallingDecoder());
                            socketChannel.pipeline().addLast(MarshallingFactory.buildMarshallingEncoder());
                            socketChannel.pipeline().addLast(new ClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(address, port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    private class ClientHandler extends ChannelHandlerAdapter {

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
            System.out.println("连接异常:" + cause.getMessage());
            super.exceptionCaught(ctx, cause);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            PacketRequestModel model = new PacketRequestModel();

            MsgRequest request = new MsgRequest();
            request.setMessageId(UUID.randomUUID().toString());
            request.setValParameters(new Object[]{2, 3});
            request.setTypeParamaters(new Class<?>[]{int.class, int.class});
            request.setMethodName("add");
            request.setClassName("calc");

            MsgHeader header = new MsgHeader();
            header.setFrameType(RpcFpsType.NORMAL_TYPE.getCode());
            header.setAcquireCode(4396);

            MsgTail tail = new MsgTail();
            tail.setCrc(CRCUtil.crcCode(header.getAcquireCode(), request.toByteArray()));

            model.setHeader(header);
            model.setBody(request);
            model.setTail(tail);
            ctx.writeAndFlush(model);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            PacketResponseModel responseModel = (PacketResponseModel) msg;
            System.out.println("received:" + responseModel);
        }
    }
}
