package Blove.Packet.Service.Packets;

import Blove.Packet.Enums.RpcFpsType;
import Blove.Packet.impl.PacketBaseImpl;
import Blove.Packet.model.PacketModel;

/*
 * @Time    : 2019/6/30 10:14 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : HeartPacket.java
 * @Software: IntelliJ IDEA
 */
public class HeartPacket implements PacketBaseImpl {
    @Override
    public PacketModel getFrameBytes(int acquireCode, byte[] data) {
        return new PacketModel(RpcFpsType.HEART_TYPE.getCode(), acquireCode, new byte[0]);
    }
}
