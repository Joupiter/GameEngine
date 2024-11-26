package fr.joupi.games.engine.player;

import fr.joupi.games.engine.point.SinglePoint;
import fr.joupi.games.engine.utils.concurrent.MultiThreading;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.UUID;

@Getter
@Setter
public abstract class BaseGamePlayer {

    protected final UUID uuid;

    protected final Player player;

    protected final CraftPlayer craftPlayer;

    protected boolean spectator;

    public BaseGamePlayer(UUID uuid, boolean spectator) {
        this.uuid = uuid;
        this.player = Bukkit.getPlayer(uuid);
        this.craftPlayer = (CraftPlayer) player;
        this.spectator = spectator;
    }

    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    public void sendMessage(String... messages) {
        Arrays.asList(messages).forEach(this::sendMessage);
    }

    public void teleport(Location location) {
        player.teleport(location);
    }

    public void teleport(SinglePoint point) {
        point.teleport(player);
    }

    public String getName() {
        return player.getName();
    }

    public Location getLocation() {
        return player.getLocation();
    }

    public PlayerInventory getInventory() {
        return player.getInventory();
    }

    public boolean isAlive() {
        return !spectator;
    }

    public void playSound(Sound sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public void setGameMode(GameMode gameMode) {
        player.setGameMode(gameMode);
    }

    public void setHealth(double health) {
        player.setHealth(health);
    }

    public void closeInventory() {
        player.closeInventory();
    }

    public Player.Spigot spigot() {
        return player.spigot();
    }

    public void sendTitle(int fadeIn, int stay, int fadeOut, String title, String subtitle) {
        MultiThreading.execute(() ->
                sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn, stay, fadeOut),
                        new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}")),
                        new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}"))));
    }

    public void sendActionBar(String message) {
        MultiThreading.execute(() -> sendPacket(new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message + "\"}"), (byte) 2)));
    }

    public void sendPacket(PlayerConnection connection, Packet<?> packet) {
        connection.sendPacket(packet);
    }

    public void sendPacket(PlayerConnection connection, Packet<?>... packets) {
        Arrays.asList(packets)
                .forEach(connection::sendPacket);
    }

    public void sendPacket(Packet<?> packet) {
        sendPacket(craftPlayer.getHandle().playerConnection, packet);
    }

    public void sendPacket(Packet<?>... packets) {
        sendPacket(craftPlayer.getHandle().playerConnection, packets);
    }

}