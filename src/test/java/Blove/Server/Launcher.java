package Blove.Server;

import Blove.Netty.Server.MessageRecvExecutor;

/*
 * @Time    : 2019/7/24 4:09 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : Launcher.java
 * @Software: IntelliJ IDEA
 */
public class Launcher {
    public static void main(String[] args) throws InterruptedException {
        MessageRecvExecutor executor = MessageRecvExecutor.getExecutor();
        executor.start();
    }
}
