package Blove.Model;

import Blove.Packet.Enums.RpcBeginAndEndSignal;

import java.io.Serializable;

/*
 * @Time    : 2019/7/4 4:24 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MsgHeader.java
 * @Software: IntelliJ IDEA
 */
public class MsgHeader implements Serializable {
    private final static byte frameHeader = RpcBeginAndEndSignal.START_FRAME.getCode();
    private static final long serialVersionUID = 8621388291212035113L;
    private byte frameType;
    private int acquireCode;
    private int packetLength;

    public static byte getFrameHeader() {
        return frameHeader;
    }

    public byte getFrameType() {
        return frameType;
    }

    public void setFrameType(byte frameType) {
        this.frameType = frameType;
    }

    public int getAcquireCode() {
        return acquireCode;
    }

    public void setAcquireCode(int acquireCode) {
        this.acquireCode = acquireCode;
    }

    public int getPacketLength() {
        return packetLength;
    }

    public void setPacketLength(int packetLength) {
        this.packetLength = packetLength;
    }
}
