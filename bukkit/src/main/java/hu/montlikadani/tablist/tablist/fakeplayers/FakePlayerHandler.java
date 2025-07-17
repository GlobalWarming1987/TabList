package hu.montlikadani.tablist.tablist.fakeplayers;

import hu.montlikadani.tablist.tablist.player.PlayerSkinProperties;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class FakePlayerHandler {

	public enum EditingResult {
		OK,
		ALREADY_EXIST,
		NOT_EXIST,
		PING_AMOUNT
	}

	private final Set<IFakePlayer> fakePlayers = new HashSet<>();

	public Set<IFakePlayer> getAllFakePlayers() {
		return Collections.unmodifiableSet(fakePlayers);
	}

	public EditingResult createPlayer(String name, String displayName, String headIdentifier, int ping) {
		if (name == null || name.isEmpty()) {
			return EditingResult.NOT_EXIST;
		}

		for (IFakePlayer fp : fakePlayers) {
			if (fp.getName().equalsIgnoreCase(name)) {
				return EditingResult.ALREADY_EXIST;
			}
		}

		FakePlayer newFakePlayer = new FakePlayer(name);
		newFakePlayer.setPingLatency(ping);
		fakePlayers.add(newFakePlayer);
		return EditingResult.OK;
	}

	public EditingResult removePlayer(String name) {
		for (IFakePlayer fp : fakePlayers) {
			if (fp.getName().equalsIgnoreCase(name)) {
				fakePlayers.remove(fp);
				return EditingResult.OK;
			}
		}
		return EditingResult.NOT_EXIST;
	}

	public EditingResult renamePlayer(String oldName, String newName) {
		if (oldName.equalsIgnoreCase(newName)) {
			return EditingResult.NOT_EXIST;
		}

		for (IFakePlayer fp : fakePlayers) {
			if (fp.getName().equalsIgnoreCase(oldName)) {
				removePlayer(oldName);
				createPlayer(newName, newName, "", fp.getPingLatency());
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

	public EditingResult setPing(String name, int ping) {
		if (ping < 0 || ping > 1000) {
			return EditingResult.PING_AMOUNT;
		}

		for (IFakePlayer fp : fakePlayers) {
			if (fp.getName().equalsIgnoreCase(name)) {
				fp.setPingLatency(ping);
				return EditingResult.OK;
			}
		}

		return EditingResult.NOT_EXIST;
	}
}
