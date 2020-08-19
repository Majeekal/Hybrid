package me.majeek.hybrid;

import io.github.retrooper.packetevents.PacketEvents;
import me.majeek.hybrid.data.DataManager;
import me.majeek.hybrid.data.PlayerData;
import me.majeek.hybrid.listeners.BukkitListener;
import me.majeek.hybrid.listeners.NetworkListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Hybrid extends JavaPlugin {
    private static Hybrid instance;

    @Override
    public void onLoad() {
        PacketEvents.load();
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        PacketEvents.getSettings().setIdentifier("anticheat_handler");
        PacketEvents.start(this);

        PacketEvents.getAPI().getEventManager().registerListener(new NetworkListener());
        getServer().getPluginManager().registerEvents(new BukkitListener(), this);

        for (Player player : getServer().getOnlinePlayers()){ DataManager.INSTANCE.register(new PlayerData(player.getUniqueId())); }
    }

    @Override
    public void onDisable() {
        PacketEvents.stop();
    }

    public static Hybrid getInstance() {
        return instance;
    }
}
