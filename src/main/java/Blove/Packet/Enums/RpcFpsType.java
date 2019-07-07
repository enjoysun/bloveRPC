package Blove.Packet.Enums;

public enum RpcFpsType {
    /**
     * @Author myou<myoueva@gmail.com>
     * @Description 数据包帧类型枚举
     * @Date 4:48 PM 2019/6/28
     * @Param
     * @return
     **/
    OK((byte) 0x01), //正常通信
    CLIENT_TIMEOUT((byte) 0x02), // 客户端连接超时
    SERVER_TIMEOUT((byte) 0x03), // 服务端超时
    BAD_REQUEST((byte) 0x04), // 请求结构体错误
    BAD_RESPONSE((byte) 0x05), // 输出结构体错误
    HEART_TYPE((byte) 0x06), // 心跳
    NORMAL_TYPE((byte) 0x07), //正常
    CLIENT_SERIALIZATION_ERROR((byte) 0x08),//
    CLIENT_CANCELED((byte) 0x09), // 客户端取消操作
    SERVER_BUSY((byte) 0x0A), // 服务端繁忙
    CLIENT_BUSY((byte) 0x0B), // 客户端繁忙
    SERIALIZATION_ERROR((byte) 0x0C), // 序列化错误
    INTERNAL_ERROR((byte) 0x0D), // 内部错误
    SERVER_METHOD_INVOKE_ERROR((byte) 0x0E), // 服务端方法运行错误
    UNKNOWN((byte) 0xFF); // 未知错误

    private byte code;

    RpcFpsType(byte code) {
        this.code = code;
    }

    public static RpcFpsType valueOf(byte code) {
        for (RpcFpsType item : values()) {
            if (item.code == code)
                return item;
        }
        return UNKNOWN;
    }
    public byte getCode() {
        return code;
    }
}
