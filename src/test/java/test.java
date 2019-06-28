import Blove.Packet.Enums.RpcBeginAndEndSignal;
import Blove.Packet.Enums.RpcFpsType;

/*
 * @Time    : 2019/6/28 5:09 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : test.java
 * @Software: IntelliJ IDEA
 */
public class test {
    public static void main(String[] args) {
        System.out.println(RpcBeginAndEndSignal.END_FRAME.getCode());
        System.out.println(RpcFpsType.BAD_REQUEST.getCode());
    }
}
