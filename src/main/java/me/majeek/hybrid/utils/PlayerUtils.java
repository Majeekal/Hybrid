package me.majeek.hybrid.utils;

import me.majeek.hybrid.data.PlayerData;
import org.bukkit.Location;

public class PlayerUtils {
    public static boolean onGround(PlayerData player) {
        Location location = player.getPlayer().getLocation();
        double expand = 0.3;
        for (double x = -expand; x <= expand; x += expand) {
            for (double z = -expand; z <= expand; z += expand) {
                if (location.clone().add(x, -0.05, z).getBlock().getType().isSolid() || location.clone().add(x, -0.5001, z).getBlock().getType().isSolid()) {
                    return true;
                }
            }
        }
        return false;
    }
}
