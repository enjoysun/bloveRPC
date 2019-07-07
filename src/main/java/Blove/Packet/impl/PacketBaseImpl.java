package Blove.Packet.impl;

import Blove.Packet.model.PacketModel;

/*
 * @Time    : 2019/6/29 6:28 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : PacketBaseImpl.java
 * @Software: IntelliJ IDEA
 */
public interface PacketBaseImpl {
    PacketModel getFrameModel(int acquireCode, byte[] data);
    byte[] getFrameBytes(int acquire, byte[] data);
}
