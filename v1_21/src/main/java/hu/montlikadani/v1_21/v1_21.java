package hu.montlikadani.v1_21;

import hu.montlikadani.api.IPacketNM;
import io.netty.channel.*;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketListener;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.EnumProtocolDirection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardScore;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardObjective;
import net.minecraft.world.scores.criteria.IScoreboardCriteria;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.net.SocketAddress;
import java.util.*;

public final class v1_21 implements IPacketNM {

    private Field entriesField, playerNetworkManagerField;
    private final Scoreboard scoreboard = new Scoreboard();

    @Override
    public void sendPacket(Player player, Object packet) {
        getPlayerHandle(player).c.b((Packet<?>) packet);
    }

    @Override
    public void addPlayerChannelListener(Player player, List<Class<?>> classesToListen) {
        Channel channel = playerChannel(getPlayerHandle(player).c);

        if (channel != null && channel.pipeline().get(PACKET_INJECTOR_NAME) == null) {
            try {
                channel.pipeline().addBefore("packet_handler", PACKET_INJECTOR_NAME,
                        new PacketReceivingListener(player.getUniqueId(), classesToListen));
            } catch (NoSuchElementException ignored) {
            }
        }
    }

    private Channel playerChannel(PlayerConnection connection) {
        if (playerNetworkManagerField == null && (playerNetworkManagerField = fieldByType(connection.getClass().getSuperclass(),
                NetworkManager.class)) == null) {
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
            } catch (NoSuchElementException ignored) {
            }
        }
    }

    @Override
    public EntityPlayer getPlayerHandle(Player player) {
        return ((org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer) player).getHandle();
    }

    @Override
    public IChatBaseComponent fromJson(String json) {
        return net.minecraft.network.chat.ComponentSerialization.a
                .parse(com.mojang.serialization.JsonOps.INSTANCE, com.google.gson.JsonParser.parseString(json)).getOrThrow();
    }

    @Override
    public EntityPlayer getNewEntityPlayer(com.mojang.authlib.GameProfile profile) {
        MinecraftServer server = ((org.bukkit.craftbukkit.v1_21_R1.CraftServer) Bukkit.getServer()).getServer();
        ClientInformation clientInfo = ClientInformation.a();
        EntityPlayer entityPlayer = new EntityPlayer(server, server.I(), profile, clientInfo);

        entityPlayer.c = new EmptyPacketListener(server, new EmptyConnection(EnumProtocolDirection.b), entityPlayer,
                new CommonListenerCookie(profile, 0, clientInfo, false));

        return entityPlayer;
    }

    @Override
    public double[] serverTps() {
        return ((org.bukkit.craftbukkit.v1_21_R1.CraftServer) Bukkit.getServer()).getServer().recentTps;
    }

    @Override
    public ScoreboardObjective createScoreboardHealthObjectivePacket(String objectiveName, Object nameComponent) {
        return new ScoreboardObjective(null, objectiveName, IScoreboardCriteria.b, (IChatBaseComponent) nameComponent,
                IScoreboardCriteria.EnumScoreboardHealthDisplay.b, true, null);
    }

    @Override
    public PacketPlayOutScoreboardScore removeScoreboardScorePacket(String objectiveName, String scoreName, int score) {
        ScoreboardObjective objective = null;
        return new PacketPlayOutScoreboardScore(
                scoreName,
                objectiveName,
                score,
                Optional.of(CommonComponents.a),
                Optional.empty()
        );
    }

    private static class EmptyPacketListener extends PlayerConnection {
        public EmptyPacketListener(MinecraftServer server, NetworkManager nm, EntityPlayer player, CommonListenerCookie cookie) {
            super(server, nm, player, cookie);
        }

        @Override
        public boolean h() {
            return false;
        }

        @Override
        public void b(Packet<?> packet) {
        }
    }

    private static class EmptyConnection extends NetworkManager {
        public EmptyConnection(EnumProtocolDirection direction) {
            super(direction);
            n = new EmptyChannel(null);
            o = new SocketAddress() {};
        }

        @Override public void c() {}
        @Override public PacketListener k() { return null; }
        @Override public void a(Packet packet) {}
        @Override public void a(Packet packet, net.minecraft.network.PacketSendListener listener) {}
        @Override public void a(Packet packet, net.minecraft.network.PacketSendListener listener, boolean flag) {}
    }

    private static class EmptyChannel extends AbstractChannel {
        private final ChannelConfig config = new DefaultChannelConfig(this);
        protected EmptyChannel(Channel parent) { super(parent); }
        @Override protected AbstractUnsafe newUnsafe() { return null; }
        @Override protected boolean isCompatible(EventLoop loop) { return false; }
        @Override protected SocketAddress localAddress0() { return null; }
        @Override protected SocketAddress remoteAddress0() { return null; }
        @Override protected void doBind(SocketAddress localAddress) {}
        @Override protected void doDisconnect() {}
        @Override protected void doClose() {}
        @Override protected void doBeginRead() {}
        @Override protected void doWrite(ChannelOutboundBuffer in) {}
        @Override public ChannelConfig config() { config.setAutoRead(true); return config; }
        @Override public boolean isOpen() { return false; }
        @Override public boolean isActive() { return false; }
        @Override public ChannelMetadata metadata() { return new ChannelMetadata(true); }
    }

    private final class PacketReceivingListener extends ChannelDuplexHandler {
        private final UUID listenerPlayerId;
        private final List<Class<?>> classesToListen;

        public PacketReceivingListener(UUID listenerPlayerId, List<Class<?>> classesToListen) {
            this.listenerPlayerId = listenerPlayerId;
            this.classesToListen = classesToListen;
        }

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            super.write(ctx, msg, promise);
        }
    }
}
