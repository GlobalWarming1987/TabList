package hu.montlikadani.tablist.tablist.fakeplayers;

import hu.montlikadani.tablist.tablist.player.TabListPlayer.PlayerSkinProperties;

import java.util.UUID;

public final class FakePlayer implements IFakePlayer {

	private final String name;
	private String displayName, headId;
	private int ping;

	public FakePlayer(String name, String displayName, String headIdentifier, int ping) {
		this.name = name;
		this.displayName = displayName;
		this.headId = headIdentifier;
		this.ping = ping;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public String getHeadId() {
		return headId;
	}

	@Override
	public int getPing() {
		return ping;
	}

	@Override
	public void remove() {
		// Remove the fake player from the tab list or scoreboard (actual code depends on implementation)
	}

	@Override
	public void display() {
		// Display the fake player (actual code depends on implementation)
	}

	@Override
	public void setSkin(PlayerSkinProperties props) {
		// Set skin based on provided PlayerSkinProperties (implementation required)
	}

	@Override
	public void setPing(int ping) {
		this.ping = ping;
	}

	@Override
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
