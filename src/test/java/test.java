import Blove.Core.RPCLayerThreadFactory;
import Blove.Packet.Enums.RpcBeginAndEndSignal;
import Blove.Packet.Enums.RpcFpsType;
import Blove.Util.CRCUtil;
import sun.misc.CRC16;

import java.util.zip.CRC32;

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
        System.out.println(CRCUtil.getCRC("ss".getBytes()));
        Thread thread =new RPCLayerThreadFactory("hello", false).newThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("ok");
            }
        });
        System.out.println(thread.getName());
        thread.run();
    }
}
