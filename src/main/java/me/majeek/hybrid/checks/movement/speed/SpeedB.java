package me.majeek.hybrid.checks.movement.speed;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.PacketType;
import me.majeek.hybrid.checks.Check;
import me.majeek.hybrid.checks.Info;
import me.majeek.hybrid.data.PlayerData;
import me.majeek.hybrid.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

@Info(name = "Speed", type = "B")
public class SpeedB extends Check {
    @Override
    public void onPacketReceive(PacketReceiveEvent e, PlayerData data) {
        if (PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())){
            double limit = getBaseSpeed(data.getPlayer());

            if (PlayerUtils.blockNearHead(data)) limit += 0.91;
            if (PlayerUtils.isOnWeirdBlock(data)) limit += 0.34;
            if (data.isTakingVelocity()) limit += Math.hypot(Math.abs(data.getLastVel().getX()), Math.abs(data.getLastVel().getZ()));

            if (data.getDeltaXZ() > limit
                    && !data.getPlayer().isFlying()) {
                if (++preVL > 7) {
                    flag(data, "breached limit, s: " + data.getDeltaXZ());
                    setBackToGround(data);
                }
            } else preVL *= 0.75;
        }
    }

    private float getBaseSpeed(Player player) {
        return 0.34f + (PlayerUtils.getPotionEffectLevel(player, PotionEffectType.SPEED) * 0.062f) + ((player.getWalkSpeed() - 0.2f) * 1.6f);
    }
}
