package Blove.Model;

import Blove.Packet.Enums.RpcBeginAndEndSignal;

/*
 * @Time    : 2019/7/4 4:50 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MsgTail.java
 * @Software: IntelliJ IDEA
 */
public class MsgTail {
    private byte[] crc;
    private final static byte frameTail = RpcBeginAndEndSignal.END_FRAME.getCode();

    public byte[] getCrc() {
        return crc;
    }

    public void setCrc(byte[] crc) {
        this.crc = crc;
    }

    public static byte getFrameTail() {
        return frameTail;
    }
}
