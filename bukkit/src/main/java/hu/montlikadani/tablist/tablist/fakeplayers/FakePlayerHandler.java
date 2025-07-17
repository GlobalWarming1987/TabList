package hu.montlikadani.tablist.bukkit.player;

import hu.montlikadani.tablist.TabList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class FakePlayerHandler {

    private final TabList plugin;
    private final ConcurrentHashMap<UUID, FakePlayer> fakePlayers = new ConcurrentHashMap<>();

    public FakePlayerHandler(TabList plugin) {
        this.plugin = plugin;
    }

    public void addPlayer(Player player) {
        UUID uuid = player.getUniqueId();

        if (fakePlayers.containsKey(uuid)) {
            return;
        }

        // Run the addition of the fake player on the main server thread
        if (!Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().runTask(JavaPlugin.getProvidingPlugin(getClass()), () -> {
                doAddPlayer(player);
            });
        } else {
            doAddPlayer(player);
        }
    }

    private void doAddPlayer(Player player) {
        UUID uuid = player.getUniqueId();

        if (fakePlayers.containsKey(uuid)) {
            return;
        }

        FakePlayer fake = new FakePlayer(player.getName(), uuid);
        fake.spawn();

        fakePlayers.put(uuid, fake);
    }

    public void removePlayer(Player player) {
        UUID uuid = player.getUniqueId();

        FakePlayer fake = fakePlayers.remove(uuid);
        if (fake != null) {
            fake.remove();
        }
    }

    public void clear() {
        fakePlayers.values().forEach(FakePlayer::remove);
        fakePlayers.clear();
    }
}
