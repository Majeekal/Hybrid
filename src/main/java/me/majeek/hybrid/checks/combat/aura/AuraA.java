package me.majeek.hybrid.checks.combat.aura;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.PacketType;
import me.majeek.hybrid.checks.Check;
import me.majeek.hybrid.checks.Info;
import me.majeek.hybrid.data.PlayerData;

@Info(name = "Aura", type = "A")
public class AuraA extends Check {
    private long lastFlying;

    @Override
    public void onPacketReceive(PacketReceiveEvent e, PlayerData data) {
        if (PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())){
            lastFlying = time();
        }else if (e.getPacketId() == PacketType.Client.USE_ENTITY){
            long diff = time() - lastFlying;

            if (diff < 5) {
                if (++preVL > 5) {
                    flag(data, "post");
                }
            } else preVL = 0;
        }
    }
}
