package Blove.Packet.model;

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
    private final byte frameHeader = RpcBeginAndEndSignal.START_FRAME.getCode();
    private final byte frameTail = RpcBeginAndEndSignal.END_FRAME.getCode();
    private byte frameType;
    private int acquireCode;
    private int packetLength;

    public void setPacketData(byte[] packetData) {
        this.packetData = packetData;
    }

    private byte[] packetData;

    // 转义规则字段
    private final byte REVISE_CODE = (byte) 0xAD; // 转义码
    private final byte REVISE_HEAD = (byte) 0X00; // 头部
    private final byte REVISE_TAIL = (byte) 0x01; // 尾部
    private final byte REVISE_SELF = (byte) 0x02; // 自身

    public PacketModel(byte frameType, int acquireCode, byte[] packetData) {
        this(frameType, acquireCode, 0, packetData);
    }

    public PacketModel(byte frameType, int acquireCode, int packetLength, byte[] packetData) {
        this.frameType = frameType;
        this.acquireCode = acquireCode;
        this.packetLength = packetLength;
        this.packetData = packetData;
    }

    /**
     * @return byte[]
     * @Author myou<myoueva               @               gmail.com>
     * @Description 转移内容字节(若是内容部分有和头尾字节重复则转义为指定的字节 ， 注意只转义内容部分)
     * 0xC0 -> 0xAD 0x00
     * 0xDE -> 0xAD 0x01
     * 0xAD -> 0xAD 0x02
     * @Date 6:59 PM 2019/6/29
     * @Param： body:进过编解码框架处理后的内容部分
     **/
    public byte[] revise(byte[] body) {
        ByteBuffer buffer = ByteBuffer.allocate(body.length * 2); // 初始化一个2倍body的bytebuffer
        for (byte item : body) { // 进行内容区域字节转义防止误读
            if (item == frameHeader) {
                buffer.put(REVISE_CODE).put(REVISE_HEAD);
            } else if (item == frameTail) {
                buffer.put(REVISE_CODE).put(REVISE_TAIL);
            } else if (item == REVISE_CODE) {
                buffer.put(REVISE_CODE).put(REVISE_SELF);
            } else {
                buffer.put(item);
            }
        }
        byte[] result = new byte[buffer.position()]; // position拿到当前写模式下buyebuffer最新可写下标用于赋值byte数组长度避免remain全部长度
        buffer.flip(); // 将position置为0，转换为读模式
        buffer.get(result); // 将bytebuffer内容赋值到byte数组中
        return result;
    }


    /**
     * @return byte[]
     * @Author myou<myoueva       @       gmail.com>
     * @Description 还原内容部分转义字段
     * @Date 7:37 PM 2019/6/29
     * @Param [body]
     **/
    public byte[] resvert(byte[] body) {
        ByteBuffer buffer = ByteBuffer.allocate(body.length);
        for (int i = 0; i < body.length; i++) {
            if (body[i] == REVISE_CODE) {
                i++;
                if (body[i] == REVISE_HEAD) {
                    buffer.put(frameHeader);
                } else if (body[i] == REVISE_TAIL) {
                    buffer.put(frameTail);
                } else if (body[i] == REVISE_SELF) {
                    buffer.put(REVISE_CODE);
                } else {
                    throw new RuntimeException("转义还原失败，存在规则外的解析字节");
                }
            } else {
                buffer.put(body[i]);
            }
        }
        byte[] bytes = new byte[buffer.position()];
        buffer.flip();
        buffer.get(bytes);
        return bytes;
    }

    /**
     * @return byte[]
     * @Author myou<myoueva   @   gmail.com>
     * @Description 构建完整数据包(含crc自动生成构建)
     * @Date 8:23 PM 2019/6/29
     * @Param []
     **/
    public byte[] getPacket() {
        int len = 0;
        if (packetData != null)
            len = packetData.length;
        ByteBuffer crcBuffer = ByteBuffer.allocate(len + 11); //去除头帧和尾帧的长度
        crcBuffer.put(frameType);
        crcBuffer.putInt(acquireCode);
        crcBuffer.putInt(packetData.length);
        crcBuffer.put(packetData);
        // 添加帧中的crc
        byte[] bytes = new byte[crcBuffer.position()];
        crcBuffer.flip();
        crcBuffer.mark();
        crcBuffer.get(bytes);
        byte[] crc = CRCUtil.getCRC(bytes).getBytes();
        crcBuffer.reset();
        crcBuffer.compact();
        crcBuffer.put(crc);
        byte[] result = revise((byte[]) crcBuffer.flip().array()); //数据转义
        // 构造完整数据包
        ByteBuffer frame = ByteBuffer.allocate(result.length + 2);
        frame.put(frameHeader);
        frame.put(result);
        frame.put(frameTail);
        return (byte[]) frame.flip().array();
    }


}
