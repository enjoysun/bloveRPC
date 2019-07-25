package Blove.Codecs.Marshalling.CodeDecoder;

import Blove.Codecs.Marshalling.MarshallingFactory;
import Blove.Packet.model.PacketRequestModel;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/*
 * @Time    : 2019/7/7 4:16 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MarshallingDecoder.java
 * @Software: IntelliJ IDEA
 */
public class MarshallingDecoder {
    public static void main(String[] args) {
        new Server().start(8083);
    }
}

class Server{
    public void start(int port){
        NioEventLoopGroup bossGroup=new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrapServer = new ServerBootstrap();
        bootstrapServer.group(bossGroup,workerGroup)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
//                        socketChannel.pipeline().addLast("decoder", MarshallingFactory.marshallingDecoder());
//                        socketChannel.pipeline().addLast("encoder", MarshallingFactory.marshallingEncoder());
                        socketChannel.pipeline().addLast(new TestHandler());
                    }
                });
        try {
            ChannelFuture channelFuture =bootstrapServer.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

class TestHandler extends ChannelHandlerAdapter{

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println(cause.getMessage());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PacketRequestModel model = (PacketRequestModel)msg;
        System.out.println(model.getHeader().toString());
        System.out.println(model.getBody().toString());
        System.out.println(model.getTail().toString());
    }
}