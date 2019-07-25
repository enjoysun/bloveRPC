package Blove.Netty.Server;

import Blove.Codecs.Marshalling.MarshallingFactory;
import Blove.Codecs.MsgToMsg.MsgDecoder;
import Blove.Codecs.MsgToMsg.MsgEncoder;
import Blove.Netty.Channel.MessageRecvHandler;
import Blove.Netty.Channel.MessageSendHandler;
import Blove.Util.RPCSystemConfig;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/*
 * @Time    : 2019/7/24 3:16 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MessageRecvChannelInitializer.java
 * @Software: IntelliJ IDEA
 */
public class MessageRecvChannelInitializer extends ChannelInitializer<SocketChannel> {

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
    }
}
