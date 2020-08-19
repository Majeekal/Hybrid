package me.majeek.hybrid.checks.movement.speed;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.PacketType;
import me.majeek.hybrid.checks.Check;
import me.majeek.hybrid.checks.Info;
import me.majeek.hybrid.data.PlayerData;
import me.majeek.hybrid.utils.PlayerUtils;

@Info(name = "Speed", type = "A")
public class SpeedA extends Check {
    private boolean lastOnGround, lastLastOnGround;

    @Override
    public void onPacketReceive(PacketReceiveEvent e, PlayerData data) {
        if (PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())){
            if (!data.isOnGround() && !lastOnGround && !lastLastOnGround && !data.getPlayer().isFlying() && !PlayerUtils.inLiquid(data)){
                double predicted = data.getLastDeltaXZ() * 0.91;
                double diff = data.getDeltaXZ() - predicted;

                if (diff >= 0.026) {
                    if (++preVL > 5) {
                        flag(data, "ignored friction at air! diff: " + diff);
                        setBackToGround(data);
                    }
                } else preVL = Math.max(0, preVL--);
            }
            lastLastOnGround = lastOnGround;
            lastOnGround = data.isOnGround();
        }
    }
}
