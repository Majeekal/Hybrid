package me.majeek.hybrid.checks;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.event.impl.PacketSendEvent;
import me.majeek.hybrid.Hybrid;
import me.majeek.hybrid.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class Check implements Listener {
    private String name;
    private String type;

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

    public Punishment getPunishment() {
        return Punishment.valueOf(Hybrid.getInstance().getConfig().getString(getName().toLowerCase() + ".punishment.type"));
    }

    public int getDuration() {
        return Hybrid.getInstance().getConfig().getInt(getName().toLowerCase() + "punishment.duration");
    }

    public void onPacketReceive(final PacketReceiveEvent event, final PlayerData data){}

    public void onPacketSend(final PacketSendEvent event, final PlayerData data){}
}
