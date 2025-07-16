package hu.montlikadani.v1_21;

import hu.montlikadani.api.IPacketNM;
import hu.montlikadani.api.IPacketNM.ObjectiveFormat;
import io.netty.channel.*;
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
import net.minecraft.world.scores.*;
import net.minecraft.world.scores.criteria.IScoreboardCriteria;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.Field;
import java.net.SocketAddress;
import java.util.*;

public final class v1_21 implements IPacketNM {

    private Field playerNetworkManagerField;
    private final Scoreboard scoreboard = new Scoreboard();
    private final Map<String, ScoreboardObjective> scoreboardObjectives = new HashMap<>();

    // ... existing methods same as before ...

    @Override
    public PacketPlayOutScoreboardObjective unregisterBoardTeamPacket(String teamName) {
        Collection<ScoreboardTeam> teams = scoreboard.getTeams();

        synchronized (teams) {
            for (ScoreboardTeam team : new ArrayList<>(teams)) {
                if (team.getName().equals(teamName)) {
                    scoreboard.removeTeam(team);
                    // Use constructor instead of obsolete factory method
                    return new PacketPlayOutScoreboardObjective(team, 1); // '1' = removal mode
                }
            }
        }

        return null;
    }

    // ... rest of class including EmptyPacketListener, EmptyConnection, EmptyChannel, PacketReceivingListener ...
}
