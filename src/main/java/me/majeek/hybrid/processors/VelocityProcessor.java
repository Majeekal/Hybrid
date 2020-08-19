package me.majeek.hybrid.processors;

import io.github.retrooper.packetevents.event.impl.PacketSendEvent;
import io.github.retrooper.packetevents.packet.PacketType;
import io.github.retrooper.packetevents.packetwrappers.out.entityvelocity.WrappedPacketOutEntityVelocity;
import me.majeek.hybrid.data.DataManager;
import me.majeek.hybrid.data.PlayerData;
import org.bukkit.util.Vector;

public class VelocityProcessor {
    public static void process(PacketSendEvent e){
        if (e.getPacketId() == PacketType.Server.ENTITY_VELOCITY){
            PlayerData data = DataManager.INSTANCE.getUser(e.getPlayer().getUniqueId());
            WrappedPacketOutEntityVelocity event = new WrappedPacketOutEntityVelocity(e.getNMSPacket());

            data.setVelXTicks((int) Math.round(event.getVelocityX() * 100));
            data.setVelYTicks((int) Math.round(event.getVelocityY() * 100));
            data.setVelZTicks((int) Math.round(event.getVelocityZ() * 100));

            data.setLastVel(new Vector(event.getVelocityX(), event.getVelocityY(), event.getVelocityZ()));
        }
    }
}
