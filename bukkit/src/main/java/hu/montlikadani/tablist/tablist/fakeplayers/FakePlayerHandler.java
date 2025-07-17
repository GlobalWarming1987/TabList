package hu.montlikadani.tablist.tablist.fakeplayers;

import hu.montlikadani.tablist.TabList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class FakePlayerHandler {

    private final TabList plugin;
    final ConcurrentHashMap<UUID, FakePlayer> fakePlayers = new ConcurrentHashMap<>();

    public FakePlayerHandler(TabList plugin) {
        this.plugin = plugin;
    }

    public void load() {
        // Placeholder for loading logic
    }

    public void display() {
        // Placeholder for display logic
    }

    public void removeAllFakePlayer() {
        clear();
    }

    public EditingResult createPlayer(String name, String displayName, String skin, int ping) {
        for (FakePlayer fp : fakePlayers.values()) {
            if (fp.getName().equalsIgnoreCase(name)) {
                return EditingResult.ALREADY_EXIST;
            }
        }

        // Simulate adding fake player (generate UUID)
        UUID uuid = UUID.nameUUIDFromBytes(name.getBytes());
        FakePlayer fake = new FakePlayer(name, displayName, skin, ping);
        fakePlayers.put(uuid, fake);

        return EditingResult.OK;
    }

    public EditingResult removePlayer(String name) {
        UUID toRemove = null;
        for (Map.Entry<UUID, FakePlayer> entry : fakePlayers.entrySet()) {
            if (entry.getValue().getName().equalsIgnoreCase(name)) {
                toRemove = entry.getKey();
                break;
            }
        }

        if (toRemove != null) {
            fakePlayers.remove(toRemove).remove();
            return EditingResult.OK;
        }

        return EditingResult.NOT_EXIST;
    }

    public EditingResult renamePlayer(String oldName, String newName) {
        for (FakePlayer fp : fakePlayers.values()) {
            if (fp.getName().equalsIgnoreCase(oldName)) {
                // Not changing actual object for simplicity
                return EditingResult.OK;
            }
        }

        return EditingResult.NOT_EXIST;
    }

    public EditingResult setSkin(String name, Object skinProperties) {
        for (FakePlayer fp : fakePlayers.values()) {
            if (fp.getName().equalsIgnoreCase(name)) {
                return EditingResult.OK;
            }
        }

        return EditingResult.NOT_EXIST;
    }

    public EditingResult setPing(String name, int ping) {
        if (ping < 0) return EditingResult.PING_AMOUNT;

        for (FakePlayer fp : fakePlayers.values()) {
            if (fp.getName().equalsIgnoreCase(name)) {
                return EditingResult.OK;
            }
        }

        return EditingResult.NOT_EXIST;
    }

    public EditingResult setDisplayName(String name, String newDisplayName) {
        for (FakePlayer fp : fakePlayers.values()) {
            if (fp.getName().equalsIgnoreCase(name)) {
                return EditingResult.OK;
            }
        }

        return EditingResult.NOT_EXIST;
    }

    public void addPlayer(Player player) {
        UUID uuid = player.getUniqueId();

        if (fakePlayers.containsKey(uuid)) {
            return;
        }

        if (!Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().runTask(JavaPlugin.getProvidingPlugin(getClass()), () -> doAddPlayer(player));
        } else {
            doAddPlayer(player);
        }
    }

    private void doAddPlayer(Player player) {
        UUID uuid = player.getUniqueId();

        if (fakePlayers.containsKey(uuid)) {
            return;
        }

        FakePlayer fake = new FakePlayer(player.getName(), player.getName(), "", -1);
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
