package Blove.Packet.Service.Packets;

import Blove.Packet.Enums.RpcFpsType;
import Blove.Packet.impl.PacketBaseImpl;
import Blove.Packet.model.PacketModel;

/*
 * @Time    : 2019/6/30 10:18 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : NormalPacket.java
 * @Software: IntelliJ IDEA
 */
public class NormalPacket implements PacketBaseImpl {
    @Override
    public PacketModel getFrameBytes(int acquireCode, byte[] data) {
        return new PacketModel(RpcFpsType.OK.getCode(), acquireCode,data.length, data);
    }
}
