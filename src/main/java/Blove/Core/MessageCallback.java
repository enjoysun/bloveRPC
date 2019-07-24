package Blove.Core;

import Blove.Exception.InvokeModuleException;
import Blove.Model.MsgRequest;
import Blove.Model.MsgResponse;
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
    private MsgResponse response;
    private MsgRequest request;
    private static final Lock lock = new ReentrantLock();
    private Condition finish = lock.newCondition();
    public MessageCallback(){}

    public MessageCallback(MsgRequest request) {
        this.request = request;
    }

    public Object getResult() throws InterruptedException {
        // 返回rpc构造完毕的response构造体信息（多线程处理）
        try {
            lock.lock();
            finish.await(RPCSystemConfig.AWAIT_TIME, TimeUnit.MILLISECONDS);
            if (this.response != null) {
                if (this.response.getError().isEmpty()) {
                    return this.response.getResult();
                } else {
                    throw new InvokeModuleException(this.response.getError());
                }
            } else {
                return null;
            }
        } finally {
            lock.unlock();
        }
    }

    public void putResponse(MsgResponse response) {
        try {
            lock.lock();
            finish.signal();
            this.response = response;
        } finally {
            lock.unlock();
        }
    }
}
