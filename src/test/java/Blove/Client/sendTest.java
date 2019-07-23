package Blove.Client;

import Blove.Netty.Client.MsgSendExecutor;

/*
 * @Time    : 2019/7/23 4:24 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : sendTest.java
 * @Software: IntelliJ IDEA
 */
public class sendTest {
    public static void main(String[] args) throws InterruptedException {
        MsgSendExecutor executor = new MsgSendExecutor("127.0.0.1:8083");
        Calc calc = executor.execute(Calc.class);
//        executor.close();
        calc.add(1, 2);
        Thread.sleep(10000);
    }
}

interface Calc{
    int add(int x, int y);
}
