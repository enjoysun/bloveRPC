package Blove.Netty.Server;

import Blove.Core.Blogger;
import Blove.Model.MsgHeader;
import Blove.Model.MsgRequest;
import Blove.Model.MsgResponse;
import Blove.Model.MsgTail;
import Blove.Packet.Enums.RpcFpsType;
import Blove.Packet.model.PacketRequestModel;
import Blove.Packet.model.PacketResponseModel;
import Blove.Util.CRCUtil;
import Blove.Util.RPCSystemConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.InvocationTargetException;

/*
 * @Time    : 2019/7/24 3:36 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MessageRecvInitializeTask.java
 * @Software: IntelliJ IDEA
 */
public class MessageRecvInitializeTask implements Runnable {
    private PacketRequestModel msgRequest;

    private ChannelHandlerContext channelHandlerContext;

    private MsgResponse msgResponse;

    private MessageRecvExecutor messageRecvExecutor;

    public MessageRecvInitializeTask(PacketRequestModel msgRequest,
                                     ChannelHandlerContext channelHandlerContext,
                                     MsgResponse response,
                                     MessageRecvExecutor executor) {
        this.msgRequest = msgRequest;
        this.channelHandlerContext = channelHandlerContext;
        this.msgResponse = response;
        this.messageRecvExecutor = executor;
    }

    @Override
    public void run() {
        msgResponse.setMessageId(msgRequest.getBody().getMessageId());
        try {
            msgResponse.setResult(messageRecvExecutor.reflect(msgRequest.getBody()));
        } catch (NoSuchMethodException e) {
            msgResponse.setError(e.getMessage());
        } catch (InvocationTargetException e) {
            msgResponse.setError(e.getMessage());
        } catch (IllegalAccessException e) {
            msgResponse.setError(e.getMessage());
        } catch (InstantiationException e) {
            msgResponse.setError(e.getMessage());
        }
        MsgHeader header = new MsgHeader();
        header.setFrameType(RpcFpsType.NORMAL_TYPE.getCode());
        header.setAcquireCode(4396);
        MsgTail tail = new MsgTail();
        tail.setCrc(CRCUtil.crcCode(header.getAcquireCode(), msgResponse.toByteArray()));
        PacketResponseModel responseModel = new PacketResponseModel(header, msgResponse, tail);
        channelHandlerContext.writeAndFlush(responseModel).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (!channelFuture.isSuccess()){
                    // 处理失败消息发送
                    Blogger.loggerFactory().info("消息处理失败:"+msgRequest.getBody().getMessageId());
                }else {
                    Blogger.loggerFactory().info("消息处理成功:"+msgRequest.getBody().getMessageId());
                }
            }
        });
    }
}
