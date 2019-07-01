package Blove.Packet.Enums;

public enum RPCSerializerProtocol {
    /**
     * @return
     * @Author myou<myoueva@gmail.com>
     * @Description //编解码框架选择
     * @Date 10:41 PM 2019/7/1
     * @Param
     **/
    JDK_SERIALIZER("jdk"),

    KRYO_SERIALIZER("kryo"),
    HESSIAN_SERIALIZER("hessian"),
    MARSHALLING_SERIALIZER("marshalling"),
    PROTOCOL_SERIALIZER("protocol");

    public String getProtocolType() {
        return protocolType;
    }

    private final String protocolType;

    /**
     * @Author myou<myoueva@gmail.com>
     * @Description 枚举类和普通的类相同都包含成员变量和成员方法和构造器(且构造器必须为private修饰)
     * 每一个枚举都是一个private final String KRYO_SERIALIZER="jdk"
     * 定义了枚举类的构造函数后，其枚举值也需要初始化对应类型值
     * 一组枚举值定义完毕后需要以分号来结尾
     * class.values()返回所有枚举值
     * @Date 10:55 PM 2019/7/1
     * @Param [protocolType]
     * @return
     **/
    RPCSerializerProtocol(String protocolType) {
        this.protocolType = protocolType;
    }

    @Override
    public String toString() {
        return protocolType;
    }
}
