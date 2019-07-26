package Blove.Netty.Channel;

import Blove.Core.Blogger;
import Blove.Model.MsgRequest;
import Blove.Model.MsgResponse;
import Blove.Netty.Server.MessageRecvExecutor;
import Blove.Netty.Server.MessageRecvInitializeTask;
import Blove.Packet.model.PacketRequestModel;
import Blove.Util.Common;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

/*
 * @Time    : 2019/7/24 3:21 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MessageRecvHandler.java
 * @Software: IntelliJ IDEA
 */
public class MessageRecvHandler extends ChannelHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Blogger.loggerFactory().info("链路处理错误即将关闭:"+cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PacketRequestModel msgRequest = (PacketRequestModel) msg;
        boolean flag=Common.checkCrc(msgRequest.getHeader().getAcquireCode(), msgRequest.getBody().toByteArray(), msgRequest.getTail().getCrc());
        if (!flag){
            Blogger.loggerFactory().info("crc校验失败，该链路即将关闭");
            ctx.close();
        }
        MsgResponse response = new MsgResponse();
        MessageRecvExecutor executor = MessageRecvExecutor.getExecutor();
        MessageRecvInitializeTask task = new MessageRecvInitializeTask(
                msgRequest, ctx, response, executor);
        executor.submit(task);
    }
}
