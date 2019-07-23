package Blove.Netty.Client;

import Blove.Netty.RPCServerLoader;
import Blove.Packet.Enums.RPCSerializerProtocol;

import java.lang.reflect.Proxy;

/*
 * @Time    : 2019/7/23 11:54 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MsgSendExecutor.java
 * @Software: IntelliJ IDEA
 */
public class MsgSendExecutor {
    private RPCServerLoader loader = RPCServerLoader.getInstance();

    public MsgSendExecutor(String serverAddress) {
        // 暂默认MARSHALLING_SERIALIZER
        loader.load(serverAddress, RPCSerializerProtocol.MARSHALLING_SERIALIZER);
    }

    public void close(){
        loader.unLoad();
    }

    public static <T> T execute(Class<T> rpcInterface){
        return (T) Proxy.newProxyInstance(
                rpcInterface.getClassLoader(),
                new Class<?>[]{rpcInterface},
                new MessageSendProxy<T>(rpcInterface)
        );
    }
}
