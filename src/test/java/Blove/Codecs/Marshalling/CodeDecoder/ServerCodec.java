package Blove.Codecs.Marshalling.CodeDecoder;

import Blove.Codecs.Marshalling.MarshallingFactory;
import Blove.Codecs.MsgToMsg.MsgDecoder;
import Blove.Codecs.MsgToMsg.MsgEncoder;
import Blove.Model.MsgHeader;
import Blove.Model.MsgResponse;
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

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.UUID;

/*
 * @Time    : 2019/7/24 5:26 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : ServerCodec.java
 * @Software: IntelliJ IDEA
 */
public class ServerCodec {
    public static void main(String[] args) {
        new S().start(8086);
    }
}

class S{
    public void start(int port){
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
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
                            socketChannel.pipeline().addLast(new ServerHandler());
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ServerHandler extends ChannelHandlerAdapter{

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
            System.out.println("连接异常:"+cause.getMessage());
            super.exceptionCaught(ctx, cause);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("receive："+msg);
            PacketRequestModel requestModel = (PacketRequestModel) msg;
            PacketResponseModel responseModel=new PacketResponseModel();
            MsgResponse response=new MsgResponse();
            response.setMessageId(UUID.randomUUID().toString());
            response.setError("33");
            response.setResult("5");
            MsgHeader header = new MsgHeader();
            header.setFrameType(RpcFpsType.NORMAL_TYPE.getCode());
            header.setAcquireCode(4396);

            MsgTail tail = new MsgTail();
            tail.setCrc(CRCUtil.crcCode(header.getAcquireCode(), response.toByteArray()));
            responseModel.setHeader(header);
            responseModel.setBody(response);
            responseModel.setTail(tail);
            ctx.writeAndFlush(responseModel);
        }
    }
}
