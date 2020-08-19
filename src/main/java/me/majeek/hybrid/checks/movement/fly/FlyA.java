package me.majeek.hybrid.checks.movement.fly;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.PacketType;
import me.majeek.hybrid.checks.Check;
import me.majeek.hybrid.checks.Info;
import me.majeek.hybrid.data.PlayerData;
import me.majeek.hybrid.utils.PlayerUtils;

@Info(name = "Fly", type = "A")
public class FlyA extends Check {
    @Override
    public void onPacketReceive(PacketReceiveEvent e, PlayerData data) {
        if (PacketType.Client.Util.isInstanceOfFlying(e.getPacketId())){
            double predicted = (data.getLastDeltaY() - 0.08) * 0.9800000190734863;

            if (!data.isOnGround() && data.getAirTicks() > 6 && !data.isTakingVelocity()){
                if (!PlayerUtils.inLiquid(data) && !PlayerUtils.isInWeb(data) && !PlayerUtils.isOnClimbable(data)){
                    double diff = Math.abs(data.getDeltaY() - predicted);
                    if (diff >= 0.001 && Math.abs(predicted) >= 0.005){
                        flag(data, "invalid vertical movement. diff: " + diff);
                        setBackToGround(data);
                    }
                }
            }
        }
    }
}
