package Blove.Packet.Service.Packets;

import Blove.Model.MsgHeader;
import Blove.Model.MsgTail;
import Blove.Model.Services.AbstractStruct;
import Blove.Packet.Enums.RpcFpsType;
import Blove.Packet.impl.PacketBaseImpl;
import Blove.Packet.model.PacketModel;

import java.nio.ByteBuffer;

/*
 * @Time    : 2019/6/30 10:14 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : HeartPacket.java
 * @Software: IntelliJ IDEA
 */
public class HeartPacket extends AbstractStruct implements PacketBaseImpl {

    @Override
    public PacketModel getFrameModel(int acquireCode, byte[] data) {
        return getFrameModel(acquireCode, data, RpcFpsType.HEART_TYPE.getCode());
    }

    @Override
    public byte[] getFrameBytes(int acquire, byte[] data) {
        return getFrameBytes(acquire, data, RpcFpsType.NORMAL_TYPE.getCode());
    }
}
