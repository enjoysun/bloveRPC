package Blove.Netty.Client;

/*
 * @Time    : 2019/7/19 2:42 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MessageSendProxy.java
 * @Software: IntelliJ IDEA
 */

import Blove.Core.MessageCallback;
import Blove.Model.MsgRequest;
import Blove.Netty.Channel.MessageSendHandler;
import Blove.Netty.RPCServerLoader;

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
        // 构造请求消息体
        MsgRequest request = new MsgRequest();
        request.setMessageId(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setTypeParamaters(method.getParameterTypes());
        request.setValParameters(args);

        // 执行
        method.invoke(interFace, args);

        MessageSendHandler handler = RPCServerLoader.getInstance().getMessageSendHandler();
        MessageCallback callback = handler.sendRequest(request);
        return callback.getResult();
    }
}
