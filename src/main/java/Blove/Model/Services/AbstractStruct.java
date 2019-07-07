package Blove.Model.Services;

import Blove.Model.MsgHeader;
import Blove.Model.MsgTail;
import Blove.Model.impl.StructImpl;
import Blove.Packet.Enums.RpcFpsType;
import Blove.Util.CRCUtil;

import java.nio.ByteBuffer;

/*
 * @Time    : 2019/7/7 2:43 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : AbstractStruct.java
 * @Software: IntelliJ IDEA
 */
public class AbstractStruct implements StructImpl {
    // 转义规则字段
    private final byte REVISE_CODE = (byte) 0xAD; // 转义码
    private final byte REVISE_HEAD = (byte) 0X00; // 头部
    private final byte REVISE_TAIL = (byte) 0x01; // 尾部
    private final byte REVISE_SELF = (byte) 0x02; // 自身


    /**
     * @return byte[]
     * @Author myou<myoueva@gmail.com>
     * @Description 还原内容部分转义字段
     * @Date 7:37 PM 2019/6/29
     * @Param [body]
     **/
    @Override
    public byte[] revert(byte[] body) {
        ByteBuffer buffer = ByteBuffer.allocate(body.length);
        for (int i = 0; i < body.length; i++) {
            if (body[i] == REVISE_CODE) {
                i++;
                if (body[i] == REVISE_HEAD) {
                    buffer.put(MsgHeader.getFrameHeader());
                } else if (body[i] == REVISE_TAIL) {
                    buffer.put(MsgTail.getFrameTail());
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
     * @Author myou<myoueva@gmail.com>
     * @Description 转移内容字节(若是内容部分有和头尾字节重复则转义为指定的字节 ， 注意只转义内容部分)
     * 0xC0 -> 0xAD 0x00
     * 0xDE -> 0xAD 0x01
     * 0xAD -> 0xAD 0x02
     * @Date 6:59 PM 2019/6/29
     * @Param： body:进过编解码框架处理后的内容部分
     **/
    @Override
    public byte[] revise(byte[] body) {
        ByteBuffer buffer = ByteBuffer.allocate(body.length * 2); // 初始化一个2倍body的bytebuffer
        for (byte item : body) { // 进行内容区域字节转义防止误读
            if (item == MsgHeader.getFrameHeader()) {
                buffer.put(REVISE_CODE).put(REVISE_HEAD);
            } else if (item == MsgTail.getFrameTail()) {
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

    @Override
    public byte[] crcCode(byte[] data) {
        return CRCUtil.getCRC(data).getBytes();
    }

//    public PacketModel getFrameModel(int acquireCode, byte[] packetData, byte type) {
//        MsgHeader header = new MsgHeader();
//        header.setFrameType(type);
//        header.setAcquireCode(acquireCode);
//        header.setPacketLength(packetData.length);
//        MsgTail tail = new MsgTail();
//        int len = 0;
//        if (packetData != null)
//            len = packetData.length;
//        ByteBuffer crcBuffer = ByteBuffer.allocate(len + 11); //去除头帧和尾帧的长度
//        crcBuffer.put(RpcFpsType.HEART_TYPE.getCode());
//        crcBuffer.putInt(acquireCode);
//        crcBuffer.putInt(packetData.length);
//        crcBuffer.put(packetData);
//        // 添加帧中的crc
//        byte[] bytes = new byte[crcBuffer.position()];
//        crcBuffer.flip();
//        crcBuffer.mark();
//        crcBuffer.get(bytes);
//        byte[] crc = this.crcCode(bytes);
//        tail.setCrc(crc);
//        return new PacketModel(header, packetData, tail);
//
//    }

    public byte[] getFrameBytes(int acquireCode, byte[] packetData, byte type){
        int len = 0;
        if (packetData != null)
            len = packetData.length;
        ByteBuffer crcBuffer = ByteBuffer.allocate(len + 11); //去除头帧和尾帧的长度
        crcBuffer.put(type);
        crcBuffer.putInt(acquireCode);
        crcBuffer.putInt(packetData.length);
        crcBuffer.put(packetData);
        // 添加帧中的crc
        byte[] bytes = new byte[crcBuffer.position()];
        crcBuffer.flip();
        crcBuffer.mark();
        crcBuffer.get(bytes);
        byte[] crc = this.crcCode(bytes);
        crcBuffer.reset();
        crcBuffer.compact();
        crcBuffer.put(crc);
        byte[] result = revise((byte[]) crcBuffer.flip().array()); //数据转义
        // 构造完整数据包
        ByteBuffer frame = ByteBuffer.allocate(result.length + 2);
        frame.put(MsgHeader.getFrameHeader());
        frame.put(result);
        frame.put(MsgTail.getFrameTail());
        return (byte[]) frame.flip().array();
    }
}
