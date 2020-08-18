package me.majeek.hybrid.checks.movement;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.PacketType;
import me.majeek.hybrid.checks.Check;
import me.majeek.hybrid.checks.Info;
import me.majeek.hybrid.data.PlayerData;

@Info(name = "Fly", type = "A")
public class FlyA extends Check {
    @Override
    public void onPacketReceive(PacketReceiveEvent event, PlayerData data) {
        if(event.getPacketId() == PacketType.Client.FLYING){

        }
    }
}
