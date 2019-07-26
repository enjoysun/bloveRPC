package Blove.Netty.Client;

import Blove.Codecs.Marshalling.MarshallingFactory;
import Blove.Codecs.MsgToMsg.MsgDecoder;
import Blove.Codecs.MsgToMsg.MsgEncoder;
import Blove.Netty.Channel.MessageSendHandler;
import Blove.Util.RPCSystemConfig;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/*
 * @Time    : 2019/7/4 5:11 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MessageSendChannelInitializer.java
 * @Software: IntelliJ IDEA
 */
public class MessageSendChannelInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * @Author myou<myoueva@gmail.com>
     * @Description //RPC客户端管道初始化
     * 做到可配置化添加pipline组件
     * @Date 5:12 PM 2019/7/4
     * @Param
     * @return
     **/

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(
                RPCSystemConfig.FRAME_LENGTH,
                0,
                RPCSystemConfig.MESSAGE_LENGTH,
                0,
                RPCSystemConfig.MESSAGE_LENGTH
        ));
        socketChannel.pipeline().addLast(new LengthFieldPrepender(RPCSystemConfig.MESSAGE_LENGTH));
        socketChannel.pipeline().addLast(MarshallingFactory.buildMarshallingDecoder());
        socketChannel.pipeline().addLast(MarshallingFactory.buildMarshallingEncoder());
        socketChannel.pipeline().addLast(new MessageSendHandler());
    }

}
