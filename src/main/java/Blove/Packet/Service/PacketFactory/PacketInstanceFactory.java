package Blove.Packet.Service.PacketFactory;

import Blove.Packet.Enums.PacketType;
import Blove.Packet.Service.Packets.HeartPacket;
import Blove.Packet.Service.Packets.NormalPacket;
import Blove.Packet.impl.PacketBaseImpl;

/*
 * @Time    : 2019/6/30 9:57 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : PacketInstanceFactory.java
 * @Software: IntelliJ IDEA
 */
public class PacketInstanceFactory{
    public static byte[] getFrameBytes(PacketType packetType,int acquireCode, byte[] data){
        if (packetType == PacketType.HEART){
            return new HeartPacket().getFrameBytes(acquireCode, data);
        }else if (packetType==PacketType.NORMAL){
            return new NormalPacket().getFrameBytes(acquireCode, data);
        }else {
            return null;
        }
    }
}
