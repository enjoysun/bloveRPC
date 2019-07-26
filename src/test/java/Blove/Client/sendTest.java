package Blove.Client;

import Blove.Netty.Client.MsgSendExecutor;
import Blove.Netty.Server.Simulate.Interface.Calc;

/*
 * @Time    : 2019/7/23 4:24 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : sendTest.java
 * @Software: IntelliJ IDEA
 */
public class sendTest {
    public static void main(String[] args) throws InterruptedException {
        MsgSendExecutor executor = new MsgSendExecutor("127.0.0.1:8086");
        Calc calc = executor.execute(Calc.class);
//        executor.close();
        int result = calc.add(4, 2);
        System.out.println(result);
        Thread.sleep(10000);
    }
}
