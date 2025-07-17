package hu.montlikadani.tablist.tablist.fakeplayers;

import hu.montlikadani.tablist.TabList;
import hu.montlikadani.tablist.tablist.player.PlayerSkinProperties;
import org.bukkit.Bukkit;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public final class FakePlayerHandler {

	private final TabList plugin;
	private final Set<IFakePlayer> fakePlayers = new LinkedHashSet<>();

	public enum EditingResult {
		OK, ALREADY_EXIST, NOT_EXIST, PING_AMOUNT
	}

	public FakePlayerHandler(TabList plugin) {
		this.plugin = plugin;
	}

	public EditingResult createPlayer(String name, String displayName, String headIdentifier, int ping) {
		if (getFakePlayer(name).isPresent()) {
			return EditingResult.ALREADY_EXIST;
		}

		FakePlayer player = new FakePlayer(name, displayName, headIdentifier, ping);
		fakePlayers.add(player);

		return EditingResult.OK;
	}

	public EditingResult removePlayer(String name) {
		Optional<IFakePlayer> optional = getFakePlayer(name);
		if (!optional.isPresent()) {
			return EditingResult.NOT_EXIST;
		}

		fakePlayers.remove(optional.get());
		return EditingResult.OK;
	}

	public EditingResult renamePlayer(String oldName, String newName) {
		Optional<IFakePlayer> optional = getFakePlayer(oldName);
		if (!optional.isPresent()) {
			return EditingResult.NOT_EXIST;
		}

		IFakePlayer player = optional.get();
		fakePlayers.remove(player);
		fakePlayers.add(new FakePlayer(newName, player.getDisplayName(), player.getHeadIdentifier(), player.getPingLatency()));
		return EditingResult.OK;
	}

	public EditingResult setSkin(String name, PlayerSkinProperties skin) {
		Optional<IFakePlayer> optional = getFakePlayer(name);
		if (!optional.isPresent()) {
			return EditingResult.NOT_EXIST;
		}

		optional.get().setSkin(skin);
		return EditingResult.OK;
	}

	public EditingResult setPing(String name, int latency) {
		if (latency < 0) {
			return EditingResult.PING_AMOUNT;
		}

		Optional<IFakePlayer> optional = getFakePlayer(name);
		if (!optional.isPresent()) {
			return EditingResult.NOT_EXIST;
		}

		optional.get().setPingLatency(latency);
		return EditingResult.OK;
	}

	public Set<IFakePlayer> getAllFakePlayers() {
		return Collections.unmodifiableSet(fakePlayers);
	}

	public Optional<IFakePlayer> getFakePlayer(String name) {
		return fakePlayers.stream().filter(fp -> fp.getName().equalsIgnoreCase(name)).findFirst();
	}
}
