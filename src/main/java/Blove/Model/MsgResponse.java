package Blove.Model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/*
 * @Time    : 2019/6/28 4:29 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MsgResponse.java
 * @Software: IntelliJ IDEA
 */
public class MsgResponse implements Serializable {
    private static final long serialVersionUID = -5205347809467363759L;
    /**
     * @Author myou<myoueva @ gmail.com>
     * @Description rpc通信体输出结构类
     * @Date 4:29 PM 2019/6/28
     * @Param
     * @return
     **/
    private String messageId;
    private String error;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toStringExclude(this, new String[]{"result"});
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    private Object result;
}
