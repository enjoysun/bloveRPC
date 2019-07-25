package Blove.Codecs.MsgToMsg;

import Blove.Codecs.Marshalling.MarshallingFactory;
import Blove.Codecs.Marshalling.NettyMarshallingEncoder;
import Blove.Packet.model.PacketModel;
import Blove.Packet.model.PacketResponseModel;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;

import java.util.List;

/*
 * @Time    : 2019/7/19 3:17 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MsgEncoder.java
 * @Software: IntelliJ IDEA
 */
public class MsgEncoder extends MessageToMessageEncoder<PacketResponseModel> {
//    private final NettyMarshallingEncoder marshallingEncoder;

    public MsgEncoder() {
//        this.marshallingEncoder = MarshallingFactory.marshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, PacketResponseModel packetModel, List<Object> list) throws Exception {
        if (packetModel==null||packetModel.getHeader()==null) {
            System.out.printf("%s链路消息为空", channelHandlerContext.name());
            channelHandlerContext.close();
        }
        ByteBuf sendBuffer = Unpooled.buffer();
//        sendBuffer.writeInt(packetModel.getHeader().getPacketLength());
        sendBuffer.writeByte(packetModel.getHeader().getFrameHeader());
        /** 消息长度字段位于2位置偏移，该位置影响LengthFieldBasedFrameDecoder初始化参数
         * 所以构造pojo消息体时要额外注意该字段的位置才能使用定长方案解决粘包问题(length_field=4)(offset=2)
         * 该帧结构lengthField(maxframe,0,4,0,4)
         * |frameHeader|length|code|crc|body|tail
         */
        sendBuffer.writeByte(packetModel.getHeader().getFrameType());
        sendBuffer.writeInt(packetModel.getHeader().getAcquireCode());
        sendBuffer.writeBytes(packetModel.getTail().getCrc());
//        marshallingEncoder.encode(channelHandlerContext, packetModel.getBody(), sendBuffer);
        sendBuffer.writeByte(packetModel.getTail().getFrameTail());
        list.add(sendBuffer);
    }
}
