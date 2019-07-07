package Blove.Codecs.Marshalling;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/*
 * @Time    : 2019/7/7 3:59 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MarshallingFactory.java
 * @Software: IntelliJ IDEA
 */
public class MarshallingFactory {
    final static MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");

    /**
     * @return io.netty.handler.codec.marshalling.MarshallingDecoder
     * @Author myou<myoueva@ gmail.com>
     * @Description //解码器
     * @Date 4:07 PM 2019/7/7
     * @Param []
     **/
    public static MarshallingDecoder marshallingDecoder() {
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
        MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024 << 1);
        return decoder;
    }

    /**
     * @Author myou<myoueva@gmail.com>
     * @Description //编码器
     * @Date 4:09 PM 2019/7/7
     * @Param []
     * @return io.netty.handler.codec.marshalling.MarshallingEncoder
     **/
    public static MarshallingEncoder marshallingEncoder() {
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        MarshallerProvider provider = new DefaultMarshallerProvider(
                marshallerFactory, configuration);
        MarshallingEncoder decoder = new MarshallingEncoder(provider);
        return decoder;
    }
}
