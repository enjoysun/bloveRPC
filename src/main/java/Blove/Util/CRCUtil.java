package Blove.Util;

import java.math.BigInteger;
import java.nio.ByteBuffer;

/*
 * @Time    : 2019/6/29 7:43 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : CRCUtil.java
 * @Software: IntelliJ IDEA
 */
public class CRCUtil {
    /**
     * 计算CRC16校验码
     * 摘自互联网
     * @param bytes 字节数组
     * @return {@link String} 校验码
     * @since 1.0
     */
    public static String getCRC(byte[] bytes) {
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;
        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        return Integer.toHexString(CRC);
    }

    /**
     * 将16进制单精度浮点型转换为10进制浮点型
     *
     * @return float
     * @since 1.0
     */
    private float parseHex2Float(String hexStr) {
        BigInteger bigInteger = new BigInteger(hexStr, 16);
        return Float.intBitsToFloat(bigInteger.intValue());
    }

    /**
     * 将十进制浮点型转换为十六进制浮点型
     *
     * @return String
     * @since 1.0
     */
    private String parseFloat2Hex(float data) {
        return Integer.toHexString(Float.floatToIntBits(data));
    }

    public static byte[] crcCode(int acquireCode,byte[] data) {
        /**
         * @Author myoueva@gmail.com
         * @Description //TODO
         * 根据消息体+回应码+消息长度构建crc16校验码
         * @Date 3:53 PM 2019/7/19
         * @Param [data]
         * @return byte[]
         **/
        int len = 0;
        if (data != null)
            len = data.length;
        ByteBuffer crcBuffer = ByteBuffer.allocate(len + 8);
        crcBuffer.putInt(acquireCode);
        crcBuffer.putInt(data.length);
        crcBuffer.put(data);
        byte[] bytes = new byte[crcBuffer.position()];
        return CRCUtil.getCRC(bytes).getBytes();
    }
}
