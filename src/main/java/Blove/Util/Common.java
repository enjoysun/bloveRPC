package Blove.Util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/*
 * @Time    : 2019/7/26 9:50 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : Common.java
 * @Software: IntelliJ IDEA
 */
public class Common {

    /**
     * @Author myoueva@gmail.com
     * @Description //crc16验证帧的内容完整性(协议要求验证确认码和内容字段即可)
     * @Date 10:17 AM 2019/7/26
     * @Param []
     * @return boolean
     **/
    public static boolean checkCrc(int acquireCode, byte[] data, byte[] oldCrc){
//        ByteBuf buf = Unpooled.buffer();
//        buf.writeInt(acquireCode);
//        buf.writeBytes(data);
        String crc =new String(CRCUtil.crcCode(acquireCode, data));
        String old = new String(oldCrc);
        boolean flag = crc.intern()==old.intern();
        return flag;
    }

    /**
     * @Author myoueva@gmail.com
     * @Description //该协议中确认码每一帧都要求不同，但是服务端响应的确认码要相同(确认码规则:信息id异或id的hashcode)
     * @Date 10:16 AM 2019/7/26
     * @Param []
     * @return int
     **/
    public static int getAcquireCode(String mid){
        return mid.hashCode()^mid.hashCode()>>>16;
    }
}
