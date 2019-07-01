import Blove.Packet.Enums.RPCSerializerProtocol;

/*
 * @Time    : 2019/7/1 10:50 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : enums.java
 * @Software: IntelliJ IDEA
 */
public class enums {
    public static void main(String[] args) {
        System.out.println(RPCSerializerProtocol.JDK_SERIALIZER);
        System.out.println(RPCSerializerProtocol.HESSIAN_SERIALIZER.toString());
        System.out.println(RPCSerializerProtocol.JDK_SERIALIZER.getProtocolType());
        for (RPCSerializerProtocol item: RPCSerializerProtocol.values()){
            System.out.println(item);
        }
    }
}
