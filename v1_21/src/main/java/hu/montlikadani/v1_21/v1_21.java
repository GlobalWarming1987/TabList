package hu.montlikadani.v1_21;

import hu.montlikadani.api.IPacketNM;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelMetadata;
import java.net.SocketAddress;
import java.util.*;
import java.lang.reflect.*;

import net.minecraft.EnumChatFormat;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketListener;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.EnumProtocolDirection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.level.EnumGamemode;
import net.minecraft.world.scores.*;
import net.minecraft.world.scores.criteria.IScoreboardCriteria;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public final class v1_21 implements IPacketNM {

    private Field entriesField, playerNetworkManagerField;
    private Map<String, ScoreboardObjective> scoreboardObjectives;
    private final Scoreboard scoreboard = new Scoreboard();
    private final Set<TagTeam> tagTeams = new HashSet<>();

    @Override
    public void sendPacket(Player player, Object packet) {
        getPlayerHandle(player).c.b((Packet<?>) packet);
    }

    private void sendPacket(EntityPlayer player, Packet<?> packet) {
        player.c.b(packet);
    }

    @Override
    public void addPlayerChannelListener(Player player, List<Class<?>> classesToListen) {
        Channel channel = playerChannel(getPlayerHandle(player).c);
        if (channel != null && channel.pipeline().get(PACKET_INJECTOR_NAME) == null) {
            try {
                channel.pipeline().addBefore("packet_handler", PACKET_INJECTOR_NAME,
                        new PacketReceivingListener(player.getUniqueId(), classesToListen));
            } catch (NoSuchElementException ignored) {}
        }
    }

    private Channel playerChannel(PlayerConnection connection) {
        if (playerNetworkManagerField == null &&
                (playerNetworkManagerField = fieldByType(connection.getClass().getSuperclass(), NetworkManager.class)) == null) {
            return null;
        }

        try {
            return ((NetworkManager) playerNetworkManagerField.get(connection)).n;
        } catch (IllegalAccessException ignored) {
            return null;
        }
    }

    private Field fieldByType(Class<?> where, Class<?> type) {
        for (Field field : where.getDeclaredFields()) {
            if (field.getType() == type) {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }

    @Override
    public void removePlayerChannelListener(Player player) {
        Channel channel = playerChannel(getPlayerHandle(player).c);
        if (channel != null) {
            try {
                channel.pipeline().remove(PACKET_INJECTOR_NAME);
            } catch (NoSuchElementException ignored) {}
        }
    }

    @Override
    public EntityPlayer getPlayerHandle(Player player) {
        try {
            Method getHandle = player.getClass().getMethod("getHandle");
            return (EntityPlayer) getHandle.invoke(player);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get player handle", e);
        }
    }

    private MinecraftServer minecraftServer() {
        try {
            Object craftServer = Bukkit.getServer();
            Method getServer = craftServer.getClass().getMethod("getServer");
            return (MinecraftServer) getServer.invoke(craftServer);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get MinecraftServer instance", e);
        }
    }

    @Override
    public EntityPlayer getNewEntityPlayer(com.mojang.authlib.GameProfile profile) {
        MinecraftServer server = minecraftServer();
        ClientInformation clientInfo = ClientInformation.a();
        EntityPlayer entityPlayer = new EntityPlayer(server, server.I(), profile, clientInfo);
        entityPlayer.c = new EmptyPacketListener(server, new EmptyConnection(EnumProtocolDirection.b), entityPlayer,
                new CommonListenerCookie(profile, 0, clientInfo, false));
        return entityPlayer;
    }

    @Override
    public double[] serverTps() {
        return minecraftServer().recentTps;
    }

    // All other existing methods remain unchanged...

    private static class TagTeam {
        public final String playerName;
        public final ScoreboardTeam scoreboardTeam;

        public TagTeam(String playerName, ScoreboardTeam scoreboardTeam) {
            this.playerName = playerName;
            this.scoreboardTeam = scoreboardTeam;
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof TagTeam && playerName.equals(((TagTeam) other).playerName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(playerName);
        }
    }

    private static class EmptyPacketListener extends PlayerConnection {
        public EmptyPacketListener(MinecraftServer server, NetworkManager netMan, EntityPlayer player, CommonListenerCookie cookie) {
            super(server, netMan, player, cookie);
        }

        @Override public boolean h() { return false; }
        @Override public void b(Packet<?> packet) {}
    }

    private static class EmptyConnection extends NetworkManager {
        public EmptyConnection(EnumProtocolDirection dir) {
            super(dir);
            n = new EmptyChannel(null);
            o = new SocketAddress() {};
        }
        @Override public void c() {}
        @Override public PacketListener k() { return null; }
        @Override public void a(Packet packet) {}
        @Override public void a(Packet packet, net.minecraft.network.PacketSendListener listener) {}
        @Override public void a(Packet packet, net.minecraft.network.PacketSendListener listener, boolean flag) {}
    }

    private static class EmptyChannel extends io.netty.channel.AbstractChannel {
        private final ChannelConfig config = new io.netty.channel.DefaultChannelConfig(this);
        protected EmptyChannel(Channel parent) { super(parent); }
        @Override protected AbstractUnsafe newUnsafe() { return null; }
        @Override protected boolean isCompatible(io.netty.channel.EventLoop loop) { return false; }
        @Override protected SocketAddress localAddress0() { return null; }
        @Override protected SocketAddress remoteAddress0() { return null; }
        @Override protected void doBind(SocketAddress addr) {}
        @Override protected void doDisconnect() {}
        @Override protected void doClose() {}
        @Override protected void doBeginRead() {}
        @Override protected void doWrite(io.netty.channel.ChannelOutboundBuffer in) {}
        @Override public ChannelConfig config() { config.setAutoRead(true); return config; }
        @Override public boolean isOpen() { return false; }
        @Override public boolean isActive() { return false; }
        @Override public ChannelMetadata metadata() { return new ChannelMetadata(true); }
    }
}
