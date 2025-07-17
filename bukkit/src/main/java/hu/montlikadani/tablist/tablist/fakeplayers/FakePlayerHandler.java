package hu.montlikadani.tablist.tablist.fakeplayers;

import hu.montlikadani.tablist.TabList;
import hu.montlikadani.tablist.tablist.player.PlayerSkinProperties;
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
    private final List<IFakePlayer> fakePlayers = Collections.synchronizedList(new ArrayList<>());

    public FakePlayerHandler(TabList plugin) {
        this.plugin = plugin;
    }

    public void addPlayer(Player player) {
        UUID uuid = player.getUniqueId();

        for (IFakePlayer fp : fakePlayers) {
            if (fp.getUniqueId().equals(uuid)) {
                return;
            }
        }

        if (!Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().runTask(JavaPlugin.getProvidingPlugin(getClass()), () -> doAddPlayer(player));
        } else {
            doAddPlayer(player);
        }
    }

    private void doAddPlayer(Player player) {
        UUID uuid = player.getUniqueId();

        for (IFakePlayer fp : fakePlayers) {
            if (fp.getUniqueId().equals(uuid)) {
                return;
            }
        }

        FakePlayer fake = new FakePlayer(player.getName(), player.getName(), "", -1);
        fake.spawn();
        fakePlayers.add(fake);
    }

    public void removePlayer(Player player) {
        UUID uuid = player.getUniqueId();

        Iterator<IFakePlayer> it = fakePlayers.iterator();
        while (it.hasNext()) {
            IFakePlayer fp = it.next();
            if (fp.getUniqueId().equals(uuid)) {
                fp.remove();
                it.remove();
                return;
            }
        }
    }

    public void clear() {
        fakePlayers.forEach(IFakePlayer::remove);
        fakePlayers.clear();
    }

    public List<IFakePlayer> getFakePlayers() {
        return fakePlayers;
    }

    public EditingResult setPing(String name, int ping) {
        if (ping < 0) return EditingResult.PING_AMOUNT;
        for (IFakePlayer fp : fakePlayers) {
            if (fp.getName().equalsIgnoreCase(name)) {
                fp.setPing(ping);
                return EditingResult.OK;
            }
        }
        return EditingResult.NOT_EXIST;
    }

    public EditingResult setDisplayName(String name, String displayName) {
        for (IFakePlayer fp : fakePlayers) {
            if (fp.getName().equalsIgnoreCase(name)) {
                fp.setDisplayName(displayName);
                return EditingResult.OK;
            }
        }
        return EditingResult.NOT_EXIST;
    }

    public EditingResult renamePlayer(String oldName, String newName) {
        for (IFakePlayer fp : fakePlayers) {
            if (fp.getName().equalsIgnoreCase(oldName)) {
                fp.setName(newName);
                return EditingResult.OK;
            }
        }
        return EditingResult.NOT_EXIST;
    }

    public EditingResult createPlayer(String name, String displayName, String head, int ping) {
        for (IFakePlayer fp : fakePlayers) {
            if (fp.getName().equalsIgnoreCase(name)) {
                return EditingResult.ALREADY_EXIST;
            }
        }

        FakePlayer fake = new FakePlayer(name, displayName, head, ping);
        fake.spawn();
        fakePlayers.add(fake);
        return EditingResult.OK;
    }

    public EditingResult removePlayer(String name) {
        Iterator<IFakePlayer> it = fakePlayers.iterator();
        while (it.hasNext()) {
            IFakePlayer fp = it.next();
            if (fp.getName().equalsIgnoreCase(name)) {
                fp.remove();
                it.remove();
                return EditingResult.OK;
            }
        }
        return EditingResult.NOT_EXIST;
    }

    public EditingResult setSkin(String name, PlayerSkinProperties skin) {
        for (IFakePlayer fp : fakePlayers) {
            if (fp.getName().equalsIgnoreCase(name)) {
                fp.setSkin(skin);
                return EditingResult.OK;
            }
        }
        return EditingResult.NOT_EXIST;
    }

    public void removeAllFakePlayer() {
        clear();
    }

    public void load() {
        // implement loading logic if needed
    }

    public void display() {
        // implement display logic if needed
    }

    public Set<IFakePlayer> getAllFakePlayers() {
        return new HashSet<>(fakePlayers);
    }
}
