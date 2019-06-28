package Blove.Packet.Enums;

public enum RpcBeginAndEndSignal {
    /**
     * @Author myou<myoueva@gmail.com>
     * @Description 帧开始结束标识枚举
     * @Date 5:02 PM 2019/6/28
     * @Param
     * @return
     **/
    START_FRAME((byte) 0xC0),
    END_FRAME((byte) 0xDE),
    UNKNOWN((byte) 0xFF);

    RpcBeginAndEndSignal(byte code) {
        this.code = code;
    }
    public static RpcBeginAndEndSignal valueOf(byte code) {
        for (RpcBeginAndEndSignal item : values()) {
            if (item.code == code)
                return item;
        }
        return END_FRAME;
    }
    private byte code;
    public byte getCode() {
        return code;
    }
}
