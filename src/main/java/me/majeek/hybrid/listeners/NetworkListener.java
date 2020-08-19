package me.majeek.hybrid.listeners;

import io.github.retrooper.packetevents.annotations.PacketHandler;
import io.github.retrooper.packetevents.event.PacketListener;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.event.impl.PacketSendEvent;
import me.majeek.hybrid.data.DataManager;
import me.majeek.hybrid.data.PlayerData;
import me.majeek.hybrid.processors.MovementProcessor;
import me.majeek.hybrid.processors.VelocityProcessor;

public class NetworkListener implements PacketListener {
    @PacketHandler
    public void onReceive(PacketReceiveEvent e) {
        PlayerData data = DataManager.INSTANCE.getUser(e.getPlayer().getUniqueId());
        if (data != null) {
            MovementProcessor.process(e);

            data.inbound(e);
        }
    }

    @PacketHandler
    public void onSend(PacketSendEvent e) {
        PlayerData data = DataManager.INSTANCE.getUser(e.getPlayer().getUniqueId());
        if (data != null) {
            data.outgoing(e);
            VelocityProcessor.process(e);
        }
    }
}
