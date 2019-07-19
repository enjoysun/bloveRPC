package Blove.Model;

import Blove.Packet.Enums.RpcBeginAndEndSignal;

import java.io.Serializable;

/*
 * @Time    : 2019/7/4 4:50 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MsgTail.java
 * @Software: IntelliJ IDEA
 */
public class MsgTail implements Serializable {
    private static final long serialVersionUID = 6822626944481046153L;
    private byte[] crc;
    private final static byte frameTail = RpcBeginAndEndSignal.END_FRAME.getCode();

    public byte[] getCrc() {
        return crc;
    }

    public void setCrc(byte[] crc) {
        this.crc = crc;
    }

    public byte getFrameTail() {
        return frameTail;
    }
}
