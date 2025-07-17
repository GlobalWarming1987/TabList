package hu.montlikadani.tablist.tablist.fakeplayers;

import hu.montlikadani.tablist.TabList;
import hu.montlikadani.tablist.tablist.fakeplayers.skin.Skin;
import hu.montlikadani.tablist.tablist.fakeplayers.skin.Skin.PlayerSkinProperties;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class FakePlayerHandler {

    public enum EditingResult {
        OK,
        ALREADY_EXIST,
        NOT_EXIST,
        PING_AMOUNT
    }

    private final TabList plugin;
    private final ConcurrentHashMap<UUID, FakePlayer> fakePlayers = new ConcurrentHashMap<>();

    public FakePlayerHandler(TabList plugin) {
        this.plugin = plugin;
    }

    public EditingResult createPlayer(String name, String displayName, String headIdentifier, int ping) {
        UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes());

        if (fakePlayers.containsKey(uuid)) {
            return EditingResult.ALREADY_EXIST;
        }

        FakePlayer fake = new FakePlayer(name, displayName, headIdentifier, ping);
        fake.spawn();

        fakePlayers.put(uuid, fake);
        return EditingResult.OK;
    }

    public EditingResult removePlayer(String name) {
        UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes());

        FakePlayer removed = fakePlayers.remove(uuid);
        if (removed == null) {
            return EditingResult.NOT_EXIST;
        }

        removed.remove();
        return EditingResult.OK;
    }

    public EditingResult renamePlayer(String oldName, String newName) {
        UUID oldUuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + oldName).getBytes());

        FakePlayer fake = fakePlayers.remove(oldUuid);
        if (fake == null) {
            return EditingResult.NOT_EXIST;
        }

        fake.setName(newName);
        fake.spawn();

        UUID newUuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + newName).getBytes());
        fakePlayers.put(newUuid, fake);

        return EditingResult.OK;
    }

    public EditingResult setSkin(String name, PlayerSkinProperties skin) {
        UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes());
        FakePlayer fake = fakePlayers.get(uuid);

        if (fake == null) {
            return EditingResult.NOT_EXIST;
        }

        fake.setSkin(skin);
        return EditingResult.OK;
    }

    public EditingResult setPing(String name, int ping) {
        if (ping < -1) {
            return EditingResult.PING_AMOUNT;
        }

        UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes());
        FakePlayer fake = fakePlayers.get(uuid);

        if (fake == null) {
            return EditingResult.NOT_EXIST;
        }

        fake.setPing(ping);
        return EditingResult.OK;
    }

    public EditingResult setDisplayName(String name, String displayName) {
        UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes());
        FakePlayer fake = fakePlayers.get(uuid);

        if (fake == null) {
            return EditingResult.NOT_EXIST;
        }

        fake.setDisplayName(displayName);
        return EditingResult.OK;
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

    public void display() {
        fakePlayers.values().forEach(FakePlayer::spawn);
    }

    public void load() {
        // You can load from config or wherever needed here.
    }

    public void removeAllFakePlayer() {
        clear();
    }

    public Set<IFakePlayer> getFakePlayers() {
        return new HashSet<>(fakePlayers.values());
    }
}
