package me.majeek.hybrid.listeners;

import me.majeek.hybrid.data.DataManager;
import me.majeek.hybrid.data.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BukkitListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        DataManager.INSTANCE.register(new PlayerData(event.getPlayer().getUniqueId()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        DataManager.INSTANCE.unregister(DataManager.INSTANCE.getUser(event.getPlayer().getUniqueId()));
    }
}
