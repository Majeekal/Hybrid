package me.majeek.hybrid.utils;

import me.majeek.hybrid.data.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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

    public static boolean inLiquid(PlayerData data) {
        Player player = data.getPlayer();

        double expand = 0.3;
        for (double x = -expand; x <= expand; x += expand) {
            for (double z = -expand; z <= expand; z += expand) {
                if (player.getLocation().clone().add(z, 0, x).getBlock().isLiquid()) { return true; }
                if (player.getLocation().clone().add(z, player.getEyeLocation().getY(), x).getBlock().isLiquid()) { return true; }
            }
        }
        return false;
    }

    public static boolean blockNearHead(PlayerData data) {
        Player player = data.getPlayer();

        double expand = 0.3;
        for (double x = -expand; x <= expand; x += expand) {
            for (double z = -expand; z <= expand; z += expand) {
                if (player.getLocation().clone().add(z, 2, x).getBlock().getType() != Material.AIR) {
                    return true;
                }
                if (player.getLocation().clone().add(z, 1.5001, x).getBlock().getType() != Material.AIR) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isOnIce(PlayerData data) {
        Player p = data.getPlayer();
        if (p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().toString().contains("ICE")
                || p.getLocation().clone().add(0, -0.5, 0).getBlock().getRelative(BlockFace.DOWN).getType().toString().contains("ICE")) {
            return true;
        }
        return false;
    }

    public static boolean isOnLilyOrCarpet(PlayerData data) {
        Location loc = data.getPlayer().getLocation();
        double expand = 0.3;
        for (double x = -expand; x <= expand; x += expand) {
            for (double z = -expand; z <= expand; z += expand) {
                if (loc.clone().add(z, 0, x).getBlock().getType().toString().contains("LILY")
                        || loc.clone().add(z, -0.001, x).getBlock().getType().toString().contains("CARPET")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isOnWeirdBlock(PlayerData data) {
        Location loc = data.getPlayer().getLocation();
        double expand = 0.3;
        for (double x = -expand; x <= expand; x += expand) {
            for (double z = -expand; z <= expand; z += expand) {
                if (loc.clone().add(z, 0, x).getBlock().getType().toString().contains("SLIME")
                        || loc.clone().add(z, -0.001, x).getBlock().getType().toString().contains("ICE")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isInWeb(PlayerData data) {
        Player player = data.getPlayer();

        double expand = 0.3;
        for (double x = -expand; x <= expand; x += expand) {
            for (double z = -expand; z <= expand; z += expand) {
                if (player.getLocation().clone().add(z, 0, x).getBlock().getType() == Material.COBWEB) { return true; }
                if (player.getLocation().clone().add(z, player.getEyeLocation().getY(), x).getBlock().getType() == Material.COBWEB) { return true; }
            }
        }
        return false;
    }

    public static boolean isOnClimbable(PlayerData data) {
        Player player = data.getPlayer();
        return player.getLocation().getBlock().getType() == Material.LADDER || player.getLocation().getBlock().getType() == Material.VINE || player.getLocation().clone().add(0, 1, 0).getBlock().getType() == Material.LADDER || player.getLocation().clone().add(0, 1, 0).getBlock().getType() == Material.VINE;
    }

    public static int getPotionEffectLevel(Player player, PotionEffectType pet) {
        for (PotionEffect pe : player.getActivePotionEffects()) {
            if (!pe.getType().getName().equals(pet.getName())) continue;
            return pe.getAmplifier() + 1;
        }
        return 0;
    }
}
