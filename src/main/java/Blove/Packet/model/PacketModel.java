package Blove.Packet.model;

import Blove.Model.MsgHeader;
import Blove.Model.MsgTail;

import java.io.Serializable;

/*
 * @Time    : 2019/7/19 3:24 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : PacketModel.java
 * @Software: IntelliJ IDEA
 */
public class PacketModel implements Serializable {
    /**
     * @Author myoueva@gmail.com
     * @Description //TODO
     * rpc消息传输层消息体(传输层抽象体)
     * @Date 3:26 PM 2019/7/19
     * @Param
     * @return
     **/
    private static final long serialVersionUID = -4977991944852778488L;
    private MsgHeader header;
    private Object body;
    private MsgTail tail;


    public PacketModel(MsgHeader header, Object body, MsgTail tail) {
        this.header = header;
        this.body = body;
        this.tail = tail;
    }

    public MsgHeader getHeader() {
        return header;
    }

    public void setHeader(MsgHeader header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public MsgTail getTail() {
        return tail;
    }

    public void setTail(MsgTail tail) {
        this.tail = tail;
    }
}
