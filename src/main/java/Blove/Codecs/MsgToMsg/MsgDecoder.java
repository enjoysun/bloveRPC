package Blove.Codecs.MsgToMsg;

import Blove.Codecs.Marshalling.MarshallingFactory;
import Blove.Codecs.Marshalling.NettyMarshallingDecoder;
import Blove.Model.MsgHeader;
import Blove.Model.MsgRequest;
import Blove.Model.MsgTail;
import Blove.Packet.model.PacketRequestModel;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/*
 * @Time    : 2019/7/19 4:42 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MsgDecoder.java
 * @Software: IntelliJ IDEA
 */
public class MsgDecoder extends LengthFieldBasedFrameDecoder {
//    private final NettyMarshallingDecoder marshallingDecoder;

    public MsgDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
//        this.marshallingDecoder = MarshallingFactory.marshallingDecoder();
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        //一帧数据
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame==null){
            return null;
        }
        PacketRequestModel model=new PacketRequestModel();
        MsgHeader header = new MsgHeader();
//        header.setPacketLength(in.readInt());
        header.setFrameType(in.readByte());
        header.setAcquireCode(in.readInt());
        MsgTail tail = new MsgTail();
        ByteBuf crcBuf = in.readBytes(4);
        byte[] bytes = new byte[crcBuf.readableBytes()];
        crcBuf.readBytes(bytes);
        tail.setCrc(bytes);
        model.setHeader(header);
//        model.setBody((MsgRequest) marshallingDecoder.decode(ctx, in));
        model.setTail(tail);
        return model;
    }
}
