package Blove;

/*
 * @Time    : 2019/7/2 10:10 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : RPCSystemConfig.java
 * @Software: IntelliJ IDEA
 */
public class RPCSystemConfig {
    public static void main(String[] args) {
        Integer nums= Integer.getInteger("nettyrpc.default.thread.nums", 12);
        System.out.println(nums);
    }
}
