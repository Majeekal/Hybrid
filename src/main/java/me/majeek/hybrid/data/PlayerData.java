package me.majeek.hybrid.data;

import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.event.impl.PacketSendEvent;
import me.majeek.hybrid.checks.Check;
import me.majeek.hybrid.checks.CheckManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayerData {
    private Player player;
    private Location location, lastLocation;
    private double deltaXZ, deltaY;
    private boolean isSprinting, isSneaking, onGround;
    private List<Check> checks;

    private ExecutorService executorService;

    public PlayerData(UUID uuid){
        this.player = Bukkit.getPlayer(uuid);
        this.checks = CheckManager.loadChecks();
        executorService = Executors.newSingleThreadExecutor();
    }

    public int getPing() { return PacketEvents.getAPI().getPlayerUtils().getPing(getPlayer()); }

    public Player getPlayer() {
        return player;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public double getDeltaXZ() {
        return deltaXZ;
    }

    public void setDeltaXZ(double deltaXZ) {
        this.deltaXZ = deltaXZ;
    }

    public double getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(double deltaY) {
        this.deltaY = deltaY;
    }

    public boolean isSprinting() {
        return isSprinting;
    }

    public void setSprinting(boolean sprinting) {
        isSprinting = sprinting;
    }

    public boolean isSneaking() {
        return isSneaking;
    }

    public void setSneaking(boolean sneaking) {
        isSneaking = sneaking;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void inbound(PacketReceiveEvent event){
        executorService.execute(() -> checks.forEach(check -> check.onPacketReceive(event, DataManager.INSTANCE.getUser(event.getPlayer().getUniqueId()))));
    }

    public void outgoing(PacketSendEvent event){
        executorService.execute(() -> checks.forEach(check -> check.onPacketSend(event, DataManager.INSTANCE.getUser(event.getPlayer().getUniqueId()))));
    }
}
