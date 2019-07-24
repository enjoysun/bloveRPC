package Blove.Netty.Server.Simulate.Service;

import Blove.Netty.Server.Simulate.Interface.Calc;

/*
 * @Time    : 2019/7/23 5:49 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : RPCMath.java
 * @Software: IntelliJ IDEA
 */
public class RPCMath implements Calc {
    @Override
    public int add(int x, int y) {
        return x+y;
    }
}
