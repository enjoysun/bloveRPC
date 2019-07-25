package Blove.Core;

import Blove.Exception.InvokeModuleException;
import Blove.Model.MsgRequest;
import Blove.Model.MsgResponse;
import Blove.Packet.model.PacketRequestModel;
import Blove.Packet.model.PacketResponseModel;
import Blove.Util.RPCSystemConfig;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * @Time    : 2019/7/2 5:07 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MessageCallback.java
 * @Software: IntelliJ IDEA
 */
public class MessageCallback {
    /**
     * @Author myou<myoueva@gmail.com>
     * @Description // RPC执行结果结构体处理
     * @Date 5:25 PM 2019/7/2
     * @Param
     * @return
     **/
    private PacketResponseModel response;
    private PacketRequestModel request;
    private static final Lock lock = new ReentrantLock();
    private Condition finish = lock.newCondition();
    public MessageCallback(){}

    public MessageCallback(PacketRequestModel request) {
        this.request = request;
    }

    public Object getResult() throws InterruptedException {
        // 返回rpc构造完毕的response构造体信息（多线程处理）
        try {
            lock.lock();
            finish.await(RPCSystemConfig.AWAIT_TIME, TimeUnit.MILLISECONDS);
            if (this.response != null) {
                if (this.response.getBody().getError().isEmpty()) {
                    return this.response.getBody().getResult();
                } else {
                    throw new InvokeModuleException(this.response.getBody().getError());
                }
            } else {
                return null;
            }
        } finally {
            lock.unlock();
        }
    }

    public void putResponse(PacketResponseModel response) {
        try {
            lock.lock();
            finish.signal();
            this.response = response;
        } finally {
            lock.unlock();
        }
    }
}
