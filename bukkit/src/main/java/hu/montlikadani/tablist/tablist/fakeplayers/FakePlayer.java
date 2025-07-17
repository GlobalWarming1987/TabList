package hu.montlikadani.tablist.tablist.fakeplayers;

import hu.montlikadani.tablist.tablist.player.PlayerSkinProperties;

public final class FakePlayer implements IFakePlayer {

	private final String name;
	private String displayName;
	private int ping;
	private PlayerSkinProperties skin;

	public FakePlayer(String name, String displayName, String headIdentifier, int ping) {
		this.name = name;
		this.displayName = displayName;
		this.ping = ping;
	}

	@Override
	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public int getPingLatency() {
		return ping;
	}

	@Override
	public void setPingLatency(int ping) {
		this.ping = ping;
	}

	@Override
	public void setSkin(PlayerSkinProperties props) {
		this.skin = props;
	}

	@Override
	public PlayerSkinProperties getSkin() {
		return skin;
	}
}
