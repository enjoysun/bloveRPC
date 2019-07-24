package Blove.Netty.Server.Simulate.Service;

import Blove.Core.MessageCallback;
import Blove.Model.MsgResponse;
import Blove.Netty.Channel.MessageSendHandler;
import Blove.Netty.RPCServerLoader;
import Blove.Netty.Server.Simulate.Interface.Calc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

/*
 * @Time    : 2019/7/23 5:59 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : RPCProxy.java
 * @Software: IntelliJ IDEA
 */
public class RPCProxy<T> implements InvocationHandler {

    private Class<T> t;

    public RPCProxy(Class<T> t) {
        t = t;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        // 执行结果
        Object result = null;
        MsgResponse response = new MsgResponse();
        response.setMessageId(UUID.randomUUID().toString());
        try {
            // 构造response
            result = method.invoke(t, args);
            response.setResult(result);
            response.setError("");
        } catch (IllegalAccessException e) {
            response.setError(e.getCause().getMessage());
        } catch (InvocationTargetException e) {
            response.setError(e.getCause().getMessage());
        }

//        MessageSendHandler handler = RPCServerLoader.getInstance().getMessageSendHandler();
//        MessageCallback callback = handler.sendRequest();
//        return callback.getResult();
        return response;
    }
}
