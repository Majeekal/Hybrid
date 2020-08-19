package me.majeek.hybrid.checks;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.event.impl.PacketSendEvent;
import me.majeek.hybrid.Hybrid;
import me.majeek.hybrid.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class Check implements Listener {
    private String name;
    private String type;

    protected int preVL;

    private int vl;

    public Check(){
        name = this.getClass().getAnnotation(Info.class).name();
        type = this.getClass().getAnnotation(Info.class).type();

        Bukkit.getPluginManager().registerEvents(this, Hybrid.getInstance());
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isEnabled() {
        return Hybrid.getInstance().getConfig().getBoolean(getName().toLowerCase() + ".enabled");
    }

    public int getMaxVL(){
        return Hybrid.getInstance().getConfig().getInt(getName().toLowerCase() + ".max-vl");
    }

    public Punishment getPunishment() {
        return Punishment.valueOf(Hybrid.getInstance().getConfig().getString(getName().toLowerCase() + ".punishment.type"));
    }

    public int getDuration() {
        return Hybrid.getInstance().getConfig().getInt(getName().toLowerCase() + "punishment.duration");
    }

    public int notificationModulo(){
        return Hybrid.getInstance().getConfig().getInt("notification-modulo");
    }

    public void onPacketReceive(final PacketReceiveEvent event, final PlayerData data){}

    public void onPacketSend(final PacketSendEvent event, final PlayerData data){}

    protected void flag(PlayerData data, String information){
        vl++;

        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.hasPermission("hybrid.notify")){
                player.sendMessage(ChatColor.RED + "Hybrid: " + ChatColor.RESET + data.getPlayer().getName() + " has failed " + getName() + " (" + getType() + ") [VL:" + vl + "]");
            }
        }
    }

    protected void setBack(PlayerData playerData) {
        if (Hybrid.getInstance().getConfig().getBoolean(getName().toLowerCase() + ".setback-enabled") && (playerData.getLastSetBack() - time()) > 100){
            playerData.getPlayer().teleport(playerData.getLastLocation());
            playerData.setLastSetBack(time());
        }
    }
    protected void setBackToGround(PlayerData playerData) {
        if (Hybrid.getInstance().getConfig().getBoolean(getName().toLowerCase() + ".setback-enabled") && Math.abs(playerData.getLastSetBack() - time()) > 100) {
            playerData.getPlayer().teleport(playerData.getLastOnGroundLocation());
            playerData.setLastSetBack(time());
        }
    }

    protected long time(){ return System.nanoTime() / 1000000; }
}
