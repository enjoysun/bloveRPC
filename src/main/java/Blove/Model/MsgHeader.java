package Blove.Model;

import Blove.Packet.Enums.RpcBeginAndEndSignal;

/*
 * @Time    : 2019/7/4 4:24 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MsgHeader.java
 * @Software: IntelliJ IDEA
 */
public class MsgHeader {
    private final static byte frameHeader = RpcBeginAndEndSignal.START_FRAME.getCode();
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
