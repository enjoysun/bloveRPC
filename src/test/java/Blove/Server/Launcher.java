package Blove.Server;

import Blove.Netty.Server.MessageRecvExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * @Time    : 2019/7/24 4:09 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : Launcher.java
 * @Software: IntelliJ IDEA
 */
public class Launcher {
    public static void main(String[] args) throws InterruptedException {
        Logger logger = LoggerFactory.getLogger(Object.class);
        logger.info("ss");
        MessageRecvExecutor executor = MessageRecvExecutor.getExecutor();
        executor.start();
    }
}
