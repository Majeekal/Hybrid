package me.majeek.hybrid.checks.combat.reach;

import io.github.retrooper.packetevents.enums.minecraft.EntityUseAction;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.PacketType;
import io.github.retrooper.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;
import me.majeek.hybrid.checks.Check;
import me.majeek.hybrid.checks.Info;
import me.majeek.hybrid.data.PlayerData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@Info(name = "Reach", type = "A")
public class ReachA extends Check {
    @Override
    public void onPacketReceive(PacketReceiveEvent e, PlayerData data) {
        if (e.getPacketId() == PacketType.Client.USE_ENTITY){
            WrappedPacketInUseEntity wrappedPacketInUseEntity = new WrappedPacketInUseEntity(e.getNMSPacket());
            if (wrappedPacketInUseEntity.getAction() == EntityUseAction.ATTACK && wrappedPacketInUseEntity.getEntity() instanceof Player){
                Entity attackedEntity = wrappedPacketInUseEntity.getEntity();

                double dist = (data.getPlayer().getEyeLocation().clone().toVector().setY(0).distance(attackedEntity.getLocation().clone().toVector().setY(0)) - 0.2);

                if (dist > 4){
                    if (++preVL > 1){
                        flag(data, "hit farther than possible. dist: " + dist);
                    }
                }else preVL = 0;
            }
        }
    }
}
