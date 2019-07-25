package Blove.Packet.model;

import Blove.Model.MsgHeader;
import Blove.Model.MsgResponse;
import Blove.Model.MsgTail;

import java.io.Serializable;

/*
 * @Time    : 2019/7/7 3:53 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : PacketResponseModel.java
 * @Software: IntelliJ IDEA
 */
public class PacketResponseModel implements Serializable {
    private static final long serialVersionUID = -1885189978653749518L;

    private MsgHeader header;
    private MsgResponse body;
    private MsgTail tail;

    public MsgHeader getHeader() {
        return header;
    }

    public void setHeader(MsgHeader header) {
        this.header = header;
    }

    public MsgResponse getBody() {
        return body;
    }

    public void setBody(MsgResponse body) {
        this.body = body;
    }

    public MsgTail getTail() {
        return tail;
    }

    public void setTail(MsgTail tail) {
        this.tail = tail;
    }
}
