package hu.montlikadani.tablist.tablist.fakeplayers;

import hu.montlikadani.tablist.tablist.player.PlayerSkinProperties;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public final class FakePlayerHandler {

	public enum EditingResult {
		OK, ALREADY_EXIST, NOT_EXIST, PING_AMOUNT
	}

	private final Set<IFakePlayer> fakePlayers = new HashSet<>();

	public EditingResult createPlayer(String name, String displayName, String skin, int ping) {
		if (exists(name)) {
			return EditingResult.ALREADY_EXIST;
		}

		IFakePlayer player = new FakePlayer(name, displayName, skin, ping);
		fakePlayers.add(player);
		return EditingResult.OK;
	}

	public EditingResult removePlayer(String name) {
		Optional<IFakePlayer> opt = find(name);
		if (opt.isPresent()) {
			fakePlayers.remove(opt.get());
			return EditingResult.OK;
		}
		return EditingResult.NOT_EXIST;
	}

	public EditingResult renamePlayer(String oldName, String newName) {
		Optional<IFakePlayer> opt = find(oldName);
		if (opt.isEmpty()) {
			return EditingResult.NOT_EXIST;
		}

		if (exists(newName)) {
			return EditingResult.ALREADY_EXIST;
		}

		IFakePlayer player = opt.get();
		fakePlayers.remove(player);
		IFakePlayer newPlayer = new FakePlayer(newName, newName, null, player.getPingLatency());
		newPlayer.setSkin(player.getSkin());
		fakePlayers.add(newPlayer);

		return EditingResult.OK;
	}

	public EditingResult setPing(String name, int amount) {
		if (amount < 0 || amount > 999) {
			return EditingResult.PING_AMOUNT;
		}

		Optional<IFakePlayer> opt = find(name);
		if (opt.isEmpty()) {
			return EditingResult.NOT_EXIST;
		}

		opt.get().setPingLatency(amount);
		return EditingResult.OK;
	}

	public EditingResult setSkin(String name, PlayerSkinProperties skin) {
		Optional<IFakePlayer> opt = find(name);
		if (opt.isEmpty()) {
			return EditingResult.NOT_EXIST;
		}

		opt.get().setSkin(skin);
		return EditingResult.OK;
	}

	public boolean exists(String name) {
		return find(name).isPresent();
	}

	public Optional<IFakePlayer> find(String name) {
		return fakePlayers.stream()
				.filter(player -> player.getName().equalsIgnoreCase(name))
				.findFirst();
	}

	public Set<IFakePlayer> getAllFakePlayers() {
		return Collections.unmodifiableSet(fakePlayers);
	}
}
