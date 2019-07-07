package Blove.Packet.model;

import Blove.Model.MsgHeader;
import Blove.Model.MsgTail;
import Blove.Packet.Enums.RpcBeginAndEndSignal;
import Blove.Util.CRCUtil;

import java.nio.ByteBuffer;

/*
 * @Time    : 2019/6/29 6:29 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : PacketModel.java
 * @Software: IntelliJ IDEA
 */


/**
 * @Author myou<myoueva @ gmail.com>
 * @Description PacketModel:构建一帧的具体结构体
 * --------------------------------------------------------------------------------------------
 * |    header                                              |   body       |  tail            |
 * --------------------------------------------------------------------------------------------
 * |frame-header|frame-type|frame-acquire-code|packet-length|     Data     |crcCode|frame-tail|
 * @Date 8:53 PM 2019/6/30
 * @Param
 * @return
 **/
public class PacketModel {
    public PacketModel(MsgHeader header, byte[] body, MsgTail tail) {
        this.header = header;
        this.body = body;
        this.tail = tail;
    }

    private MsgHeader header;
    private byte[] body;
    private MsgTail tail;

    public MsgHeader getHeader() {
        return header;
    }

    public void setHeader(MsgHeader header) {
        this.header = header;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public MsgTail getTail() {
        return tail;
    }

    public void setTail(MsgTail tail) {
        this.tail = tail;
    }
}
