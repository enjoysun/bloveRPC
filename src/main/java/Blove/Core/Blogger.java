package Blove.Core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * @Time    : 2019/7/24 4:23 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : Blogger.java
 * @Software: IntelliJ IDEA
 */
public class Blogger {
    private volatile static Logger logger=LoggerFactory.getLogger(Blogger.class);
    public static Logger loggerFactory(){
        return logger;
    }
}
