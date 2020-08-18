package me.majeek.hybrid.processors;

import io.github.retrooper.packetevents.enums.minecraft.PlayerAction;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.PacketType;
import io.github.retrooper.packetevents.packetwrappers.in.entityaction.WrappedPacketInEntityAction;
import me.majeek.hybrid.data.DataManager;
import me.majeek.hybrid.data.PlayerData;
import me.majeek.hybrid.utils.PlayerUtils;

public class MovementProcessor {
    public static void process(PacketReceiveEvent event){
        PlayerData data = DataManager.INSTANCE.getUser(event.getPlayer().getUniqueId());
        if (data != null){
            if (PacketType.Client.Util.isInstanceOfFlying(event.getPacketId())){
                if (PlayerUtils.onGround(data)){
                    data.setOnGround(true);
                }else data.setOnGround(false);

                data.setLastLocation(data.getLocation() != null ? data.getLocation() : data.getPlayer().getLocation());
                data.setLocation(data.getPlayer().getLocation());

                data.setDeltaXZ(data.getLocation().toVector().setY(0).distance(data.getLastLocation().toVector()));
                data.setDeltaY(data.getLocation().getY() - data.getLastLocation().getY());
            } else if (event.getPacketId() == PacketType.Client.ENTITY_ACTION){
                WrappedPacketInEntityAction packet = new WrappedPacketInEntityAction(event.getNMSPacket());
                if (packet.getAction().equals(PlayerAction.START_SPRINTING)){
                    data.setSprinting(true);
                }
                if (packet.getAction().equals(PlayerAction.STOP_SPRINTING)){
                    data.setSprinting(false);
                }
                if (packet.getAction().equals(PlayerAction.START_SNEAKING)){
                    data.setSneaking(true);
                }
                if (packet.getAction().equals(PlayerAction.STOP_SNEAKING)){
                    data.setSneaking(false);
                }
            }
        }
    }
}
