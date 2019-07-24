package Blove.Netty.Server;

import Blove.Codecs.MsgToMsg.MsgDecoder;
import Blove.Codecs.MsgToMsg.MsgEncoder;
import Blove.Netty.Channel.MessageSendHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/*
 * @Time    : 2019/7/24 3:16 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MessageRecvChannelInitializer.java
 * @Software: IntelliJ IDEA
 */
public class MessageRecvChannelInitializer extends ChannelInitializer<SocketChannel> {
    private static final int MESSAGE_LENGTH = 4;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new MsgDecoder(
                Integer.MAX_VALUE,
                0,
                MessageRecvChannelInitializer.MESSAGE_LENGTH));
        /**
         * 编解码组件祖册
         * 服务端channel管道注册
         */
        socketChannel.pipeline().addLast(new MsgEncoder());
        socketChannel.pipeline().addLast(new MessageSendHandler());
    }
}
