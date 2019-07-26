package Blove.Netty.Client;

/*
 * @Time    : 2019/7/19 2:42 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MessageSendProxy.java
 * @Software: IntelliJ IDEA
 */

import Blove.Core.Blogger;
import Blove.Core.MessageCallback;
import Blove.Model.MsgHeader;
import Blove.Model.MsgRequest;
import Blove.Model.MsgTail;
import Blove.Netty.Channel.MessageSendHandler;
import Blove.Netty.RPCServerLoader;
import Blove.Packet.Enums.RpcFpsType;
import Blove.Packet.model.PacketRequestModel;
import Blove.Util.CRCUtil;
import Blove.Util.Common;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/***
 * @Author myoueva@gmail.com
 * @Description //TODO
 * 客户端消息处理类
 * @Date 2:42 PM 2019/7/19
 * @Param
 * @return
 **/
public class MessageSendProxy<T> implements InvocationHandler {

    private Class<T> interFace;
    public MessageSendProxy(Class<T> tClass) {
        interFace = tClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 构造body
        MsgRequest request = new MsgRequest();
        request.setMessageId(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setTypeParamaters(method.getParameterTypes());
        request.setValParameters(args);

        PacketRequestModel model = new PacketRequestModel();

        MsgHeader header = new MsgHeader();
        header.setFrameType(RpcFpsType.NORMAL_TYPE.getCode());
        header.setAcquireCode(Common.getAcquireCode(request.getMessageId()));

        MsgTail tail = new MsgTail();
        tail.setCrc(CRCUtil.crcCode(header.getAcquireCode(), request.toByteArray()));

        model.setHeader(header);
        model.setBody(request);
        model.setTail(tail);
        Blogger.loggerFactory().info(request.getClassName()+"=="+request.getMethodName());
        MessageSendHandler handler = RPCServerLoader.getInstance().getMessageSendHandler();
        MessageCallback callback = handler.sendRequest(model);
        Blogger.loggerFactory().info("发送request");
        return callback.getResult();
    }
}
