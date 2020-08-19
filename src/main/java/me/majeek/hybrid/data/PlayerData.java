package me.majeek.hybrid.data;

import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.event.impl.PacketSendEvent;
import me.majeek.hybrid.checks.Check;
import me.majeek.hybrid.checks.CheckManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayerData {

    private Player player;
    private Location location, lastLocation, lastOnGroundLocation;
    private Vector lastVel;
    private double deltaXZ, deltaY, lastDeltaXZ, lastDeltaY;
    private int airTicks, velXTicks, velYTicks, velZTicks;
    private long lastSetBack = System.nanoTime() / 1000000;
    private boolean isSprinting, isSneaking, onGround, serverOnGround;
    private List<Check> checks;

    private ExecutorService executorService;

    public PlayerData(UUID uuid){
        this.player = Bukkit.getPlayer(uuid);
        this.checks = CheckManager.loadChecks();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Credits to funke for the velocity system.
    public void lowerVelocityTicks(){
        velXTicks = Math.max(0, velXTicks - 1);
        velYTicks = Math.max(0,velYTicks - 1);
        velZTicks = Math.max(0, velZTicks - 1);
    }

    public boolean isTakingVelocity() { return velXTicks > 0 || velYTicks > 0 || velZTicks > 0; }

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

    public double getLastDeltaXZ() {
        return lastDeltaXZ;
    }

    public void setLastDeltaXZ(double lastDeltaXZ) {
        this.lastDeltaXZ = lastDeltaXZ;
    }

    public double getLastDeltaY() {
        return lastDeltaY;
    }

    public void setLastDeltaY(double lastDeltaY) {
        this.lastDeltaY = lastDeltaY;
    }

    public boolean isServerOnGround() {
        return serverOnGround;
    }

    public void setServerOnGround(boolean serverOnGround) {
        this.serverOnGround = serverOnGround;
    }

    public int getAirTicks() {
        return airTicks;
    }

    public void setAirTicks(int airTicks) {
        this.airTicks = airTicks;
    }

    public Location getLastOnGroundLocation() {
        return lastOnGroundLocation;
    }

    public void setLastOnGroundLocation(Location lastOnGroundLocation) {
        this.lastOnGroundLocation = lastOnGroundLocation;
    }

    public long getLastSetBack() {
        return lastSetBack;
    }

    public void setLastSetBack(long lastSetBack) {
        this.lastSetBack = lastSetBack;
    }

    public int getVelXTicks() {
        return velXTicks;
    }

    public void setVelXTicks(int velXTicks) {
        this.velXTicks = velXTicks;
    }

    public int getVelYTicks() {
        return velYTicks;
    }

    public void setVelYTicks(int velYTicks) {
        this.velYTicks = velYTicks;
    }

    public int getVelZTicks() {
        return velZTicks;
    }

    public void setVelZTicks(int velZTicks) {
        this.velZTicks = velZTicks;
    }

    public Vector getLastVel() {
        return lastVel;
    }

    public void setLastVel(Vector lastVel) {
        this.lastVel = lastVel;
    }

    public void inbound(PacketReceiveEvent event){
        executorService.execute(() -> checks.stream().forEach(check -> check.onPacketReceive(event, DataManager.INSTANCE.getUser(event.getPlayer().getUniqueId()))));
    }

    public void outgoing(PacketSendEvent event){
        executorService.execute(() -> checks.stream().forEach(check -> check.onPacketSend(event, DataManager.INSTANCE.getUser(event.getPlayer().getUniqueId()))));
    }
}
