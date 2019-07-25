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
    static final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
    static final MarshallingConfiguration marshallingConfiguration = new MarshallingConfiguration();
    public MarshallingFactory(){
        marshallingConfiguration.setVersion(5);
    }

    public static MarshallingEncoder buildMarshallingEncoder(){
        MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, marshallingConfiguration);
        return new MarshallingEncoder(provider);
    }

    public static MarshallingDecoder buildMarshallingDecoder(){
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, marshallingConfiguration);
        return new MarshallingDecoder(provider, 1024);
    }
}
