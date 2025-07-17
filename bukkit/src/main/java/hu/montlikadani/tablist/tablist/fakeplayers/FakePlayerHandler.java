package hu.montlikadani.tablist.tablist.fakeplayers;

import hu.montlikadani.tablist.TabList;
import hu.montlikadani.tablist.tablist.player.TabListPlayer.PlayerSkinProperties;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class FakePlayerHandler {

    private final TabList plugin;
    final Map<UUID, FakePlayer> fakePlayers = new ConcurrentHashMap<>();

    public FakePlayerHandler(TabList plugin) {
        this.plugin = plugin;
    }

    public void addPlayer(Player player) {
        UUID uuid = player.getUniqueId();

        if (fakePlayers.containsKey(uuid)) {
            return;
        }

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

    public EditingResult createPlayer(String name, String displayName, String headIdentifier, int ping) {
        if (fakePlayers.values().stream().anyMatch(fp -> fp.getName().equalsIgnoreCase(name))) {
            return EditingResult.ALREADY_EXIST;
        }

        FakePlayer fake = new FakePlayer(name, displayName, headIdentifier, ping);
        fake.spawn();

        fakePlayers.put(UUID.randomUUID(), fake);
        return EditingResult.OK;
    }

    public EditingResult removePlayer(String name) {
        UUID key = fakePlayers.entrySet().stream()
                .filter(e -> e.getValue().getName().equalsIgnoreCase(name))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        if (key == null) return EditingResult.NOT_EXIST;

        fakePlayers.remove(key).remove();
        return EditingResult.OK;
    }

    public EditingResult renamePlayer(String oldName, String newName) {
        for (FakePlayer player : fakePlayers.values()) {
            if (player.getName().equalsIgnoreCase(oldName)) {
                player.setName(newName);
                return EditingResult.OK;
            }
        }

        return EditingResult.NOT_EXIST;
    }

    public EditingResult setSkin(String name, PlayerSkinProperties skin) {
        for (FakePlayer player : fakePlayers.values()) {
            if (player.getName().equalsIgnoreCase(name)) {
                player.setSkin(skin);
                return EditingResult.OK;
            }
        }

        return EditingResult.NOT_EXIST;
    }

    public EditingResult setPing(String name, int ping) {
        if (ping < 0) return EditingResult.PING_AMOUNT;

        for (FakePlayer player : fakePlayers.values()) {
            if (player.getName().equalsIgnoreCase(name)) {
                player.setPing(ping);
                return EditingResult.OK;
            }
        }

        return EditingResult.NOT_EXIST;
    }

    public EditingResult setDisplayName(String name, String displayName) {
        for (FakePlayer player : fakePlayers.values()) {
            if (player.getName().equalsIgnoreCase(name)) {
                player.setDisplayName(displayName);
                return EditingResult.OK;
            }
        }

        return EditingResult.NOT_EXIST;
    }

    public void removeAllFakePlayer() {
        clear();
    }

    public void load() {
        // Load logic here, maybe from config in future.
    }

    public void display() {
        fakePlayers.values().forEach(FakePlayer::spawn);
    }
}
