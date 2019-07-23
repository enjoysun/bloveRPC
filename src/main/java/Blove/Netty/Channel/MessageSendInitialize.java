package Blove.Netty.Channel;

import Blove.Codecs.MsgToMsg.MsgDecoder;
import Blove.Codecs.MsgToMsg.MsgEncoder;
import Blove.Netty.Handler.MessageSendHandler;
import Blove.Util.RPCSystemConfig;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/*
 * @Time    : 2019/7/2 11:41 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MessageSendInitialize.java
 * @Software: IntelliJ IDEA
 */
public class MessageSendInitialize extends ChannelInitializer<SocketChannel> {
    /**
     * @Author myoueva@gmail.com
     * @Description //client的handler实现
     * @Date 11:46 AM 2019/7/23
     * @Param [socketChannel]
     * @return void
     **/
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast("code-decoder", new MsgDecoder(
                RPCSystemConfig.FRAME_LENGTH,
                RPCSystemConfig.MESSAGE_LENGTH,
                RPCSystemConfig.MESSAGE_LENGTH
                ));
        socketChannel.pipeline().addLast("code-encoder", new MsgEncoder());
        socketChannel.pipeline().addLast("client-handler", new MessageSendHandler());
    }
}
