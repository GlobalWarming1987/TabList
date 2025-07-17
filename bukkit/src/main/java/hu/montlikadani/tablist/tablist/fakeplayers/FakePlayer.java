package hu.montlikadani.tablist.tablist.fakeplayers;

import hu.montlikadani.tablist.tablist.player.TabListPlayer.PlayerSkinProperties;

public final class FakePlayer implements IFakePlayer {

	private String name;
	private String displayName;
	private String headIdentifier;
	private int ping;
	private PlayerSkinProperties skin;

	public FakePlayer(String name, String displayName, String headIdentifier, int ping) {
		this.name = name;
		this.displayName = displayName;
		this.headIdentifier = headIdentifier;
		this.ping = ping;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getHeadIdentifier() {
		return headIdentifier;
	}

	@Override
	public void setHeadIdentifier(String id) {
		this.headIdentifier = id;
	}

	@Override
	public int getPing() {
		return ping;
	}

	@Override
	public void setPing(int ping) {
		this.ping = ping;
	}

	@Override
	public void setSkin(PlayerSkinProperties props) {
		this.skin = props;
	}

	@Override
	public void spawn() {
		// Placeholder spawn logic (e.g., update tab or create scoreboard entry)
	}

	@Override
	public void remove() {
		// Placeholder remove logic
	}
}
