package Blove.Codecs.Marshalling;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingEncoder;

/*
 * @Time    : 2019/7/19 4:15 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : NettyMarshallingEncoder.java
 * @Software: IntelliJ IDEA
 */
public class NettyMarshallingEncoder  extends MarshallingEncoder {
    /**
     * @Author myoueva@gmail.com
     * @Description //TODO
     * 装饰模式抛出encode
     * @Date 4:31 PM 2019/7/19
     * @Param [provider]
     * @return
     **/
    public NettyMarshallingEncoder(MarshallerProvider provider) {
        super(provider);
    }

    public void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        super.encode(ctx, msg, out);
    }

}
