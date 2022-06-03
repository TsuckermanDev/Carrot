package cn.nukkit.network.protocol.v201;

import cn.nukkit.network.protocol.DataPacket;

public class AddBehaviorTreePacket201 extends DataPacket {

    public String unknown;

    @Override
    public byte pid() {
        return ProtocolInfo201.ADD_BEHAVIOR_TREE_PACKET;
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        this.reset();
        this.putString(unknown);
    }
}